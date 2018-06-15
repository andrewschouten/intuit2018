package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Home endpoint should display to last 100 posts from all users
 */
@RestController
public class HomeController {

    @Autowired
    private TweetService tweetService;

    /**
     * View the home page, no authentication required
     *
     * @return the home page which lists the latest tweets
     */
    @GetMapping("/")
    public ResponseEntity<List<Tweet>> home() {
        return ResponseEntity.ok(tweetService.getLatest100Tweets());
    }
}