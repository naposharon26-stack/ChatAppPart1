/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Login {
     // ---------------------------------------------------------------------
    // Output messages (kept as constants so the app and the tests agree)
    // ---------------------------------------------------------------------
    public static final String USERNAME_SUCCESS =
            "Username successfully captured.";
    public static final String USERNAME_FAILURE =
            "Username is not correctly formatted; please ensure that your username "
            + "contains an underscore and is no more than five characters in length.";

    public static final String PASSWORD_SUCCESS =
            "Password successfully captured.";
    public static final String PASSWORD_FAILURE =
            "Password is not correctly formatted; please ensure that the password "
            + "contains at least eight characters, a capital letter, a number, and "
            + "a special character.";

    public static final String CELL_SUCCESS =
            "Cell phone number successfully added.";
    public static final String CELL_FAILURE =
            "Cell phone number incorrectly formatted or does not contain "
            + "international code.";

    public static final String LOGIN_FAILURE =
            "Username or password incorrect, please try again.";

    /**
     * A cell phone number must start with a "+", be followed by an
     * international dialling code of 1 to 3 digits, and then by a subscriber
     * number of no more than ten digits.
     */
    private static final Pattern CELL_PATTERN =
            Pattern.compile("^\\+[0-9]{1,3}[0-9]{1,10}$");

    // ---------------------------------------------------------------------
    // Stored registration details
    // ---------------------------------------------------------------------
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    /** Creates a Login object with no user registered yet. */
    public Login() {
    }

    /**
     * Creates a Login object and stores the user's name, which is used in the
     * welcome message after a successful login.
     *
     * @param firstName the user's first name
     * @param lastName  the user's last name
     */
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ---------------------------------------------------------------------
    // Validation methods
    // ---------------------------------------------------------------------

    /**
     * Checks that a username contains an underscore and is no more than five
     * characters long.
     *
     * @param username the username entered by the user
     * @return true if the username is correctly formatted, otherwise false
     */
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks that a password is at least eight characters long and contains a
     * capital letter, a number and a special character.
     *
     * @param password the password entered by the user
     * @return true if the password meets the complexity rules, otherwise false
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks, using a regular expression, that a cell phone number contains an
     * international country code and that the number that follows it is no more
     * than ten characters long.
     *
     * @param cellPhoneNumber the cell phone number entered by the user
     * @return true if the number is correctly formatted, otherwise false
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        return CELL_PATTERN.matcher(cellPhoneNumber.trim()).matches();
    }

    // ---------------------------------------------------------------------
    // Registration and login
    // ---------------------------------------------------------------------

    /**
     * Validates the registration details and, if all of them are correct,
     * stores them so that the user can log in.
     *
     * @param username        the requested username
     * @param password        the requested password
     * @param cellPhoneNumber the user's cell phone number
     * @return a message describing the outcome of the registration attempt
     */
    public String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return USERNAME_FAILURE;
        }
        if (!checkPasswordComplexity(password)) {
            return PASSWORD_FAILURE;
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return CELL_FAILURE;
        }

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        return USERNAME_SUCCESS + " " + PASSWORD_SUCCESS + " " + CELL_SUCCESS;
    }

    /**
     * Verifies that the login details entered match the details stored when the
     * user registered.
     *
     * @param enteredUsername the username entered at the login screen
     * @param enteredPassword the password entered at the login screen
     * @return true if both values match the stored details, otherwise false
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.username == null || this.password == null) {
            return false;
        }
        return this.username.equals(enteredUsername)
                && this.password.equals(enteredPassword);
    }

    /**
     * Returns the message that must be shown to the user after a login attempt.
     *
     * @param loginSuccessful the result returned by loginUser
     * @return the welcome message on success, or the failure message otherwise
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }
        return LOGIN_FAILURE;
    }

    // ---------------------------------------------------------------------
    // Getters and setters
    // ---------------------------------------------------------------------

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

