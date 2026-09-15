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
     String Username;
     String Password;
     String FirstName;
     String LastName;
     
     
        Scanner input = new Scanner(System.in);

        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cell number (e.g. +27831234567): ");
        String cellNumber = input.nextLine();
        
         //call the registration results
        String regResult = registerUser(username, password, cellNumber);
        System.out.println(regResult);

        if (checkUserName(username) && checkPasswordComplexity(password) && checkCellPhoneNumber(cellNumber)) {
            
         String storedUsername = username;
         String storedPassword = password;
         String storedFirstName = firstName;
         String storedLastName = lastName;

            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter username to login: ");
            String loginU = input.nextLine();
            System.out.print("Enter password to login: ");
            String loginP = input.nextLine();

            boolean loggedIn = loginUser(loginU, loginP);
            String message = returnLoginStatus(loggedIn, firstName, lastName);
            System.out.println(message);
        }
    }
    public static boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean capitalFound = false;
        boolean numberFound = false;
        boolean specialFound = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                capitalFound = true;
            }
            if (ch >= '0' && ch <= '9') {
                numberFound = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                specialFound = true;
            }
        }
        if (capitalFound && numberFound && specialFound) {
            return true;
        } else {
            return false;
        }
    }

    // Reference: https://www.regular-expressions.info/
    public static boolean checkCellPhoneNumber(String number) {
        if (number.matches("^\\+27[0-9]{9}$")) {
            return true;
        } else {
            return false;
        }
    }

    public static String registerUser(String username, String password, String cell) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cell)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    public static boolean loginUser(String enteredUser, String enteredPass) {
        Object storedPassword = null;
        Object storedUsername = null;
        
        if (enteredUser.equals(storedUsername) && enteredPass.equals(storedPassword)) {
            return true;
        } else {
            return false;                                                                                                    
        }
    }

    public static String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
        
    }
}
