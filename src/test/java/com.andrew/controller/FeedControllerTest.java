package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import com.andrew.service.TweetService;
import com.andrew.service.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedController.class)
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TweetService tweetService;

    //mocks
    private static List<Tweet> tweetList;
    private static Tweet tweet1;
    private static Tweet tweet2;
    private static User ben;

    @BeforeClass
    public static void initMocks() {
        tweet1 = new Tweet();
        tweet1.setId(1L);
        tweet1.setMessage("hello world 1");

        tweet2 = new Tweet();
        tweet2.setId(1L);
        tweet2.setMessage("hello world 2");

        tweetList = new ArrayList<>();
        tweetList.add(tweet1);
        tweetList.add(tweet2);

        ben = new User();
        ben.setId(2L);
        ben.setName("Ben");
        ben.setLdapUid("ben");
    }

    @Test
    @WithMockUser
    public void getFeed() throws Exception {
        given(userService.getUser(anyString())).willReturn(ben);
        given(tweetService.getUserFeed(any(User.class))).willReturn(tweetList);

        mockMvc.perform(get("/feed").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tweetList.size())))
                .andExpect(jsonPath("$[0].message", is(tweet1.getMessage())))
                .andExpect(jsonPath("$[1].message", is(tweet2.getMessage())));

        verify(userService, VerificationModeFactory.times(1)).getUser(anyString());
        verify(tweetService, VerificationModeFactory.times(1)).getUserFeed(any(User.class));
        reset(userService);
        reset(tweetService);
    }
}