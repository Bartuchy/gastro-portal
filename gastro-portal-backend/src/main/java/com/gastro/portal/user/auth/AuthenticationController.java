package com.gastro.portal.user.auth;

import com.gastro.portal.user.UserEntity;
import com.gastro.portal.user.auth.dto.AuthenticationRequest;
import com.gastro.portal.user.auth.dto.AuthenticationResponse;
import com.gastro.portal.user.auth.dto.RegisterRequestDto;
import com.gastro.portal.user.auth.dto.RegisterResponseDto;
import com.gastro.portal.config.QRCodeGenerator;
import com.gastro.portal.config.mailing.token.ConfirmationTokenService;
import com.gastro.portal.user.UserService;
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
    private final UserService userService;

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
        UserEntity userEntity = confirmationTokenService.getUserByToken(confirmationToken);

        RegisterResponseDto registerResponseDto =
                new RegisterResponseDto(userEntity.getIsUsing2FA(), qrCodeGenerator.generateQRUrl(userEntity));
        return ResponseEntity.ok(registerResponseDto);
    }

    @GetMapping("/{email}/qr-code")
    public ResponseEntity<String> getGrCodeForUser(@PathVariable String email) throws UnsupportedEncodingException {
        UserEntity userEntity = userService.getUserByEmail(email);
        String qrCodeLink = qrCodeGenerator.generateQRUrl(userEntity);
        return ResponseEntity.ok(qrCodeLink);
    }
}
