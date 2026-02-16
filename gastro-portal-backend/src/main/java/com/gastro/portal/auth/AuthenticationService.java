package com.gastro.portal.auth;

import com.gastro.portal.account.UserAccountRepository;
import com.gastro.portal.admin.AdminService;
import com.gastro.portal.auth.dto.AuthenticationRequest;
import com.gastro.portal.auth.dto.AuthenticationResponse;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.auth.exception.UserNotFoundException;
import com.gastro.portal.auth.mapper.RegistrationRequestMapper;
import com.gastro.portal.auth.mapper.UserAccountUserAggregate;
import com.gastro.portal.auth.validator.RegistrationValidator;
import com.gastro.portal.config.security.jwt.JwtService;
import com.gastro.portal.mailing.Email;
import com.gastro.portal.mailing.EmailFactory;
import com.gastro.portal.mailing.EmailSender;
import com.gastro.portal.mailing.token.ConfirmationToken;
import com.gastro.portal.mailing.token.ConfirmationTokenFactory;
import com.gastro.portal.mailing.token.ConfirmationTokenService;
import com.gastro.portal.user.UserPrincipal;
import com.gastro.portal.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;

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
    private final UserAccountRepository userAccountRepository;
    private final AdminService adminService;
    private final RegistrationValidator registrationValidator;
    private final RegistrationRequestMapper registrationRequestMapper;


    @Transactional
    public void register(RegisterRequestDto request) throws IOException, MessagingException {
        registrationValidator.assertRegistrationDataValidation(request);

        UserAccountUserAggregate aggregate = registrationRequestMapper.mapFrom(request);
        userRepository.save(aggregate.user());
        userAccountRepository.save(aggregate.userAccount());

        ConfirmationToken confirmationToken = confirmationTokenFactory.createConfirmationToken(aggregate.userAccount());
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Email email = emailFactory.createConfirmationEmail(request.getDisplayName(), aggregate.user().getDisplayName(), confirmationToken.getToken());
        emailSender.sendMail(email);

        log.info("User {} registered", aggregate.user().getDisplayName());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new CustomUsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        request.getVerificationCode()
                )
        );

        UserPrincipal userPrincipal = userAccountRepository.findUserAccountEntityByUsername(request.getEmail())
                .map(userAccount -> new UserPrincipal(
                        userAccount.getId(),
                        userAccount.getUser().getId(),
                        userAccount.getUsername(),
                        userAccount.getPassword(),
                        Boolean.TRUE.equals(userAccount.getIsEnabled()),
                        Boolean.TRUE.equals(userAccount.getIsNonLocked()),
                        userAccount.getRole().getName()
                ))
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = jwtService.generateToken(userPrincipal);

        log.info("User {} authenticated", userPrincipal.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .email(request.getEmail())
                .username(userPrincipal.getUsername())
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
        adminService.enableUser(confirmationToken.getUserAccount().getUsername());
        adminService.unlockAccount(confirmationToken.getUserAccount().getUsername());

        log.info("User {} confirmed registration", confirmationToken.getUserAccount().getUsername());
    }


}
