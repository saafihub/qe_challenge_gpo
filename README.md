## About
QE Technical Assignment : Playwright(UI)+Rest Assured(API)+Java

This Playwright-based Java automation framework is structured using the Page Object Model (POM) and Playwright Fixtures. It is designed to accommodate multiple types of tests—API, UI functionality, with BDD within a single framework. The framework is built with scalability and extensibility in mind to add more test types with enhanced 
maintainability, reusability and also by separating concerns (e.g., test logic, page objects, and test data), the framework makes it easier to manage and scale as the application grows.

In this Functional automation framework(Hybrid), Covered all 6 user stories through comprehensive End-to-End (E2E) testing. This approach integrated multiple testing layers, including API, UI, and database validations, ensuring a robust assessment of our application’s functionality and performance. 

By combining various testing methodologies not only ensured complete coverage of user requirements also improved the clarity and communication of test cases among stakeholders. This holistic approach significantly contributes to delivering a high-quality product that aligns with user expectations.

For More Info : Automation Test Strategy document.docx

## Framework Structure
      src
      ├── main
      │    ├── java(org.gpo)
      │    │   ├── driver          # driver files
      │    │   ├── pages           # Page Object Model (POM) classes
      │    │   ├── utils           # Utility classes (e.g., helper methods, constants)
      │    └── test
      │        ├── java
      │            ├── tests      
      │            │   ├── hooks   # application hooks
      │            │   ├── steps   # step definition files
      │            │   ├── testrunner   # runner file
      │            ├── resources   
      │            │   ├── config   # configuration files
      │            │   ├── dataProviders   # Test data
      │            │   ├── features   # feature files
      │            │   ├── cucumber.properties  
      pom.xml # include all lib dependencies
      README.md # Instruction file.

   

## Prerequisites
   ```
   Java JDK 11 or higher - https://www.oracle.com/java/technologies/javase-downloads.html
   Node.js and npm (required for Playwright) - https://nodejs.org/
   Maven - https://maven.apache.org/download.cgi
   Playwright - Installed via npm.
   ```
1. Clone the repository to your local machine:  `qe_challenge_gpo`.

   ```
   git clone https://github.com/saafihub/qe_challenge_gpo.git
   cd qe_challenge_gpo
   ```
3. Install necessary dependencies.

   ```
   Ensure Java, Node.js, and Maven are correctly installed by verifying their versions:
   java -version
   node -v
   npm -v
   mvn -v
   ```
4. Install browsers.

   ```
   npx playwright install
   ```
5. Build the Project.

   ```
   mvn clean install
   ```
   
5. Run tests.

   ```
   # Run ui and api tests.
    To run tests --> mvn clean test
 
   ```
6. View reports and logs.
   ```
    1. Check reports for all tests --> target>>cucumber-reports
    ```
