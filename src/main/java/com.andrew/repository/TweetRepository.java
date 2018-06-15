package com.andrew.repository;

import com.andrew.entity.Tweet;
import com.andrew.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    //TODO look into PagingAndSortingRepository

    List<Tweet> findTop100ByUserOrderByCreatedDesc(User user);

    List<Tweet> findTop100ByUserInOrderByCreatedDesc(List<User> userList);

    List<Tweet> findTop100ByOrderByCreatedDesc();

}