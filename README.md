# intuit2018

Business Case:

As part of enhancing our internal services which are available to our employees, we would like to build a Twitter like solution for our employees,
where employees can tweet and have following.

High Level Requirements:
• We have 10K employees.
• Employees can follow their colleagues, post (or tweet) messages to their following.
• Use corporate LDAP for user Management.
• On an average, every employee will send approximately 10 messages a day to their following.
• On the home page we need to show 100 most recent tweets. Optionally you can support pagination.
• You are welcome to assume unspecified requirements to make it better for the customers.
• Come prepared with High level Architecture and Design.
• You are expected to explain the rationale for your choice of technologies and architectural patterns.
• This is an open-ended exercise. The goal is to demonstrate how well you design a system with limited requirements.

Programming Problem:
In the above exercise, please build a RESTful service.
• /feed : To list 100 recent tweets for the logged in user.
• In memory database is sufficient. Optionally, you are welcome to use a persistent data store of your choice.
• You are encouraged but not required to take advantage of a service code-generation framework of your choice when performing this exercise.

Note: Please check-in the code to GitHub and share the link 2 days in advance to the interview

--------------------

Building and Running:

$ mvn clean package
$ java -jar target/intuit-interview-0.1.0.jar

Run in postman using: postman/Intuit Tweet.postman_connection.json

Else run in browser:
Home page: http://localhost:8080/
Get Feed: http://localhost:8080/feed [login with ben/benspassword]
Get Followers: http://localhost:8080/follows

H2 Console: http://localhost:8080/h2-console [login with sa/no password needed]
