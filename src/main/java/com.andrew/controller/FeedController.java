package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.service.TweetService;
import com.andrew.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Feed endpoint should display to last 100 posts from the current users followers
 */
@RestController
public class FeedController {

    private static final Logger log = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    /**
     * Retrieve the current users feed, this will be the tweets of the users they follow
     * else their own tweets if they don't follow anyone
     *
     * @param principal the current logged in user
     * @return the list of tweets in the users feed
     */
    @GetMapping("/feed")
    public ResponseEntity<List<Tweet>> getFeed(Principal principal) {
        User curUser = userService.getUser(principal.getName());
        return ResponseEntity.ok(tweetService.getUserFeed(curUser));
    }
}