/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapppart1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    public LoginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
  // Test 1: Valid username
    @Test
    public void testValidUsername() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        assertTrue(login.checkUserName());
    }

    // Test 2: Username does not contain an underscore
    @Test
    public void testUsernameWithoutUnderscore() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyle1",
                "Password1!",
                "+27838968976"
        );

        assertFalse(login.checkUserName());
    }

    // Test 3: Username is more than five characters
    @Test
    public void testUsernameTooLong() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyle_123",
                "Password1!",
                "+27838968976"
        );

        assertFalse(login.checkUserName());
    }

    // Test 4: Valid password
    @Test
    public void testValidPassword() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        assertTrue(login.checkPasswordComplexity());
    }

    // Test 5: Password is too short
    @Test
    public void testPasswordTooShort() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Pass1!",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    // Test 6: Password has no capital letter
    @Test
    public void testPasswordWithoutCapital() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "password1!",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    // Test 7: Password has no number
    @Test
    public void testPasswordWithoutNumber() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password!",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    // Test 8: Password has no special character
    @Test
    public void testPasswordWithoutSpecialCharacter() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    // Test 9: Valid South African cell phone number
    @Test
    public void testValidCellPhoneNumber() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        assertTrue(login.checkCellPhoneNumber());
    }

    // Test 10: Cell phone number without international code
    @Test
    public void testCellPhoneWithoutInternationalCode() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "0838968976"
        );

        assertFalse(login.checkCellPhoneNumber());
    }

    // Test 11: Successful registration
    @Test
    public void testSuccessfulRegistration() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        String result = login.registerUser();

        assertEquals(
                "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.\n"
                + "Registration successful.",
                result
        );
    }

    // Test 12: Failed registration with invalid username
    @Test
    public void testFailedRegistration() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyle123",
                "Password1!",
                "+27838968976"
        );

        String result = login.registerUser();

        assertEquals(
                "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.",
                result
        );
    }

    // Test 13: Successful login
    @Test
    public void testSuccessfulLogin() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        login.registerUser();

        assertTrue(login.loginUser("kyl_1", "Password1!"));
    }

    // Test 14: Failed login with incorrect password
    @Test
    public void testFailedLogin() {
        Login login = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Password1!",
                "+27838968976"
        );

        login.registerUser();

        assertFalse(login.loginUser("kyl_1", "WrongPassword1!"));
    }
}