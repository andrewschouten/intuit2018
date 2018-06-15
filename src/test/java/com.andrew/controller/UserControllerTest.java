package com.andrew.controller;

import com.andrew.entity.User;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //mocks
    private static User ben;
    private static User alex;

    @BeforeClass
    public static void initMocks() {
        alex = new User();
        alex.setId(1L);
        alex.setName("Alex");
        alex.setLdapUid("alex");

        ben = new User();
        ben.setId(2L);
        ben.setName("Ben");
        ben.setLdapUid("ben");
        ben.addFollowing(alex); //ben follows alex
    }

    @Test
    @WithMockUser
    public void getFollowingTest() throws Exception {
        given(userService.getUser(anyString())).willReturn(ben);

        mockMvc.perform(get("/follows").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())));

        verify(userService, VerificationModeFactory.times(1)).getUser(anyString());
        reset(userService);
    }

    @Test
    @WithMockUser
    public void followUserTest() throws Exception {
        given(userService.getUser(anyString())).willReturn(ben);
        given(userService.followUser(any(User.class), anyLong())).willReturn(ben);

        mockMvc.perform(post("/follows/" + alex.getId()).with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())));

        verify(userService, VerificationModeFactory.times(1)).getUser(anyString());
        verify(userService, VerificationModeFactory.times(1)).followUser(any(User.class), anyLong());
        reset(userService);
    }
}