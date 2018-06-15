package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.service.TweetService;
import com.andrew.service.UserService;
import com.andrew.utils.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetController.class)
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TweetService tweetService;

    //mocks
    private static User ben;
    private static Tweet tweet;

    @BeforeClass
    public static void initMocks() {
        ben = new User();
        ben.setId(2L);
        ben.setName("Ben");
        ben.setLdapUid("ben");

        tweet = new Tweet();
        tweet.setId(1L);
        tweet.setMessage("hello world");
        tweet.setUser(ben);
    }

    @Test
    @WithMockUser
    public void postTweet() throws Exception {
        given(userService.getUser(anyString())).willReturn(ben);
        given(tweetService.createTweet(any(Tweet.class))).willReturn(tweet);

        mockMvc.perform(post("/tweets").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tweet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(tweet.getMessage())));

        verify(userService, VerificationModeFactory.times(1)).getUser(anyString());
        verify(tweetService, VerificationModeFactory.times(1)).createTweet(any(Tweet.class));
        reset(userService);
        reset(tweetService);

    }

    @Test
    @WithMockUser
    public void getTweet() throws Exception {
        given(tweetService.getTweet(anyLong())).willReturn(tweet);

        mockMvc.perform(get("/tweets/" + tweet.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(tweet.getMessage())));

        verify(tweetService, VerificationModeFactory.times(1)).getTweet(anyLong());
        reset(tweetService);
    }
}