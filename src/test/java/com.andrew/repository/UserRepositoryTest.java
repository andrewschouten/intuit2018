package com.andrew.repository;

import com.andrew.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("classpath:/jpa-test.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByLdapUidTest() {
        User ben = new User();
        ben.setName("Ben");
        ben.setLdapUid("ben");

        User found = userRepository.findByLdapUid(ben.getLdapUid()).get();
        assertThat(found.getName()).isEqualTo(ben.getName());
    }

}
