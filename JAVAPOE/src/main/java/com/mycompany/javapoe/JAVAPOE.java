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
        while (!user.checkUserName()) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Enter username again: ");
            username = input.nextLine();
            user.username = username;
        }
        System.out.println("Username successfully captured.");
      // validations for password
        while (!user.checkPasswordComplexity()) {
            System.out.println("Password is not correctly formatted, please ensure that the password "
                    + "contains at least eight characters,"
                    + " a capital letter,"
                    + " a number, "
                    + " a special character.");
            System.out.print("Enter password again: ");
            password = input.nextLine();
            user.password = password;
        }
        System.out.println("Password successfully captured.");
        
        // Validation for cell phone number
        while (!user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Enter cell number again: ");
            cell = input.nextLine();
            user.cellNumber = cell;
        }
        System.out.println("Cell phone number successfully added.");

        System.out.println(user.registerUser());

        // this is for login
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter username to login: ");
        String enteredUser = input.nextLine();
        System.out.print("Enter password to login: ");
        String enteredPass = input.nextLine();

        boolean loggedIn = user.loginUser(enteredUser, enteredPass);
        System.out.println(user.returnLoginStatus(loggedIn));
    }
} 