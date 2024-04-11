Feature: Create an CheapFlights account and perform basic functionalities via Automation

  @flightBooking
  Scenario Outline: User creates a new CheapFlights Account and Perform Action
    Given Initialize soft assert
    And Setup User Details for new Account creation
    Then Create WedDriver with given <capabilities>
    Then Open MMT Log in page
    And Create new Account
    Then Book Flight From <origin> to <Destination> for given <date> of next month
    Then I assert all via soft assert
    Examples:
      | capabilities                                                  | origin | Destination | date |
      | browserName=chrome,platform=win10,version=latest,network=true | CCU    | DEL         | 20   |
      | browserName=chrome,platform=win11,version=latest,network=true | DEL    | CCU         | 21   |