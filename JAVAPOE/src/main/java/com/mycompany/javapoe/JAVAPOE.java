/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javapoe;
import java.util.Scanner;

/**
 *
 * @author game
 */
public class JAVAPOE {

    public static void main(String[] args) {
        //Declarations of virables
        String firstName;
        String username;
        String password;
        String cell;
        String lastName;
        
        
        Scanner input = new Scanner(System.in);

        System.out.print("Enter First Name: ");
        firstName = input.nextLine();
        System.out.print("Enter Last Name: ");
        lastName = input.nextLine();
        System.out.print("Enter username: ");
        username = input.nextLine();
        System.out.print("Enter password: ");
        password = input.nextLine();
        System.out.print("Enter cell number: ");
        cell = input.nextLine();
        
        //call the login method
        Login user = new Login(firstName, lastName, username, password, cell);

        // REGISTRATION LOOP - keeps asking until correct
        while (!user.checkUserName(username)) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Enter username again: ");
            username = input.nextLine();
            user.userName = username;
        }
        System.out.println("Username successfully captured.");
      // validations for password
        while (!user.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted, please ensure that the password "
                    + "contains at least eight characters,"
                    + " a capital letter,"
                    + " a number, "
                    + " a special character.");
            System.out.print("Enter password again: ");
            password = input.nextLine();
            user.pass = password;
        }
        System.out.println("Password successfully captured.");
        
        // Validation for cell phone number
        while (!user.checkCellPhoneNumber(cell)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Enter cell number again: ");
            cell = input.nextLine();
            user.cellPhoneNumber = cell;
        }
        System.out.println("Cell phone number successfully added.");


        // this is for login
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter username to login: ");
        String enteredUser = input.nextLine();
        System.out.print("Enter password to login: ");
        String enteredPass = input.nextLine();

        boolean loggedIn = user.loginUser(enteredUser, enteredPass);
        System.out.println(user.returnLoginStatus(loggedIn));
        
        
    //// --- START OF PART 2 - QUICKCHAT ---
        if (true) {
    
        // This section handles sending of QuickChat messages
        // It is a continuation from Part 1 after successful registration/login

        // Display welcome message as required in the brief - Requirement #2
        System.out.println("\nWelcome to QuickChat!");

        // Prompt user to enter how many messages they wish to send
       
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = Integer.parseInt(input.nextLine());

        // Loop through each message the user wants to send
        // i starts at 0 so that first message hash is 00:0:HITONIGHT as per test
        for (int i = 0; i < numMessages; i++) {
            
            // Display current message number for user clarity (i+1 for display)
            System.out.println("\n--- Message " + (i+1) + " ---");

            // Prompt for recipient cell number - must include international code e.g. +27
            System.out.print("Enter recipient cell (with +): ");
            String recipient = input.nextLine();

            // Prompt for message text content from user
            System.out.print("Enter your message: ");
            String text = input.nextLine();

            // Create new Message object with recipient, text and message number
            // Constructor will automatically generate 10-digit 
            Message msg = new Message(recipient, text, i);

            // Check if message length exceeds 250 characters limit
            // This uses the length check logic required in Message class
            if (text.length() > 250) {
                // If too long, calculate by how many characters it exceeds
                int excess = text.length() - 250;
                System.out.println("Message exceeds 250 characters by " + excess + ", please reduce size.");
                i--; // Decrement counter to allow user to re-enter same message number
            } else {
                // If message length is valid, proceed to check recipient cell format
                
                // Call checkRecipientCell method from Message class
                // This validates if number contains + and <=13 characters
                String cellCheck = msg.checkRecipientCell();
                System.out.println(cellCheck); // Display success or failure message

                // Check if recipient validation failed
                if (cellCheck.contains("incorrectly formatted")) {
                    // If cell is incorrectly formatted, skip sending this message
                    System.out.println("Skipping this message - invalid recipient.");
                } else {
                    // If both message length and recipient are valid, proceed to send

                    // Generate and display Message Hash in format 00:0:HITONIGHT
                    // Uses first two digits of ID, message number, first and last word
                    System.out.println("Message Hash: " + msg.createMessageHash());
                    
                    // Display full message details including ID, Hash, Recipient, Message
                    System.out.println(msg.printMessages());

                    // Display menu for user to choose what to do with the message
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose: ");
                    int choice = Integer.parseInt(input.nextLine());

                    // Call SentMessage method - handles Send, Disregard, Store options
                    // Updates totalMessagesSent if message is sent
                    String result = msg.SentMessage(choice);
                    System.out.println(result); 
                    
                    // Display total number of messages sent so far using static method
                    System.out.println("Total messages sent: " + Message.returnTotalMessagess());
                }
            }
          }
        
        }
       }
    }
        
        // --- END OF PART 2 --  