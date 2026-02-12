Meta:
@category login

Scenario: Login successful with valid credentials
Given I login with username "emilys", password "emilyspass"
Then I receive 201 status code response
And I receive a valid access token

Scenario: Login failed with wrong password 
Given I login with username "emilys", password "1234567"
Then I receive 401 status code response
And I receive an error message "Invalid"