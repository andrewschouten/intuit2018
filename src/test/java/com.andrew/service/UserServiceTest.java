package com.andrew.service;

import com.andrew.entity.User;
import com.andrew.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    //mock
    private static User alex;
    private static User ben;

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
    }

    @Test
    public void getUserByLdapUidTest() {
        when(userRepository.findByLdapUid(anyString())).thenReturn(Optional.of(alex));

        User found = userService.getUser(alex.getLdapUid());
        assertThat(found.getId()).isEqualTo(alex.getId());
        assertThat(found.getLdapUid()).isEqualTo(alex.getLdapUid());
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    @Test
    public void getUserByIdTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(alex));

        User found = userService.getUser(alex.getId());
        assertThat(found.getId()).isEqualTo(alex.getId());
        assertThat(found.getLdapUid()).isEqualTo(alex.getLdapUid());
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    @Test
    public void followUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(ben);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(alex));

        User saved = userService.followUser(ben, alex.getId());
        assertThat(saved.getId()).isEqualTo(ben.getId());
        assertThat(saved.getLdapUid()).isEqualTo(ben.getLdapUid());
        assertThat(saved.getName()).isEqualTo(ben.getName());
    }
}
