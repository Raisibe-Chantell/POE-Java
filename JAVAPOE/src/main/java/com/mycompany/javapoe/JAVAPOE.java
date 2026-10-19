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
        
        
    //PART 2
        if (true) {
            // --- START OF PART 2 - QUICKCHAT ---
            System.out.println("\nWelcome to QuickChat!");
            
            System.out.print("How many messages do you wish to enter? ");
            int numMessages = Integer.parseInt(input.nextLine());
            
            for (int i = 1; i <= numMessages; i++) {
                System.out.println("\n--- Message " + i + " ---");
                
                System.out.print("Enter recipient cell (with +): ");
                String recipient = input.nextLine();
                
                System.out.print("Enter your message: ");
                String text = input.nextLine();

                Message msg = new Message(recipient, text, i);

                if (text.length() > 250) {
                    System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + ", please reduce size.");
                    i--;
                    continue;
                }

                String cellCheck = msg.checkRecipientCell();
                System.out.println(cellCheck);
                
                if (cellCheck.contains("incorrectly formatted")) {
                    System.out.println("Skipping this message.");
                    continue;
                }

                System.out.println("Message Hash: " + msg.createMessageHash());
                System.out.println(msg.printMessages());

                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message");
                System.out.print("Choose: ");
                int choice = Integer.parseInt(input.nextLine());

                String result = msg.SentMessage(choice);
                System.out.println(result);
                System.out.println("Total messages sent: " + msg.returnTotalMessagess());
            }
            // --- END OF PART 2 ---
        }
    }
}
    
