Scenario: Get user record
Meta: @type get
Given I can get lists user
Then I receive 200 status code response

Scenario: Get user record by UserID
Meta: @type get
Given I get user by id 1
Then I receive 200 status code response
And I receive user id 1

Scenario: Can not set user record with wrong UserID
Meta: @type negative
Given I can not get user by ID (userId=999)
Then I receive 404 status code response

Scenario: Create user record
Meta: @type post
Given I can create a new user with first name is John, last name is Doe, 30 years old
Then I receive 201 status code response
And I receive a new user with right info

Scenario: Update user record
Meta: @type put
Given I can update user (userId=1)
Then I receive 200 status code response
And user is updated

Scenario: Delete user record
Meta: @type delete
Given I can delete user (userId=1)
Then I receive 200 status code response
And I can not find user (userId=1)

Scenario: Create user with example data table
Meta: @type post
Given I create user with <firstName>, <lastName>, <age>
Then I receive 201 status code response
And I receive a new user with right info

Examples:
| firstName | lastName | age |
| Nick | Wild | 32 |
| Judy | Hopps | 25 |
| Gary | Snake | 27 |
| John | Wick | 39 |
