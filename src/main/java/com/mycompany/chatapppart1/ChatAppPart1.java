/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppart1;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatAppPart1 {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);

        System.out.println("=== Chat App Registration ===");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        Login login = new Login(firstName, lastName);

        // Keep asking until the details are valid and the user is registered.
        do {
            System.out.print("Enter a username: ");
            String username = input.nextLine();

            System.out.print("Enter a password: ");
            String password = input.nextLine();

            System.out.print("Enter your cell phone number (e.g. +27838968976): ");
            String cellPhoneNumber = input.nextLine();

            System.out.println(login.registerUser(username, password, cellPhoneNumber));
            System.out.println();

        } while (login.getUsername() == null);

        System.out.println("=== Chat App Login ===");

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Username: ");
            String enteredUsername = input.nextLine();

            System.out.print("Password: ");
            String enteredPassword = input.nextLine();

            loggedIn = login.loginUser(enteredUsername, enteredPassword);
            System.out.println(login.returnLoginStatus(loggedIn));
        }

        input.close();
    }
}
