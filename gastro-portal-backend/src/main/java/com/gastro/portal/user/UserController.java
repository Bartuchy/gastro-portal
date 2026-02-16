package com.gastro.portal.user;

import com.gastro.portal.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
        List<UserInfoDto> userInfos = userService.getAllUsers();
        return ResponseEntity.ok(userInfos);
    }


}
