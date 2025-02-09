package com.microservice.thread.controller;

import com.microservice.thread.entity.UserEntity;
import com.microservice.thread.entity.UserJsonEntity;
import com.microservice.thread.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getById(@PathVariable Integer userId) {
        Optional<UserEntity> userDetails = userService.getUserById(userId);
        return userDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllRandomUser")
    public Mono<ResponseEntity<String>> getAllRandomUsers() {
        return userService.fetchRandomUsers().map(result -> ResponseEntity.ok(result));
    }

    @GetMapping("/cache/{email}")
    public String getUserFromCache(@PathVariable String email) {
        UserJsonEntity user = userService.getUserFromCache(email);
        if (user != null) {
            return "User found in cache: " + user.getEmail();
        } else {
            return "User not found in cache.";
        }
    }

}
