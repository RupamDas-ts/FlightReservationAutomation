Flight Booking Automation
=========================

Overview
--------

This project automates the flight booking process on a travel website using Selenium, Java, Rest Assured, and Cucumber
framework. It allows users to specify various test combinations such as browser name, version, and platform to run the
tests. The automation includes creating a new account using a temporary email address generated via the One Sec Mail
API, fetching OTP for account creation, and booking flights from a specified origin to a destination on a given date.

## Demo Video

https://github.com/RupamDas-ts/FlightReservationAutomation/assets/123362738/4b8e7ae3-9118-4f44-a217-79c822c3a6f2


Features
--------

* Automated flight booking process on a travel website.
* User-defined test combinations for browser name, version, and platform.
* Creating a new account using a temporary email address.
* Fetching OTP for account creation.
* Booking flights from a specified origin to a destination on a given date.

Technologies Used
-----------------

* Selenium WebDriver: For interacting with the web browser.
* Java: Programming language for test automation.
* Rest Assured: For interacting with the One Sec Mail API.
* Cucumber: BDD framework for test scenario organization and execution.

Getting Started
---------------

To run the tests and use the automation framework, follow these steps:

1. Clone the repository:

   bashCopy code

   `git clone https://github.com/RupamDas-ts/FlightReservationAutomation.git`

2. Navigate to the project directory:

   bashCopy code

   `cd FlightReservationAutomation`

3. Set up your environment:
    * Ensure you have Java installed. You can download it
      from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
    * Install necessary dependencies using Maven:

      bashCopy code

      `mvn clean install`

4. Create `Cucumber.yaml` file with your own credentials the same structure as `Cucumber.sample.yml` and keep that in
   the `TestConfigs` directory

5. Run the tests:

   bashCopy code for Linux or Mac

   ```CUCUMBER_FILTER_TAGS="@flightBooking" mvn test -DENV_NAME=LambdaTest -DsuiteXmlFile=testng.xml -DPARALLEL=2```

   For Windows

   ```set CUCUMBER_FILTER_TAGS="@flightBooking"```

   ```mvn test -DENV_NAME="LambdaTest" -DsuiteXmlFile="testng.xml" -DPARALLEL=2```

6. To re-run failed tests:

   ```mvn test -DENV_NAME=LambdaTest -DsuiteXmlFile=testng-rerun.xml -DPARALLEL=2```
7. To run the same tests multiple times, set the `RUN_N_TIMES` environment variable:
   
   ```CUCUMBER_FILTER_TAGS="@flightBooking" mvn test -DENV_NAME=LambdaTest -DsuiteXmlFile=testng.xml -DPARALLEL=2 -DRUN_N_TIMES=3```

Configuration
-------------

* Modify the `Cucumber.yaml` file to configure credentials to run tests on remote platforms or use `local` as `ENV_NAME`
  to run tests on your local.
* Tests can be run parallelly or serially by providing correct `PARALLEL` value as per requirements like, `-DPARALLEL=1`
  for serial execution `-DPARALLEL=2` for parallel execution of two tests.
* To run tests multiple times, set the `RUN_N_TIMES` environment variable to the desired number of iterations.
* Use `testng.xml` for regular test execution.
* Use `testng-rerun.xml` for re-running failed tests.

Contributing
------------

Contributions are welcome! If you'd like to contribute to this project, feel free to fork the repository and submit a
pull request.

License
-------

This project is licensed under the MIT License. See the LICENSE file for details.

Contact
-------

For any inquiries or issues regarding the project, please feel free to contact me at dasrupam740@example.com.
