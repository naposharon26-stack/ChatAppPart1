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
        Login login = new Login();

        assertTrue(login.checkUserName("kyl_1"));
    }

    // Test 2: Username does not contain an underscore
    @Test
    public void testUsernameWithoutUnderscore() {
        Login login = new Login();

        assertFalse(login.checkUserName("kyle1"));
    }

    // Test 3: Username is more than five characters
    @Test
    public void testUsernameTooLong() {
        Login login = new Login();

        assertFalse(login.checkUserName("kyle_123"));
    }
  // Test 4: Valid password
    @Test
    public void testValidPassword() {
        Login login = new Login();

        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    // Test 5: Password is too short
    @Test
    public void testPasswordTooShort() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("Pass1!"));
    }

    // Test 6: Password has no capital letter
    @Test
    public void testPasswordWithoutCapital() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("password1!"));
    }
}