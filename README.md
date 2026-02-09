# BDD JBehave API Automation

Tech stack:
- Maven
- JBehave
- REST Assured
- Allure

Run test:
- Run story: -Dtest=UserApiStoryRunner
- Run with filter:  mvn clean test -Dtest=UserApiStoryRunner "-Djbehave.metaFilters=+type delete" ('delete' could be changed depending on need)
- Open allure: allure serve target/allure-results

