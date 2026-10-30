package io.student.rococo.controller;

import io.student.rococo.model.UserJson;
import io.student.rococo.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserJson getUser(@AuthenticationPrincipal Jwt principal) {
        return userService.getCurrentUser(principal.getClaim("sub"));
    }

    @PatchMapping
    public UserJson updateUser(@RequestBody UserJson user, @AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        if (!Objects.equals(username, user.username())) {
            throw new ResponseStatusException(FORBIDDEN, "Поле username обновлять нельзя");
        }
        return userService.updateUser(user);
    }
}
