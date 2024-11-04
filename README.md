# User service backend automation

`user-svc-qa` is a testing framework designed for verifying all operations performed by the user service. This service includes creating users, updating user information, fetching user details, and more. The framework ensures reliable testing with mock endpoints and comprehensive test coverage.

## Technology Stack

This framework is built using the following technologies:

- **Java**: Core language for development and logic implementation.
- **Rest Assured**: A tool for testing RESTful services.
- **Cucumber**: For Behavior-Driven Development (BDD) to create human-readable tests.
- **Wiremock**: For mocking and simulating REST APIs.
- **jUnit**: The test runner for executing test cases.
- **Maven**: A build automation tool used for project management.

## Framework Overview

This testing framework is designed to validate all user operations via REST endpoints, simulated with **Wiremock**. It ensures robust API testing and supports the following scenarios:

- Creation and update of user records.
- Retrieval of user details.
- Handling cases with missing or incomplete user data.

### Assumptions

To simplify the testing setup, some assumptions are made:

1. The system already contains two users with IDs `1` and `2`.
2. One user exists without an address in the database for negative test scenarios.

## Prerequisites

Ensure the following are installed and properly configured on your system:

- **Java Development Kit (JDK) 8 or above**
- **Apache Maven 3.6 or above**

## How to Run Test Cases

Follow these steps to execute the test cases:

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd user-svc-qa
   ```

2. **Run the tests**:
   Execute the following command from the root directory:
   ```
   mvn clean test
   ```

## Viewing Test Execution Reports

To access the test execution report locally:

1. Navigate to `target` -> `Cucumber-reports` folder.
2. Locate the `cucumber.html` file.
3. Right-click on the `html` file and choose `Open with` -> select your preferred browser.

## Project Structure

- **src/test/java/stepDefs**: Contains test scripts written in Java with Rest Assured and Cucumber.
- **src/test/resources**: Stores Cucumber feature files.
- **src/test/java/wiremock**: Contains mock server configurations and response mappings.
- **pom.xml**: Maven configuration file for managing dependencies and build plugins.

## Key Features

- **Mocked Endpoints**: All endpoints are mocked using **Wiremock** for isolated testing.
- **BDD with Cucumber**: Tests are written in an easy-to-read format for better collaboration between technical and non-technical stakeholders.
- **Customizable Test Scenarios**: Easily modify and create new test cases by updating the feature files.

## Future Enhancements

Potential improvements for the project include:

- **Integration with CI/CD Pipelines** for automated testing.
- **Enhanced Reporting** using plugins like **Extent Reports** for detailed analysis.