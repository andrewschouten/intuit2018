package com.andrew.service;

import com.andrew.entity.User;
import com.andrew.exception.FollowUserException;
import com.andrew.exception.UserNotFoundException;
import com.andrew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for user management
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve the user by id
     *
     * @param userId the user id to retrieve
     * @return the user else exception if not found
     */
    public User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new UserNotFoundException("Unable to find user with Id: " + userId);
    }

    /**
     * Retrieve the user by the ldap uid
     *
     * @param ldapUid the ldap uid
     * @return the user else exception if not found
     */
    public User getUser(String ldapUid) {
        Optional<User> user = userRepository.findByLdapUid(ldapUid);
        if (user.isPresent())
            return user.get();
        throw new UserNotFoundException("Unable to find user with ldapUID: " + ldapUid);
    }

    /**
     * Follow another user
     *
     * @param user the current user
     * @param userIdToFollow the user id of the user to follow
     * @return the saved user
     */
    public User followUser(User user, Long userIdToFollow) {
        User toFollow = this.getUser(userIdToFollow);
        if (userIdToFollow.compareTo(user.getId()) == 0)
            throw new FollowUserException("You cannot follow yourself");
        if (user.getFollowing().contains(toFollow))
            throw new FollowUserException("You are already following this user");
        user.addFollowing(toFollow);
        return userRepository.save(user);
    }

}
