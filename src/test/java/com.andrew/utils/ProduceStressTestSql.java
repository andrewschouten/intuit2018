package com.andrew.utils;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Andrew.schouten on 6/14/18.
 */
public class ProduceStressTestSql {

    @Test
    public void produceStressData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("stress-test-data.sql"));

            //10k users
            int tweet = 0;

            //ben follows 500 people
            writer.write("insert into user (id, name, ldap_uid) values(1, 'Ben', 'ben'); \n");

            //everyone follows Bob the CTO
            writer.write("insert into user (id, name, ldap_uid) values(2, 'Bob', 'bob'); \n");

            for (int user=3; user<10000; user++) {
                System.out.println(user);

                writer.write("insert into user (id, name, ldap_uid) values(");
                writer.write(Integer.toString(user));
                writer.write(", 'User");
                writer.write(Integer.toString(user));
                writer.write("', 'user");
                writer.write(Integer.toString(user));
                writer.write("'); \n");

                //tweets 100 times (10 days worth of data)
                for(int tweetCnt=0;tweetCnt<100; tweetCnt++) {
                    tweet++;
                    writer.write("  insert into tweet (id, message, user_id, created) values(");
                    writer.write(Integer.toString(tweet));
                    writer.write(", 'Message");
                    writer.write(Integer.toString(tweet));
                    writer.write(" From User");
                    writer.write(Integer.toString(user));
                    writer.write("', ");
                    writer.write(Integer.toString(user));
                    writer.write(", CURRENT_TIMESTAMP()); \n");
                }

                //following last 10 users
                if (user > 20) {
                    for (int follow=user-1; follow>user-11; follow--) {
                        writer.write("    insert into user_following (user_id, following_id) values(");
                        writer.write(Integer.toString(user));
                        writer.write(", ");
                        writer.write(Integer.toString(follow));
                        writer.write("); \n ");
                    }
                }

                //ben follows 500 users
                if (user < 500) {
                    writer.write("insert into user_following (user_id, following_id) values(1, ");
                    writer.write(Integer.toString(user));
                    writer.write("); \n");
                }

                //all users follow Bob
                writer.write("insert into user_following (user_id, following_id) values(");
                writer.write(Integer.toString(user));
                writer.write(" , 2); \n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
