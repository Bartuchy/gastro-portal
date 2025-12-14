package com.gastro.portal.auth;

import com.gastro.portal.common.mapper.user.UserMapperFacade;
import com.gastro.portal.user.UserEntity;
import com.gastro.portal.auth.dto.AuthenticationRequest;
import com.gastro.portal.auth.dto.AuthenticationResponse;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.mailing.Email;
import com.gastro.portal.mailing.EmailFactory;
import com.gastro.portal.mailing.EmailSender;
import com.gastro.portal.mailing.token.ConfirmationToken;
import com.gastro.portal.mailing.token.ConfirmationTokenFactory;
import com.gastro.portal.mailing.token.ConfirmationTokenService;
import com.gastro.portal.config.security.jwt.JwtService;
import com.gastro.portal.auth.exception.UserExistsException;
import com.gastro.portal.auth.exception.UserNotFoundException;
import com.gastro.portal.user.UserPrincipal;
import com.gastro.portal.user.UserRepository;
import com.gastro.portal.user.UserService;
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

        Email email = emailFactory.createConfirmationEmail(request.getEmail(), userEntity.getNickname(), confirmationToken.getToken());
        emailSender.sendMail(email);

        log.info("User {} registered", userEntity.getUsername());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new CustomUsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        request.getVerificationCode()
                )
        );

        UserPrincipal userPrincipal = userRepository.findUserByUsername(request.getEmail())
                .map(UserPrincipal::new)
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = jwtService.generateToken(userPrincipal);

        log.info("User {} authenticated", userPrincipal.user().getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .email(request.getEmail())
                .username(userPrincipal.user().getUsername())
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
        userService.enableUser(confirmationToken.getUserEntity().getUsername());
        userService.unlockAccount(confirmationToken.getUserEntity().getUsername());

        log.info("User {} confirmed registration", confirmationToken.getUserEntity().getUsername());
    }


}
