package com.example.prototypesetup.controller;

import com.example.prototypesetup.entity.AppUser;
import com.example.prototypesetup.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    // GET all users
    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @GetMapping("/{id}")
public ResponseEntity<AppUser> getUserById(@PathVariable("id") Long id) {
    return appUserRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    // CREATE user
    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserRepository.save(user);
    }

    // UPDATE user
@PutMapping("/{id}")
public ResponseEntity<AppUser> updateUser(@PathVariable("id") Long id, @RequestBody AppUser updatedUser) {
    return appUserRepository.findById(id).map(user -> {
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }
        AppUser savedUser = appUserRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }).orElse(ResponseEntity.notFound().build());
}


    // DELETE user
    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
    if (appUserRepository.existsById(id)) {
        appUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
