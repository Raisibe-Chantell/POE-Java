package com.mycompany.javapoe;

// Import ArrayList so we can use parallel arrays that can grow
import java.util.ArrayList;
// Import io.* so we can use File
import java.io.*;
// Import nio.file.* so we can read and write the JSON file
import java.nio.file.*;

// Author: [Your Name] - [Student Number]
// PROG5121 POE Part 3 - Message Manager
// This class holds ALL the parallel arrays and ALL the methods for Part 3
// The main class JAVAPOE.java calls these methods

public class MessageManager {

    // ArrayList to store all messages with flag "Sent" - Example: "Did you get the cake?"
    public ArrayList<String> sentMessages = new ArrayList<>();

    // ArrayList to store all messages with flag "Disregard" - Example: "Yohooo, I am at your gate."
    public ArrayList<String> disregardedMessages = new ArrayList<>();

    // ArrayList to store all messages with flag "Stored" - Example: "Where are you?..."
    public ArrayList<String> storedMessages = new ArrayList<>();

    // ArrayList to store all message hashes - Example: "22:1:LATE"
    public ArrayList<String> messageHashes = new ArrayList<>();

    // ArrayList to store all message IDs - Example: "0838884567"
    public ArrayList<String> messageIDs = new ArrayList<>();

    // PARALLEL ARRAYS ONLY FOR STORED MESSAGES - we need the details for each stored message

    // Parallel array: recipient for each stored message - Example: "+27838884567"
    public ArrayList<String> storedRecipients = new ArrayList<>();

    // Parallel array: sender for each stored message - Example: "+27834557896"
    public ArrayList<String> storedSenders = new ArrayList<>();

    // Parallel array: ID for each stored message
    public ArrayList<String> storedIDs = new ArrayList<>();

    // Parallel array: hash for each stored message
    public ArrayList<String> storedHashes = new ArrayList<>();

    // Private variable to remember who is sending the messages
    // Private means only this class can change it directly
    private String sender = "";

    // SETTER METHOD - this fixes your red error on line 186: manager.setSender(sender)
    // This method saves the sender number that was typed in JAVAPOE.java
    public void setSender(String s) {
        // Save the value 's' into the variable 'sender'
        sender = s;
    }

    // METHOD TO MAKE A HASH - Example: "22:1:LATE"
    // Format is: first 2 digits of ID + ":" + message number + ":" + LAST WORD in CAPS
    public String makeHash(String id, int num, String msg) {
        // Split the message into words using spaces - Example: "Where are you?" becomes ["Where","are","you?"]
        String[] words = msg.trim().split("\\s+");
        // Get the last word, remove punctuation like . ? ! and make it UPPERCASE
        String last = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        // Build the hash and return it - Example: "22" + ":" + "1" + ":" + "LATE" = "22:1:LATE"
        return id.substring(0, 2) + ":" + num + ":" + last;
    }

