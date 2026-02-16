package com.gastro.portal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping("{email}/using2fa")
    public ResponseEntity<Boolean> isUserUsing2FA(@PathVariable String email) {
        boolean using2FA = userAccountService.isUserAccountUsing2FA(email);
        return ResponseEntity.ok(using2FA);
    }

    @PostMapping("{email}/update/2fa")
    public ResponseEntity<Void> modifyUser2FA(@PathVariable String email) {
        userAccountService.updateUser2FA(email, false);
        return ResponseEntity.ok().build();
    }
}
