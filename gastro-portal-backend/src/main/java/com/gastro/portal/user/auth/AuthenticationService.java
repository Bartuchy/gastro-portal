package com.gastro.portal.user.auth;

import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.auth.dto.AuthenticationRequest;
import com.gastro.portal.user.auth.dto.AuthenticationResponse;
import com.gastro.portal.user.auth.dto.RegisterRequestDto;
import com.gastro.portal.config.mailing.Email;
import com.gastro.portal.config.mailing.EmailFactory;
import com.gastro.portal.config.mailing.EmailSender;
import com.gastro.portal.config.mailing.token.ConfirmationToken;
import com.gastro.portal.config.mailing.token.ConfirmationTokenFactory;
import com.gastro.portal.config.mailing.token.ConfirmationTokenService;
import com.gastro.portal.config.security.jwt.JwtService;
import com.gastro.portal.user.auth.exception.UserExistsException;
import com.gastro.portal.user.auth.exception.UserNotFoundException;
import com.gastro.portal.user.UserRepository;
import com.gastro.portal.user.UserService;
import com.gastro.portal.user.mapper.UserMapperFacade;
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
    private final UserMapperFacade userMapperFacade;
    private final UserService userService;


    @Transactional
    public void register(RegisterRequestDto request) throws IOException, MessagingException {
        UserEntity userEntity = userMapperFacade.toUserEntity(request);
        Optional<UserEntity> existingUser = userService.getUSerOptByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistsException(request);
        }

        userRepository.save(userEntity);

        ConfirmationToken confirmationToken = confirmationTokenFactory.createConfirmationToken(userEntity);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Email email = emailFactory.createConfirmationEmail(request.getEmail(), userEntity.getUsername(), confirmationToken.getToken());
        emailSender.sendMail(email);

        log.info("User {} registered", userEntity.getEmail());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new CustomUsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        request.getVerificationCode()
                )
        );

        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = jwtService.generateToken(userEntity);

        log.info("User {} authenticated", userEntity.getEmail());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .email(request.getEmail())
                .username(userEntity.getEmail())
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
        userService.enableUser(confirmationToken.getUserEntity().getEmail());
        userService.unlockAccount(confirmationToken.getUserEntity().getEmail());

        log.info("User {} confirmed registration", confirmationToken.getUserEntity().getEmail());
    }


}
