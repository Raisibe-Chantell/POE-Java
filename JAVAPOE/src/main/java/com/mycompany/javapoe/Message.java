package com.mycompany.javapoe;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    String messageID;
    String recipient;
    String message;
    int messageNumber;
    static int totalMessagesSent = 0;
    static int totalMessages = 0;

    public Message(String recipient, String message, int messageNumber) {
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageID = generateID();
    }

    public String generateID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += rand.nextInt(10);
        }
        return id;
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // FIXED 1 - exact strings from page 15 photo
    public String checkRecipientCell() {
        if (recipient.length() <= 13 && recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // ADDED - Required by page 15
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
        }
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        lastWord = lastWord.replaceAll("[^A-Za-z]", "");
        firstWord = firstWord.replaceAll("[^A-Za-z]", "");
        return firstTwo + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // FIXED 2 - don't count stored as sent
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                totalMessages++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice";
        }
    }

    public String printMessages() {
        return "MessageID: " + messageID + "\nMessage Hash: " + createMessageHash() + "\nRecipient: " + recipient + "\nMessage: " + message;
    }

    public void storeMessage() {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);
            writer.write(printMessages() + "\n---\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error storing message");
        }
    }

    // FIXED 3 - Must be static
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }
    
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}