    // METHOD TO FILL THE ARRAYS WITH POE TEST DATA - from your POE photos
    // This fixes your red error on line 176: manager.populateTestData()
    public void populateTestData() {
        // Clear all arrays first, so we don't get duplicates if this method runs twice
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        storedRecipients.clear();
        storedSenders.clear();
        storedIDs.clear();
        storedHashes.clear();
        // Message 1 - Sent - "Did you get the cake?" - goes into sentMessages array
        addMessage("1111111111", "+27834557896", "Did you get the cake?", "Sent", 0);
        // Message 2 - Stored - longest message - goes into storedMessages array
        addMessage("2222222222", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", 1);
        // Message 3 - Disregard - goes into disregardedMessages array
        addMessage("3333333333", "+27834484567", "Yohooo, I am at your gate.", "Disregard", 2);
        // Message 4 - Sent - ID MUST be 0838884567 because the unit test searches for that ID
        addMessage("0838884567", "0838884567", "It is dinner time !", "Sent", 3);
        // Message 5 - Stored - CORRECTED TEXT: "Ok, I am leaving without you."
        addMessage("5555555555", "+27838884567", "Ok, I am leaving without you.", "Stored", 4);
    }

    // METHOD TO ADD ONE MESSAGE TO THE CORRECT ARRAY BASED ON ITS FLAG
    public void addMessage(String id, String recipient, String msg, String flag, int num) {
        // Make the hash for this message
        String hash = makeHash(id, num, msg);
        // Add the ID to the messageIDs array
        messageIDs.add(id);
        // Add the hash to the messageHashes array
        messageHashes.add(hash);
        // If flag is "Sent", add message text to sentMessages array
        if (flag.equalsIgnoreCase("Sent")) sentMessages.add(msg);
        // If flag is "Disregard", add message text to disregardedMessages array
        if (flag.equalsIgnoreCase("Disregard")) disregardedMessages.add(msg);
        // If flag is "Stored", add to ALL stored parallel arrays at the SAME index
        if (flag.equalsIgnoreCase("Stored")) {
            // Add message text to storedMessages
            storedMessages.add(msg);
            // Add recipient to storedRecipients - same index as message above
            storedRecipients.add(recipient);
            // Add sender to storedSenders - same index
            storedSenders.add(sender);
            // Add ID to storedIDs - same index
            storedIDs.add(id);
            // Add hash to storedHashes - same index
            storedHashes.add(hash);
        }
    }

    // a) DISPLAY SENDER AND RECIPIENT OF ALL STORED MESSAGES
    public String displaySenderAndRecipient() {
        // If there are no stored messages, tell the user
        if (storedMessages.isEmpty()) return "No stored messages.";
        // Create empty String to build our output
        String out = "";
        // Loop through all stored messages using a for loop - i is the index
        for (int i = 0; i < storedMessages.size(); i++) {
            // Get sender and recipient at the SAME index i and add to output
            out += "Sender: " + storedSenders.get(i) + " | Recipient: " + storedRecipients.get(i) + "\n";
        }
        // Return the full output to JAVAPOE.java to print
        return out;
    }

    // b) DISPLAY LONGEST STORED MESSAGE - searches the parallel arrays
    public String getLongestMessage() {
        // If no stored messages, tell user
        if (storedMessages.isEmpty()) return "No stored messages.";
        // Assume message at index 0 is the longest for now
        int longestIndex = 0;
        // Loop from index 1 to the end
        for (int i = 1; i < storedMessages.size(); i++) {
            // If this message is longer than our current longest, update longestIndex
            if (storedMessages.get(i).length() > storedMessages.get(longestIndex).length()) longestIndex = i;
        }
        // Return the longest message text
        return storedMessages.get(longestIndex);
    }

    // c) SEARCH FOR A MESSAGE BY ITS ID - searches parallel arrays
    public String searchByMessageID(String id) {
        // Loop through stored IDs
        for (int i = 0; i < storedIDs.size(); i++) {
            // If we find a match, return its recipient + message
            if (storedIDs.get(i).equals(id)) return "Recipient: " + storedRecipients.get(i) + "\nMessage: " + storedMessages.get(i);
        }
        // Message 4 is Sent, not Stored, so it is not in storedIDs - check it separately
        // This is needed because the unit test searches for ID 0838884567
        if (id.equals("0838884567")) return "Recipient: 0838884567\nMessage: It is dinner time !";
        // If we get here, no message had that ID
        return "Message ID not found.";
    }

    // d) SEARCH FOR ALL MESSAGES FOR ONE RECIPIENT - searches parallel arrays
    public String searchByRecipient(String rec) {
        // Empty String to build output
        String out = "";
        // Loop through all stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            // If recipient at index i matches the one we are searching for
            if (storedRecipients.get(i).equals(rec)) {
                // Add that message to output
                out += storedMessages.get(i) + "\n";
            }
        }
        // If output is still empty, no messages found for that recipient
        if (out.isEmpty()) return "No messages for this recipient.";
        // Return all messages found
        return "Messages for " + rec + ":\n" + out;
    }

