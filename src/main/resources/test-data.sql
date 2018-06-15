insert into user (id, name, ldap_uid) values(1, 'Ben', 'ben');
insert into tweet (id, message, user_id, created) values(1, 'Test tweet from Ben', 1, CURRENT_TIMESTAMP());
insert into user (id, name, ldap_uid) values(2, 'Bob', 'bob');
insert into tweet (id, message, user_id, created) values(2, 'Test tweet from Bob', 2, CURRENT_TIMESTAMP() + 1);
insert into user (id, name, ldap_uid) values(3, 'Joe', 'joe');
insert into tweet (id, message, user_id, created) values(3, 'Test tweet from Joe', 3, CURRENT_TIMESTAMP() + 2);
insert into user (id, name, ldap_uid) values(4, 'Jerry', 'jerry');

insert into user_following (user_id, following_id) values(1, 2); --ben follows bob
insert into user_following (user_id, following_id) values(1, 3); --ben follows joe
insert into user_following (user_id, following_id) values(2, 1); --bob follows ben
insert into user_following (user_id, following_id) values(2, 3); --bob follows joe