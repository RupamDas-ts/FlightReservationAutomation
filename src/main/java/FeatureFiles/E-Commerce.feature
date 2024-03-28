Feature: Create an MMT account and perform basic functionalities via Automation

  Scenario Outline: User creates a new MMT Account
    Given Setup User Details for new Account creation
    Then Create WedDriver with given <capabilities>
    Then Open MMT Log in page
    And Create new Account
    Examples:
      | capabilities                                                            |
      | browserName=chrome,platform=win10,version=latest-4,network.http2=false  |