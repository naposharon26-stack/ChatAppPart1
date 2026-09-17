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
    private String firstName;
    private String lastName;
    private String UserName;
    private String Password;
    private String CellphoneNumber;
    
    private boolean isRegistered = false;

    public Login(String firstName,String lastName, String UserName,String Password, String CellphoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.UserName = UserName;
        this.Password = Password;
        this.CellphoneNumber = CellphoneNumber;
    }
     public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getusername() {
        return UserName;
    }

    public String getpassword() {
        return Password;
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
      public boolean checkUserName() {
        if (UserName == null) {
            return false;
        }
        return UserName.contains("_") && UserName.length() <= 5;
    }
    
    public boolean checkPasswordComplexity() {
        if (Password == null) {
            return false;
        }
    boolean hasMinLength = Password.length() >= 8;
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(Password).find();
        boolean hasNumber = Pattern.compile("[0-9]").matcher(Password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(Password).find();
        return hasMinLength && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Cell phone number must contain the South African international
     * country code (+27) followed by the number, which must be no more
     * than ten characters long (after the country code).
     */
    public boolean checkCellPhoneNumber() {
        if (CellphoneNumber == null) {
            return false;
        }
        return Pattern.matches("^\\+27\\d{1,10}$", CellphoneNumber);
    }

    // ---------- Message helpers (match required output text) ----------

    public String getUsernameMessage() {
        return checkUserName()
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.";
    }

    public String getPasswordMessage() {
        return checkPasswordComplexity()
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, and a special character.";
    }

    public String getCellPhoneMessage() {
        return checkCellPhoneNumber()
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.";
    }

    /**
     * Runs all registration checks and returns the correct message.
     * Registration only succeeds when username, password and cell number
     * are all valid.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return getUsernameMessage();
        }
        if (!checkPasswordComplexity()) {
            return getPasswordMessage();
        }
        if (!checkCellPhoneNumber()) {
            return getCellPhoneMessage();
        }
        isRegistered = true;
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nRegistration successful.";
    }

    // ---------- Login ----------

    /**
     * Verifies that the entered username and password match the stored
     * credentials for a user that has already registered.
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (!isRegistered) {
            return false;
        }
        return UserName.equals(enteredUsername) && Password.equals(enteredPassword);
    }

    /**
     * Returns the appropriate message for the outcome of a login attempt.
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}