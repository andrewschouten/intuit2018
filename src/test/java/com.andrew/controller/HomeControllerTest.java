package com.andrew.controller;

import com.andrew.entity.Tweet;
import com.andrew.service.TweetService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetService tweetService;

    //mocks
    private static List<Tweet> tweetList;
    private static Tweet tweet1;
    private static Tweet tweet2;

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
    }

    @Test
    @WithMockUser //TODO should not be needed not loading my security config
    public void home() throws Exception {
        given(tweetService.getLatest100Tweets()).willReturn(tweetList);

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tweetList.size())))
                .andExpect(jsonPath("$[0].message", is(tweet1.getMessage())))
                .andExpect(jsonPath("$[1].message", is(tweet2.getMessage())));

        verify(tweetService, VerificationModeFactory.times(1)).getLatest100Tweets();
        reset(tweetService);
    }
}