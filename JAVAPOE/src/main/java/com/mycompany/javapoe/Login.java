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
    String fName;
    String sName;
    String userName;
    String pass;
    String cellPhoneNumber;

    public Login(String firstName, String lastName, String username, String password, String cellNumber) {
        fName= firstName;
        sName = lastName;
        userName = username;
        pass = password;
        cellPhoneNumber = cellNumber;
    }

    public boolean checkUserName(String username) {
        return userName.contains("_") && userName.length() <= 5;
    }

    public boolean checkPasswordComplexity(String pass) {
        if (pass.length() < 8) return false;
        boolean cap = false;
         boolean num = false;
         boolean spec = false;
         
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) cap = true;
            if (Character.isDigit(c)) num = true;
            if (!Character.isLetterOrDigit(c)) spec = true;
        }
        return cap && num && spec;
    }
    
    //reference : Pattern class regex for validating international https://www.w3schools.com/jsref/jsref_obj_regexp.asp
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(String username) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(pass)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // This is the method
    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(userName) && enteredPass.equals(pass);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            // True 
            return "Welcome " + fName + ", " + sName + " it is great to see you again.";
        } else {
            // False 
            return "Username or password incorrect, please try again.";
        }
    }
} 

