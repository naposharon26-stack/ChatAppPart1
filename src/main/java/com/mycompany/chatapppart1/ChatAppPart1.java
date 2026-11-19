package com.mycompany.chatapppart1;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the QuickChat application.
 * Handles registration, login and message options.
 *
 * @author Student
 */
public class ChatAppPart1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Chat App: Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (must contain '_' and be <= 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special char): ");
        String password = scanner.nextLine();

        System.out.print("Enter South African cell number (e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();

        Login login = new Login(
                firstName, lastName, username, password, cellPhoneNumber);

        System.out.println();
        System.out.println(login.getUsernameMessage());
        System.out.println(login.getPasswordMessage());
        System.out.println(login.getCellPhoneMessage());

        if (login.checkUserName()
                && login.checkPasswordComplexity()
                && login.checkCellPhoneNumber()) {

            login.registerUser();
            System.out.println("Registration successful!");

            System.out.println();
            System.out.println("=== Chat App: Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean success = login.loginUser(
                    loginUsername, loginPassword);

            System.out.println(login.returnLoginStatus(success));

            if (success) {
                runQuickChat(scanner, login);
            }

        } else {
            System.out.println(
                    "Registration failed. Please fix the errors above and try again.");
        }

        scanner.close();
    }

    // ---------------- QUICKCHAT MENU ----------------

    private static void runQuickChat(Scanner scanner, Login login) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        Message.setScanner(scanner);

        // Part 3: arrays of messages, loaded from the JSON file at start-up
        MessageManager manager = new MessageManager();
        try {
            manager.loadStoredMessages(Message.DEFAULT_STORE_FILE);
        } catch (IOException e) {
            System.out.println("Could not read stored messages: " + e.getMessage());
        }

        int maxMessages = readPositiveInt(
                scanner, "How many messages do you wish to enter? ");

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Please choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    messagesEntered = sendMessages(
                            scanner, maxMessages, messagesEntered, manager);
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Goodbye.");
                    running = false;
                    break;

                case "4":
                    storedMessagesMenu(scanner, manager,
                            login.getfirstName() + " " + login.getlastName());
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please enter 1, 2, 3 or 4.");
            }
        }
    }

    // ---------------- SEND MESSAGES ----------------

    private static int sendMessages(
            Scanner scanner, int maxMessages, int messagesEntered,
            MessageManager manager) {

        if (messagesEntered >= maxMessages) {
            System.out.println(
                    "You have already entered all "
                    + maxMessages + " messages.");
            return messagesEntered;
        }

        while (messagesEntered < maxMessages) {
            System.out.println();
            System.out.println(
                    "--- Message " + (messagesEntered + 1)
                    + " of " + maxMessages + " ---");

            String recipient = readRecipient(scanner);
            String text = readMessageText(scanner);

            Message message = new Message(recipient, text);

            System.out.println(message.getMessageIDStatus());

            String result = message.SentMessage();
            messagesEntered++;

            System.out.println(result);

            // Part 3: add the message to the matching array
            if ("Message successfully sent.".equals(result)) {
                manager.addMessage(message, MessageManager.FLAG_SENT);
            } else if ("Message successfully stored.".equals(result)) {
                manager.addMessage(message, MessageManager.FLAG_STORED);
            } else if ("Message deleted.".equals(result)) {
                manager.addMessage(message, MessageManager.FLAG_DISREGARD);
            }

            if ("Message successfully sent.".equals(result)) {
                System.out.println();
                System.out.println(message.details());
            }
        }

        System.out.println();
        System.out.println(
                "Total number of messages sent: "
                + Message.returnTotalMessages());

        return messagesEntered;
    }

    // ---------------- STORED MESSAGES MENU (Part 3) ----------------

    private static void storedMessagesMenu(
            Scanner scanner, MessageManager manager, String sender) {

        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Stored Messages ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Enter your choice: ");

            switch (scanner.nextLine().trim().toLowerCase()) {
                case "a":
                    System.out.println(manager.displaySenderAndRecipient(sender));
                    break;

                case "b":
                    String longest = manager.getLongestMessage();
                    System.out.println(longest == null
                            ? "There are no stored messages."
                            : "Longest stored message: " + longest);
                    break;

                case "c":
                    System.out.print("Enter the message ID: ");
                    System.out.println(manager.searchByMessageIDDetails(
                            scanner.nextLine().trim()));
                    break;

                case "d":
                    System.out.print("Enter the recipient's cell number: ");
                    List<String> found = manager.searchByRecipient(
                            scanner.nextLine().trim());
                    if (found.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    }
                    for (String text : found) {
                        System.out.println("\"" + text + "\"");
                    }
                    break;

                case "e":
                    System.out.print("Enter the message hash: ");
                    System.out.println(manager.deleteByHash(
                            scanner.nextLine().trim().toUpperCase()));
                    try {
                        manager.saveStoredMessages(Message.DEFAULT_STORE_FILE);
                    } catch (IOException e) {
                        System.out.println("Could not update the JSON file: " + e.getMessage());
                    }
                    break;

                case "f":
                    System.out.println(manager.displayReport());
                    break;

                case "g":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option. Please enter a to g.");
            }
        }
    }

    // ---------------- RECIPIENT VALIDATION ----------------

    private static String readRecipient(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Enter the recipient's cell number "
                    + "(e.g. +27718693002): ");

            String cell = scanner.nextLine().trim();

            if (Message.isValidCell(cell)) {
                System.out.println(
                        "Cell phone number successfully captured.");
                return cell;
            }

            System.out.println(
                    "Cell phone number is incorrectly formatted or "
                    + "does not contain an international code. "
                    + "Please correct the number and try again.");
        }
    }

    // ---------------- MESSAGE TEXT VALIDATION ----------------

    private static String readMessageText(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Enter your message (maximum 250 characters): ");

            String text = scanner.nextLine();

            if (Message.isValidLength(text)) {
                return text;
            }

            System.out.println(
                    "Please enter a message of less than 250 characters.");
        }
    }

    // ---------------- INPUT VALIDATION ----------------

    private static int readPositiveInt(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                int value = Integer.parseInt(
                        scanner.nextLine().trim());

                if (value > 0) {
                    return value;
                }

            } catch (NumberFormatException ignored) {
                // Display the error message below.
            }

            System.out.println(
                    "Please enter a whole number greater than 0.");
        }
    }
}
