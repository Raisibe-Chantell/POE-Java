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
                    
                      // --- END OF PART 2 --  
                  
// PART 3 START - This is the NEW 4th Menu for Stored Messages (Required for POE)

  
        // Create a MessageManager object - this object holds all our arrays and all our methods
        // We use this object to call methods like displaySenderAndRecipient(), getLongestMessage(), etc.
        MessageManager manager = new MessageManager();

        // Try to load any messages that were saved last time in the storedMessages.json file
        // If the file does not exist yet, this method will just do nothing
        manager.loadStoredMessagesFromJSON();

        // If the storedMessages array is empty (meaning JSON file was empty or did not exist)
        // Then we load the POE test data so we can test menu option 4
        // This is only for testing - in real life the user would store their own messages
        if (manager.storedMessages.isEmpty()) {
            manager.populateTestData();
        }

        // Ask the user to enter their own cell number - this will be the sender for all messages
        System.out.print("Enter your cell number (sender): ");

        // Read the sender number the user typed and store it in a variable called 'sender'
        String sender = input.nextLine();

        // Give the sender number to the manager, so the manager can save it with every stored message
        manager.setSender(sender);

        // Create a String variable called 'choice' to store the user's main menu choice
        // We start it as empty "" so the while loop will run the first time
        String mainChoice = "";

        // While loop - keep showing the main menu until the user types "3" to Quit
        // As long as choice is NOT "3", keep looping
        while (!mainChoice.equals("3")) {

            // Print a blank line for neatness, then print the main menu heading
            System.out.println("\n--- QuickChat ---");

            // Print option 1 - Send Messages (This is from Part 2)
            System.out.println("1) Send Messages");

            // Print option 2 - Show Recently Sent Messages (This is from Part 2)
            System.out.println("2) Show Recently Sent Messages");

            // Print option 3 - Quit the program
            System.out.println("3) Quit");

            // Print option 4 - Stored Messages (This is NEW for Part 3)
            // This is the 4th main menu option required by the POE instructions
            System.out.println("4) Stored Messages");

            // Ask the user to enter their choice
            System.out.print("Enter choice: ");

            // Read what the user typed and save it in 'choice'
            mainChoice = input.nextLine();

            // Switch statement - checks what the user typed and runs the matching case
            switch (mainChoice) {

                // If user typed "1"
                case "1":
                    // For now we just print a message - your Part 2 Send Messages code goes here
                    System.out.println("Send Messages - Part 2 code goes here");
                    // Break stops the switch and goes back to the top of the while loop
                    break;

                // If user typed "2"
                case "2":
                    // Print a heading
                    System.out.println("Recently Sent Messages:");
                    // Print the whole sentMessages array - this shows all messages with flag "Sent"
                    System.out.println(manager.sentMessages);
                    break;

                // If user typed "3"
                case "3":
                    // Print goodbye message - the while loop will now stop because choice == "3"
                    System.out.println("Goodbye!");
                    break;

                // If user typed "4" - THIS IS PART 3
                case "4":
                    // Create a new variable called 'choice3' for the Stored Messages sub-menu
                    // We start it as "" so the loop will run
                    String choice3 = "";

                    // While loop for Stored Messages menu - keep looping until user types "q" to quit
                    // We use equalsIgnoreCase so "Q" and "q" both work
                    while (!choice3.equalsIgnoreCase("q")) {

                        // Print the Stored Messages sub-menu heading
                        System.out.println("\n--- Stored Messages ---");

                        // Option a - Display sender and recipient of all stored messages (POE Question 2a)
                        System.out.println("a) Display sender and recipient of all stored messages");

                        // Option b - Display the longest stored message (POE Question 2b)
                        System.out.println("b) Display longest stored message");

                        // Option c - Search for a message by its Message ID (POE Question 2c)
                        System.out.println("c) Search for a message ID");

                        // Option d - Search for all messages sent to one recipient (POE Question 2d)
                        System.out.println("d) Search for all messages for a recipient");

                        // Option e - Delete a message using its message hash (POE Question 2e)
                        System.out.println("e) Delete a message using message hash");

                        // Option f - Display a full report of all stored messages (POE Question 2f)
                        System.out.println("f) Display full report");

                        // Option q - Quit back to the main menu
                        System.out.println("q) Quit to main menu");

                        // Ask the user to enter their choice for the sub-menu
                        System.out.print("Enter choice: ");

                        // Read what the user typed for the sub-menu
                        choice3 = input.nextLine();

                        // Switch for the sub-menu - we use toLowerCase() so "A" and "a" both work
                        switch (choice3.toLowerCase()) {

                            // If user typed "a"
                            case "a":
                                // Call the method displaySenderAndRecipient() from MessageManager
                                // This method loops through the parallel arrays and returns sender + recipient
                                System.out.println(manager.displaySenderAndRecipient());
                                break;

                            // If user typed "b"
                            case "b":
                                // Call the method getLongestMessage() - it finds the longest message in the storedMessages array
                                System.out.println("Longest message: " + manager.getLongestMessage());
                                break;

                            // If user typed "c"
                            case "c":
                                // Ask the user to enter the Message ID they want to search for
                                System.out.print("Enter Message ID: ");

                                // Read the ID the user typed
                                String id = input.nextLine();

                                // Call searchByMessageID() and print the result (Recipient + Message)
                                System.out.println(manager.searchByMessageID(id));
                                break;

                            // If user typed "d"
                            case "d":
                                // Ask the user to enter the recipient number they want to search for
                                System.out.print("Enter recipient: ");

                                // Read the recipient the user typed
                                String rec = input.nextLine();

                                // Call searchByRecipient() - it searches the parallel arrays for that recipient
                                System.out.println(manager.searchByRecipient(rec));
                                break;

                            // If user typed "e"
                            case "e":
                                // Ask the user to enter the hash of the message they want to delete
                                System.out.print("Enter hash to delete: ");

                                // Read the hash the user typed
                                String hash = input.nextLine();

                                // Call deleteByHash() - it removes the message from ALL parallel arrays at the same index
                                System.out.println(manager.deleteByHash(hash));
                                break;

                            // If user typed "f"
                            case "f":
                                // Call getFullReport() - it prints Hash, Recipient and Message for every stored message
                                System.out.println(manager.getFullReport());
                                break;

                            // If user typed "q"
                            case "q":
                                // Print message to show we are going back to main menu
                                // The while loop will now stop because choice3 == "q"
                                System.out.println("Back to main menu.");
                                break;

                            // If user typed anything else
                            default:
                                // Tell the user their choice was invalid
                                System.out.println("Invalid option. Try again.");
                                break;
                        }
                        // End of Stored Messages sub-menu switch
                    }
                    // End of Stored Messages while loop
                    break;

                // If user typed anything else in main menu (not 1,2,3,4)
                default:
                    // Tell the user their choice was invalid
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
            // End of main menu switch
        }
        // End of main menu while loop - user typed "3" to quit

        // Close the Scanner to free up memory - good practice
        input.close();
    }
    // End of main method
}
// End of JAVAPOE class
        }
                }
            }
        }
// PART 3 END

            

        
       
