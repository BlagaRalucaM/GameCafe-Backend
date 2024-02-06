package com.example.gameCafe.controllers;

import com.example.gameCafe.entities.User;
import com.example.gameCafe.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping
    public User saveUser(@RequestBody @Valid User newUser) {
        return userService.saveUser(newUser);
    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable Integer id, @Valid @RequestBody User newUser) throws Exception {
        return userService.updateUserById(id, newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PatchMapping("/{Id}")
    public ResponseEntity<User> updateEmail(@PathVariable Integer id, @RequestParam String newEmail) {

        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setEmail(newEmail);
        User updatedUser = userService.saveUser(user);

        return ResponseEntity.ok(updatedUser);
    }


}
