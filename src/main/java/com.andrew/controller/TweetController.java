package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.exception.CreateTweetException;
import com.andrew.service.TweetService;
import com.andrew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

/**
 * Tweet endpoints to manage tweets
 */
@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    /**
     * Allows a user to tweet a message to their followers
     *
     * @param principal the current logged in user
     * @param tweet the message to post
     * @return the created tweet and location uri
     */
    @PostMapping("/tweets")
    public ResponseEntity<Tweet> postTweet(Principal principal, @RequestBody Tweet tweet) {
        User curUser = userService.getUser(principal.getName());
        tweet.setUser(curUser);
        Tweet newTweet = tweetService.createTweet(tweet);
        if (newTweet != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newTweet.getId()).toUri();
            return ResponseEntity.created(location).body(newTweet);
        }

        throw new CreateTweetException("Unknown exception creating tweet!");
    }

    /**
     * Retrieve a tweet by its id
     *
     * @param tweetId the id of the tweet to retrieve
     * @return the tweet if found
     */
    @GetMapping("/tweets/{tweetId}")
    public ResponseEntity<Tweet> getTweet(@PathVariable Long tweetId) {
        Tweet tweet = tweetService.getTweet(tweetId);
        return ResponseEntity.ok(tweet);
    }
}