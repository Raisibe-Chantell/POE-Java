/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javapoe;

/**
 *
 * @author game
 */
public class Login {
    String firstName;
    String lastName;
    String username;
    String password;
    String cellNumber;

    public Login(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }

    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;
        boolean cap = false;
         boolean num = false;
         boolean spec = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) cap = true;
            if (Character.isDigit(c)) num = true;
            if (!Character.isLetterOrDigit(c)) spec = true;
        }
        return cap && num && spec;
    }

    public boolean checkCellPhoneNumber() {
        return cellNumber.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // This is the method
    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(username) && enteredPass.equals(password);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            // True message from your table
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            // False message from your table
            return "Username or password incorrect, please try again.";
        }
    }
} 

