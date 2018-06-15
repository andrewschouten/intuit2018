package com.andrew.service;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.repository.TweetRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TweetServiceTest {

    @TestConfiguration
    static class TweetServiceConfiguration {
        @Bean
        public TweetService tweetService() {
            return new TweetService();
        }
    }

    @Autowired
    private TweetService tweetService;

    @MockBean
    private TweetRepository tweetRepository;

    //mock
    private static User alex;
    private static User ben;

    private static List<Tweet> alexTweetList;
    private static Tweet tweet1;
    private static Tweet tweet2;

    @BeforeClass
    public static void setUp() {
        alex = new User();
        alex.setId(1L);
        alex.setName("Alex");
        alex.setLdapUid("alex");

        ben = new User();
        ben.setId(2L);
        ben.setName("Ben");
        ben.setLdapUid("ben");
        ben.addFollowing(alex);

        tweet1 = new Tweet();
        tweet1.setId(1L);
        tweet1.setMessage("hello world from alex");
        tweet1.setUser(alex);

        tweet2 = new Tweet();
        tweet2.setId(2L);
        tweet2.setMessage("hello world from alex again");
        tweet2.setUser(alex);

        alexTweetList = new ArrayList<>();
        alexTweetList.add(tweet1);
        alexTweetList.add(tweet2);
    }

    @Test
    public void getUserFeedTest() {
        when(tweetRepository.findTop100ByUserInOrderByCreatedDesc(anyList())).thenReturn(alexTweetList);

        List<Tweet> bensFeed = tweetService.getUserFeed(ben);
        assertThat(bensFeed.size()).isEqualTo(alexTweetList.size());
        assertThat(bensFeed.get(0).getMessage()).isEqualTo(tweet1.getMessage());
        assertThat(bensFeed.get(1).getMessage()).isEqualTo(tweet2.getMessage());
    }

    @Test
    public void getTweetTest() {
        when(tweetRepository.findById(anyLong())).thenReturn(Optional.of(tweet1));

        Tweet found = tweetService.getTweet(tweet1.getId());
        assertThat(found.getMessage()).isEqualTo(tweet1.getMessage());
    }

    @Test
    public void createTweetTest() {
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet1);

        Tweet saved = tweetService.createTweet(tweet1);
        assertThat(saved.getMessage()).isEqualTo(tweet1.getMessage());
    }

    @Test
    public void getLatest100TweetsTest() {
        when(tweetRepository.findTop100ByOrderByCreatedDesc()).thenReturn(alexTweetList);

        List<Tweet> latestList = tweetService.getLatest100Tweets();
        assertThat(latestList.size()).isEqualTo(alexTweetList.size());
        assertThat(latestList.get(0).getMessage()).isEqualTo(tweet1.getMessage());
        assertThat(latestList.get(1).getMessage()).isEqualTo(tweet2.getMessage());
    }

    @Test
    public void getLatest100TweetsByUserTest() {
        when(tweetRepository.findTop100ByUserOrderByCreatedDesc(any(User.class))).thenReturn(alexTweetList);

        List<Tweet> latestForUser = tweetService.getLatest100Tweets(ben);
        assertThat(latestForUser.size()).isEqualTo(alexTweetList.size());
        assertThat(latestForUser.get(0).getMessage()).isEqualTo(tweet1.getMessage());
        assertThat(latestForUser.get(1).getMessage()).isEqualTo(tweet2.getMessage());
    }

    @Test
    public void getLatest100TweetsByUserListTest() {
        when(tweetRepository.findTop100ByUserInOrderByCreatedDesc(anyList())).thenReturn(alexTweetList);

        List<Tweet> latestForUserList = tweetService.getLatest100Tweets(ben.getFollowing());
        assertThat(latestForUserList.size()).isEqualTo(alexTweetList.size());
        assertThat(latestForUserList.get(0).getMessage()).isEqualTo(tweet1.getMessage());
        assertThat(latestForUserList.get(1).getMessage()).isEqualTo(tweet2.getMessage());
    }
}
