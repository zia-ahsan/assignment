# Assignment Project By Mohammad Ziaul Ahsan
## Overview
The `assignment` project contains two backend microservices and a separate frontend UI service:

### Backend Multi-Module Project (assignment):
* `account-service`: Manages accounts and integrates with transaction-service.
* `transaction-service`: Records and retrieves transactions for accounts.
### Frontend Standalone Project:
`ui-service`: A simple user interface for account and transaction management.
## Prerequisites
1. Java 21 installed on your system.
2. Maven 3.9.8 or higher.
3. H2 in-memory database (automatically configured).
4. IntelliJ IDEA or any preferred IDE.
5. cURL, Postman, or any REST client for testing APIs.
## Backend Multi-Module Project Instructions
### Steps to Build and Run the Backend Services
1. Navigate to the `assignment` project root directory.
2. Build the project:
*  > `mvn clean install`
3. Start the account-service:
* > mvn spring-boot:run -pl account-service
* > Service runs at: http://localhost:8082
4. Start the transaction-service:
* > mvn spring-boot:run -pl transaction-service
* >Service runs at: http://localhost:8081
### Testing the Backend APIs
**1. Account Creation:**
* Endpoint: POST http://localhost:8082/accounts/create
* Parameters: customerId (`Long`), initialCredit (`0 or more`)
* Example:
* > curl -X POST "http://localhost:8082/accounts/create?customerId=1&initialCredit=100"

**2. View Accounts:**

* Endpoint: GET http://localhost:8082/accounts
* Returns a list of accounts with associated transactions.

**3. Record Transaction (called automatically by `account-service`):**

* Endpoint: POST http://localhost:8081/transactions/record
* Parameters: accountId, amount
## Frontend Standalone Project Instructions
### Steps to Build and Run the UI Service
1. Navigate to the `ui-service` project root directory.
2. Build the project:
* > mvn clean install
3. Start the UI service:
* > mvn spring-boot:run
* Service runs at: http://localhost:8083

### Testing the UI
1. Open your browser and go to http://localhost:8083.
2. Home Page:
* Contains two options:
  * **Open Account:** Opens an account with random `customerId` and user-provided `initialCredit (0 or more)`.
  * **View Accounts and Transactions:** Displays a list of accounts and their associated transactions.
### Notes
1. **Database:**
* Both `account-service` and `transaction-service` use in-memory H2 databases.
* Access the H2 console for debugging:
  * account-service: http://localhost:8082/h2-console
  * transaction-service: http://localhost:8081/h2-console
* Default credentials:
  * **URL:** jdbc:h2:mem:account_db, jdbc:h2:mem:transaction_db 
  * **Username:** sa
  * **Password:** password
2. **Functional Testing With UI:**

The `account-service` integrates with the `transaction-service` using REST calls.
Ensure both services are running before testing the integration.
### Support
For any issues or questions, please contact the developer.
* **mobile:** 0645881639
* **email:** zia7770@yahoo.com