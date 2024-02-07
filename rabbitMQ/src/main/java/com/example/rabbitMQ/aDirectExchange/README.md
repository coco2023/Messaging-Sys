### 1\. Direct Exchange

**Scenario**: An application that processes user actions such as "login" and "signup". Each action needs to be processed by a different service.

*   **Exchange**: A direct exchange named "UserActions".
*   **Queues**: Two queues, "LoginQueue" for login actions and "SignupQueue" for signup actions.
*   **Producers**: The application sends messages about user actions to the exchange.
*   **Consumers**: Separate services for handling logins and signups.

**How it works**: The producer sends a message with a routing key "login" for login actions, and "signup" for signup actions. The "LoginQueue" is bound to the "UserActions" exchange with a routing key "login", and the "SignupQueue" with "signup". The exchange routes messages to the appropriate queue based on the exact match of the routing key.
