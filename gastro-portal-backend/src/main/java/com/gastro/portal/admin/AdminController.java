package com.gastro.portal.admin;

import com.gastro.portal.user.UserService;
import com.gastro.portal.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin-panel")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserInfoDto> users = userService.getAllUsers();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("block-user")
    public ResponseEntity<?> blockUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("pending-recipes")
    public ResponseEntity<?> getPendingRecipes() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("accept-recipe")
    public ResponseEntity<?> acceptRecipe() {
        return ResponseEntity.ok().build();
    }


}
