package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.AppUser;
import com.example.prototypesetup.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/test/{username}")
    public ResponseEntity<?> testUser(@PathVariable("username") String username) {
        try {
            AppUser user = appUserRepository.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("Account not found."));
        }
        else if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid username or password"));
        }

        UserInfo userInfo = new UserInfo(user.getUserId(), user.getUsername(), user.getRole().getRoleId());
        LoginSuccessResponse response = new LoginSuccessResponse("Login successful", userInfo);

        return ResponseEntity.ok(response);
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class ErrorResponse {
        private final String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
    }

    public static class UserInfo {
        private final Long user_id;
        private final String name;
        private final Long role_id;

        public UserInfo(Long user_id, String name, Long role_id) {
            this.user_id = user_id;
            this.name = name;
            this.role_id = role_id;
        }
        public Long getUser_id() { return user_id; }
        public String getName() { return name; }
        public Long getRole_id() { return role_id; }
    }

    public static class LoginSuccessResponse {
        private final String message;
        private final UserInfo user;

        public LoginSuccessResponse(String message, UserInfo user) {
            this.message = message;
            this.user = user;
        }
        public String getMessage() { return message; }
        public UserInfo getUser() { return user; }
    }
}