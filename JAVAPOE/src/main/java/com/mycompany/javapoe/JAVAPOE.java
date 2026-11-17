/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javapoe;
import java.util.Scanner;
 import javax.swing.JOptionPane;

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
                    
                      // --- END OF PART 2 --  
                  
// PART 3 START - This is the NEW 4th Menu for Stored Messages (Required for POE)

        // Create a MessageManager object - this holds all our messages and methods
        MessageManager manager = new MessageManager();

        // Task 1.2: Load the 5 test messages into the arrays
        manager.populateTestData();

        // Task 1.3 / Part 3: Show menu until user chooses to quit
        // We use a while loop so the menu keeps showing after each choice
        String choice3 = "";

        // Loop until user types q or clicks Cancel
        while (!choice3.equalsIgnoreCase("q")) {

            // Show the menu - this is exactly what the POE asks for in Part 3
            choice3 = JOptionPane.showInputDialog(
                    "Choose an option:\n"
                    + "a) Display sender and recipient of stored messages\n"
                    + "b) Display longest stored message\n"
                    + "c) Search by Message ID\n"
                    + "d) Search by recipient\n"
                    + "e) Delete by hash\n"
                    + "f) Display full report of sent messages\n"
                    + "q) Quit");

            // If user clicks Cancel, JOptionPane returns null - stop the program
            if (choice3 == null) {
                break;
            }

            // Use switch to run the correct method for each letter
            switch (choice3.toLowerCase()) {

                // Task 2a: Display sender and recipient of stored messages
                case "a":
                    // Calls displaySenderAndRecipient() in MessageManager
                    JOptionPane.showMessageDialog(null, manager.displaySenderAndRecipient());
                    break;

                // Task 2b: Display longest stored message
                case "b":
                    // Calls getLongestMessage() in MessageManager
                    JOptionPane.showMessageDialog(null, "Longest message:\n" + manager.getLongestMessage());
                    break;

                // Task 2c: Search by Message ID
                case "c":
                    // POE test: Search for 0838884567 - should return "It is dinner time!"
                    String id = JOptionPane.showInputDialog("Enter Message ID to search for (try 0838884567):");
                    if (id != null) {
                        JOptionPane.showMessageDialog(null, manager.searchByMessageID(id));
                    }
                    break;

                // Task 2d: Search by recipient
                case "d":
                    // POE test: Search for +27838884567 - should return Message 2 + Message 5
                    String rec = JOptionPane.showInputDialog("Enter recipient to search for (try +27838884567):");
                    if (rec != null) {
                        JOptionPane.showMessageDialog(null, manager.searchByRecipient(rec));
                    }
                    break;

                // Task 2e: Delete by hash
                case "e":
                    // POE test: Delete 00:1:LATE - should delete Message 2
                    String hash = JOptionPane.showInputDialog("Enter hash to delete (try 00:1:LATE):");
                    if (hash != null) {
                        JOptionPane.showMessageDialog(null, manager.deleteByHash(hash));
                    }
                    break;

                // Task 2f: Display full report of SENT messages
                case "f":
                    // Calls getFullReport() - shows Hash, Recipient, Message for Sent only
                    JOptionPane.showMessageDialog(null, manager.getFullReport());
                    break;

                // Quit option
                case "q":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break;

                // If user types anything else
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose a, b, c, d, e, f or q.");
                    break;
            }
        }
                }
            }
        }
        }
    }
}
// PART 3 END

            

        
       
