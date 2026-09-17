 Chat App Part 1

 Project Overview

This project is a Java-based Chat Application developed as part of a programming project. Part 1 focuses on user registration, validation and login functionality.

The application allows a user to enter their personal details, create a username and password, provide a South African cellphone number, and then log in using their registered details.

Features

The application includes the following features:

* User registration
* First name and last name capture
* Username validation
* Password complexity validation
* South African cellphone number validation
* Registration status messages
* User login
* Successful and unsuccessful login messages
* JUnit 5 unit tests

 Validation Rules

Username

The username must:

* Contain an underscore (`_`)
* Be no more than 5 characters long

Example of a valid username:

`kyl_1`

 Password

The password must:

* Contain at least 8 characters
* Contain at least one capital letter
* Contain at least one number
* Contain at least one special character

Example of a valid password:

`Password1!`

Cellphone Number

The cellphone number must:

* Use the South African international country code `+27`
* Contain between 1 and 10 digits after the country code

Example:

`+27838968976`

 Project Files

The project contains the following main files:

`ChatAppPart1.java`

This is the main application file. It:

* Displays the registration screen
* Collects user information
* Creates a `Login` object
* Checks the registration details
* Registers the user
* Allows the user to log in
* Displays the login result

 `Login.java`

This class contains the main registration and authentication functionality.

It includes methods such as:

* `checkUserName()`
* `checkPasswordComplexity()`
* `checkCellPhoneNumber()`
* `registerUser()`
* `loginUser()`
* `returnLoginStatus()`

 `LoginTest.java`

This class contains 14 JUnit 5 tests used to test the functionality of the `Login` class.

The tests cover:

1. Valid username
2. Username without an underscore
3. Username longer than five characters
4. Valid password
5. Password that is too short
6. Password without a capital letter
7. Password without a number
8. Password without a special character
9. Valid cellphone number
10. Cellphone number without an international code
11. Successful registration
12. Failed registration
13. Successful login
14. Failed login

Technologies Used

Java
NetBeans IDE
JUnit 5
Regular Expressions (Regex)

 How to Run the Application

1. Clone or download the repository.
2. Open the project in NetBeans.
3. Make sure all Java files are included in the project.
4. Run `ChatAppPart1.java`.
5. Enter the requested registration details.
6. If all details are valid, the application will allow the user to log in.

 How to Run the Tests

1. Open the project in NetBeans.
2. Make sure JUnit 5 is configured.
3. Open `LoginTest.java`.
4. Run the test class.
5. The 14 tests should execute and display their results.

 Example

A valid registration could use:

```text
First name: Kyle
Last name: Smith
Username: kyl_1
Password: Password1!
Cell phone number: +27838968976
```

After successful registration, the user can enter the same username and password to log in.

 References

Oracle (2026) Pattern (Java SE 24 & JDK 24). Available at: https://docs.oracle.com/en/java/javase/24
