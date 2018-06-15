package com.andrew.repository;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("classpath:/jpa-test.sql")
public class TweetRepositoryTest {

    @Autowired
    private TweetRepository tweetRepository;

    //mocks
    private static User ben;
    private static User bob;

    @BeforeClass
    public static void initMocks() {
        ben = new User();
        ben.setId(1L);

        bob = new User();
        bob.setId(2L);
    }

    @Test
    public void findTop100ByOrderByCreatedDescTest() {
        List<Tweet> found = tweetRepository.findTop100ByOrderByCreatedDesc();
        assertThat(found.get(0).getMessage()).isEqualTo("Test tweet from Joe");
        assertThat(found.get(1).getMessage()).isEqualTo("Test tweet from Bob");
        assertThat(found.get(2).getMessage()).isEqualTo("Test tweet from Ben");
    }

    @Test
    public void findTop100ByUserInOrderByCreatedDescTest() {
        List<User> userList = Arrays.asList(ben, bob);
        List<Tweet> found = tweetRepository.findTop100ByUserInOrderByCreatedDesc(userList);
        assertThat(found.get(0).getMessage()).isEqualTo("Test tweet from Bob");
        assertThat(found.get(1).getMessage()).isEqualTo("Test tweet from Ben");
    }

    @Test
    public void findTop100ByUserOrderByCreatedDescTest() {
        List<Tweet> found = tweetRepository.findTop100ByUserOrderByCreatedDesc(ben);
        assertThat(found.get(0).getMessage()).isEqualTo("Test tweet from Ben");
    }

}
