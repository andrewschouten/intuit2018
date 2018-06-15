package com.andrew.controller;

import com.andrew.entity.User;
import com.andrew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Endpoints to follow another user and for user management
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Allows the current user to follow another user
     *
     * @param principal the current logged in user
     * @param userId the user to follow
     * @return the list of users you are following
     */
    @PostMapping("follows/{userId}")
    public ResponseEntity<List<User>> followUser(Principal principal, @PathVariable Long userId) {
        User curUser = userService.getUser(principal.getName());
        User savedUser = userService.followUser(curUser, userId);
        return ResponseEntity.ok(savedUser.getFollowing());
    }

    /**
     * Return the list of users that the current user is following
     *
     * @param principal the current logged in user
     * @return the list of users you are following
     */
    @GetMapping("follows")
    public ResponseEntity<List<User>> getFollowing(Principal principal) {
        User curUser = userService.getUser(principal.getName());
        return ResponseEntity.ok(curUser.getFollowing());
    }

}
