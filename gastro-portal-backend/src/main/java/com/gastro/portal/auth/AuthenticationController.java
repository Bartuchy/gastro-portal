package com.gastro.portal.auth;

import com.gastro.portal.account.UserAccountEntity;
import com.gastro.portal.account.UserAccountService;
import com.gastro.portal.auth.dto.AuthenticationRequest;
import com.gastro.portal.auth.dto.AuthenticationResponse;
import com.gastro.portal.auth.dto.RegisterRequestDto;
import com.gastro.portal.auth.dto.RegisterResponseDto;
import com.gastro.portal.config.QRCodeGenerator;
import com.gastro.portal.mailing.token.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ConfirmationTokenService confirmationTokenService;
    private final QRCodeGenerator qrCodeGenerator;
    private final UserAccountService userAccountService;

    @PostMapping("register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequestDto request
    ) throws MessagingException, IOException {
        authenticationService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("confirm-registration")
    public ResponseEntity<RegisterResponseDto> confirmRegistration(
            @RequestParam String confirmationToken
    ) throws UnsupportedEncodingException {
        authenticationService.confirmRegistration(confirmationToken);
        UserAccountEntity userAccountEntity = confirmationTokenService.getUserAccountByToken(confirmationToken);

        RegisterResponseDto registerResponseDto =
                new RegisterResponseDto(userAccountEntity.getIsUsing2FA(), qrCodeGenerator.generateQRUrl(userAccountEntity));
        return ResponseEntity.ok(registerResponseDto);
    }

    @GetMapping("/{email}/qr-code")
    public ResponseEntity<String> getGrCodeForUser(@PathVariable String email) throws UnsupportedEncodingException {
        UserAccountEntity userAccountEntity = userAccountService.getUserAccountEntityByUsername(email);
        String qrCodeLink = qrCodeGenerator.generateQRUrl(userAccountEntity);
        return ResponseEntity.ok(qrCodeLink);
    }
}
