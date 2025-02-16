package com.security.module.user.auth;

import com.security.module.user.auth.dto.AuthenticationRequest;
import com.security.module.user.auth.dto.AuthenticationResponse;
import com.security.module.user.auth.dto.RegisterRequestDto;
import com.security.module.config.mailing.Email;
import com.security.module.config.mailing.EmailFactory;
import com.security.module.config.mailing.EmailSender;
import com.security.module.config.mailing.token.ConfirmationToken;
import com.security.module.config.mailing.token.ConfirmationTokenFactory;
import com.security.module.config.mailing.token.ConfirmationTokenService;
import com.security.module.config.security.jwt.JwtService;
import com.security.module.user.auth.exception.UserExistsException;
import com.security.module.user.auth.exception.UserNotFoundException;
import com.security.module.user.User;
import com.security.module.user.UserRepository;
import com.security.module.user.UserService;
import com.security.module.user.mapper.UserMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;
    private final EmailFactory emailFactory;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenFactory confirmationTokenFactory;
    private final UserMapper userMapper;
    private final UserService userService;


    @Transactional
    public void register(RegisterRequestDto request) throws IOException, MessagingException {
        User user = userMapper.registerRequestDtoToUserEntity(request);
        Optional<User> existingUser = userService.getUSerOptByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistsException(request);
        }

        userRepository.save(user);

        ConfirmationToken confirmationToken = confirmationTokenFactory.createConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Email email = emailFactory.createConfirmationEmail(request.getEmail(), user.getUsername(), confirmationToken.getToken());
        emailSender.sendMail(email);

        log.info("User {} registered", user.getEmail());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new CustomUsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        request.getVerificationCode()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = jwtService.generateToken(user);

        log.info("User {} authenticated", user.getEmail());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .email(request.getEmail())
                .username(user.getEmail())
                .build();
    }

    public void confirmRegistration(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        if (confirmationToken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        userService.unlockAccount(confirmationToken.getUser().getEmail());

        log.info("User {} confirmed registration", confirmationToken.getUser().getEmail());
    }


}
