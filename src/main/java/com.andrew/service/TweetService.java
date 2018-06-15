package com.andrew.service;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.exception.TweetNotFoundException;
import com.andrew.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for posting and retrieving tweets
 */
@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Return the users feed which is the tweets from his followers if any
     * else their own tweets if not following anyone
     *
     * @param user the user to get tweets for
     * @return the list of latest tweets
     */
    public List<Tweet> getUserFeed(User user) {
        if (user.getFollowing().size() > 0)
            return this.getLatest100Tweets(user.getFollowing()); //get followers tweets
        else
            return this.getLatest100Tweets(user); //not following get own tweets
    }

    /**
     * Retrieve the tweet by id
     *
     * @param tweetId the tweet id to retrieve
     * @return the tweet else exception if not found
     */
    public Tweet getTweet(Long tweetId) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isPresent())
            return tweet.get();
        throw new TweetNotFoundException("Unable to find tweet with Id: " + tweetId);
    }

    /**
     * Retrieve the last 100 latest tweets for all users
     *
     * @return the list of latest tweets
     */
    public List<Tweet> getLatest100Tweets() {
        return tweetRepository.findTop100ByOrderByCreatedDesc();
    }

    /**
     * Retrieve the last 100 tweets for the user passed in
     *
     * @param user the user to get tweets for
     * @return the list of tweets
     */
    public List<Tweet> getLatest100Tweets(User user) {
        return tweetRepository.findTop100ByUserOrderByCreatedDesc(user);
    }

    /**
     * Retrieve the last 100 tweets for the users passed in
     *
     * @param userList the list of users to retrieve tweets for
     * @return the list of tweets
     */
    public List<Tweet> getLatest100Tweets(List<User> userList) {
        return tweetRepository.findTop100ByUserInOrderByCreatedDesc(userList);
    }

    /**
     * Create a new tweet
     *
     * @param tweet the new tweet to create with message and user set
     * @return the new tweet with id and created date set
     */
    public Tweet createTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

}
