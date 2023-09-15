package com.eventsvk.controllers;

import com.eventsvk.entity.user.Role;
import com.eventsvk.entity.user.User;
import com.eventsvk.services.RoleService;
import com.eventsvk.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class adminRestController {

    private final RoleService roleService;
    private final UserService userService;

    @Operation(
            summary = "Get users",
            description = "Get all users"
    )
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Get user",
            description = "Get user by id"
    )
    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        User user = userService.findUserById(userId);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Get roles",
            description = "Get all roles"
    )
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roleList = roleService.getAllRoles();
        return ResponseEntity.ok(roleList);
    }

    @Operation(
            summary = "Save user",
            description = "Save new user"
    )
    @PostMapping("/user")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Update user",
            description = "Update user"
    )
    @PutMapping("/user")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Ban user",
            description = "Ban user"
    )
    @PutMapping("/user/{id}")
    public ResponseEntity<HttpStatus> banUser(@PathVariable("id") long userId) {
        userService.banUser(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
