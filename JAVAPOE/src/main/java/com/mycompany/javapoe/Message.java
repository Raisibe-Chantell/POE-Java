/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javapoe;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 *
 * @author game
 */
public class Message {
    
    // Variables to store message details
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;
    private static int totalMessagesSent = 0;

    // Constructor - runs when you do new Message(...)
    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    // Helper: Generates random 10-digit ID using loop
    public String generateID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += rand.nextInt(10);
        }
        return id;
    }

    // --- METHODS ---

    // 1. Boolean: checkMessageID()
    public Boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // 2. String: checkRecipientCell() - from Table
    public String checkRecipientCell() {
        if (recipient.length() <= 10 && recipient.contains("+")) {
            return "Cell phone number successfully captured";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain international code";
        }
    }

    // 3. String: createMessageHash() - format 00:0:HITHANKS
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        // Remove ? and , for hash
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        return firstTwo + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // 4. String: SentMessage() - capital S, from your photo
    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            return "Message successfully sent";
        } else if (choice == 2) {
            return "Press 0 to delete the message";
        } else if (choice == 3) {
            storeMessage();
            totalMessagesSent++;
            return "Message successfully stored";
        }
        return "Invalid choice";
    }

    // 5. String: printMessages() - Rule 7 order: ID, Hash, Recipient, Message
    public String printMessages() {
        return "MessageID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + message;
    }

    // 6. Int: returnTotalMessagess() - IIE spelling with 2 s, Rule 6
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // 7. Your own defined storeMessage() method - stores in JSON
    public void storeMessage() {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);
            writer.write("{\n  \"messageID\": \"" + messageID + "\",\n");
            writer.write("  \"messageHash\": \"" + messageHash + "\",\n");
            writer.write("  \"recipient\": \"" + recipient + "\",\n");
            writer.write("  \"message\": \"" + message + "\"\n},\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error storing message");
        }
    }
    
    public String checkMessageLength() {
        if (message.length() > 250) {
            return "Message exceeds 250 characters by " + (message.length() - 250) + ", please reduce size.";
        }
        return "Message ready to send";
    } 
}
