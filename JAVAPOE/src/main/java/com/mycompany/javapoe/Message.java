package com.mycompany.javapoe;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private static int totalMessagesSent = 0;
    private static int totalMessages = 0; 

    // Instance variables - unique to each message
    private String recipient; 
    private String message; 
    private int messageNumber; 
    private String messageID; 

    // Constructor - initializes a new Message object
    public Message(String recipient, String message, int messageNumber) {
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageID = generateID(); 
    }

    // Method to generate random 10-digit Message ID
    // Uses Random class to create ID as per POE requirement
    public String generateID() {
        Random rand = new Random();
        String id = "";
        // Loop 10 times to build 10-digit number
        for (int i = 0; i < 10; i++) {
            id += rand.nextInt(10); // Adds random digit 0-9 each time
        }
        return id;
    }

    // Checks if Message ID is valid (10 digits or less)
    // Returns true if valid, false if invalid
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Getter for messageID - needed for unit tests and printing
    public String getMessageID() {
        return messageID;
    }

    // Validates recipient cell number format
    public String checkRecipientCell() {
        if (recipient.length() <= 13 && recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            // Exact failure message required by brief page 15
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Checks if message length is within 250 characters limit
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            // Calculates by how much it exceeds 250
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
        }
    }

    // Creates Message Hash in format 00:0:HITONIGHT
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2); // Get first 2 digits of ID
        String[] words = message.trim().split("\\s+"); // Split message into words
        String firstWord = words[0]; // First word of message
        String lastWord = words[words.length - 1]; // Last word of message
        // Remove punctuation/special chars from words for clean hash
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        // Build hash and convert to uppercase
        return firstTwo + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // Handles user choice to Send, Disregard or Store message
   
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                totalMessages++; 
                return "Message successfully sent.";
            case 2:
                // Disregard - do not count as sent, show delete message
                return "Press 0 to delete the message.";
            case 3:
                // Store - save to JSON file, do not count as sent
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice";
        }
    }

    // Builds full message display string with ID, Hash, Recipient, Message
    // Used for printing to console and storing
    public String printMessages() {
        return "MessageID: " + messageID + "\nMessage Hash: " + createMessageHash() + "\nRecipient: " + recipient + "\nMessage: " + message;
    }

    // Stores message details to JSON file (storedMessages.json)
    // Appends each stored message to file for later retrieval
    // Uses try-catch for file handling errors
    public void storeMessage() {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true); // true = append mode
            writer.write(printMessages() + "\n---\n"); // Write message with separator
            writer.close(); // Close file after writing
        } catch (IOException e) {
            System.out.println("Error storing message");
        }
    }

    // Returns total number of messages sent - must be static
    
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }
    
    // Alternative method with correct spelling - also returns sent count
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