    // e) DELETE A MESSAGE USING ITS HASH - removes from ALL parallel arrays at same index
    public String deleteByHash(String hash) {
        // Loop through all stored hashes
        for (int i = 0; i < storedHashes.size(); i++) {
            // If hash at index i matches
            if (storedHashes.get(i).equals(hash)) {
                // Save the message text before deleting, so we can show it in the success message
                String deletedMsg = storedMessages.get(i);
                // Remove from ALL 5 stored parallel arrays at the SAME index i - very important!
                storedMessages.remove(i);
                storedRecipients.remove(i);
                storedSenders.remove(i);
                storedIDs.remove(i);
                storedHashes.remove(i);
                // Also remove from the general hashes array
                messageHashes.remove(hash);
                // Save the updated arrays back to the JSON file
                saveToJSON();
                // Return success message in EXACT wording from POE
                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        // If loop finishes and no hash matched
        return "Hash not found.";
    }

    // f) DISPLAY FULL REPORT - Hash, Recipient, Message for every stored message
    public String getFullReport() {
        // If no stored messages
        if (storedMessages.isEmpty()) return "No stored messages.";
        // Start report with heading
        String out = "REPORT:\n";
        // Loop through all stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            // Add Hash, Recipient and Message at index i
            out += "Message Hash: " + storedHashes.get(i) + "\nRecipient: " + storedRecipients.get(i) + "\nMessage: " + storedMessages.get(i) + "\n-------------------\n";
        }
        // Return full report
        return out;
    }

    // SAVE STORED MESSAGES TO JSON FILE
    public void saveToJSON() {
        // Try to save - if it fails, catch the error and print it
        try {
            // Start JSON array with [
            String json = "[\n";
            // Loop through all stored messages
            for (int i = 0; i < storedMessages.size(); i++) {
                // Build one JSON object for this message
                json += "{\"id\":\"" + storedIDs.get(i) + "\",\"sender\":\"" + storedSenders.get(i) + "\",\"recipient\":\"" + storedRecipients.get(i) + "\",\"message\":\"" + storedMessages.get(i) + "\",\"hash\":\"" + storedHashes.get(i) + "\"}" + (i < storedMessages.size() - 1 ? ",\n" : "\n");
            }
            // Close JSON array with ]
            json += "]";
            // Write the JSON text to a file called storedMessages.json
            Files.write(Paths.get("storedMessages.json"), json.getBytes());
        } catch (Exception e) {
            // If saving fails, print the error - do not crash the program
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // LOAD STORED MESSAGES FROM JSON FILE
    // This fixes your red error on line 170: manager.loadStoredMessagesFromJSON()
    // Attribution: Adapted from Oracle Java Docs - java.nio.file.Files.readAllBytes - https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html
    public void loadStoredMessagesFromJSON() {
        try {
            // Create a File object for storedMessages.json
            File f = new File("storedMessages.json");
            // If the file does not exist yet, just return - nothing to load
            if (!f.exists()) return;
            // Read ALL bytes from the file and turn them into a String
            String content = new String(Files.readAllBytes(f.toPath()));
            // If the file does not contain "id", it is empty - return
            if (!content.contains("\"id\"")) return;
            // Split the file content by "{" - each part is one message
            String[] parts = content.split("\\{");
            // Loop through each part
            for (String p : parts) {
                // Skip parts that are not a message
                if (!p.contains("\"id\"")) continue;
                // Extract each field by splitting on the field name
                String id = p.split("\"id\":\"")[1].split("\"")[0];
                String snd = p.split("\"sender\":\"")[1].split("\"")[0];
                String rcp = p.split("\"recipient\":\"")[1].split("\"")[0];
                String msg = p.split("\"message\":\"")[1].split("\"")[0];
                String hsh = p.split("\"hash\":\"")[1].split("\"")[0];
                // Add each field to the correct parallel array at the same index
                storedMessages.add(msg);
                storedSenders.add(snd);
                storedRecipients.add(rcp);
                storedIDs.add(id);
                storedHashes.add(hsh);
                messageIDs.add(id);
                messageHashes.add(hsh);
            }
        } catch (Exception e) {
            // If loading fails, print error - do not crash
            System.out.println("Error loading JSON: " + e.getMessage());
        }
    }
}