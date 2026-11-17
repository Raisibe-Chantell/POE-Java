package com.mycompany.javapoe;

import java.util.ArrayList;


public class MessageManager {

    // Task 1.1: Declare 5 parallel arrays + 1 for recipients
    // Parallel means: index 0 in every list = Message 1, index 1 = Message 2, etc.
    public ArrayList<String> sentMessages = new ArrayList<>(); // Stores messages flagged as Sent
    public ArrayList<String> disregardedMessages = new ArrayList<>(); // Stores messages flagged as Disregard
    public ArrayList<String> storedMessages = new ArrayList<>(); // Stores messages flagged as Stored
    public ArrayList<String> messageHashes = new ArrayList<>(); // Stores the hash for each message
    public ArrayList<String> messageIDs = new ArrayList<>(); // Stores the ID for each message
    public ArrayList<String> recipients = new ArrayList<>(); // Stores the recipient cell number for each message

    // Extra lists to keep everything in line - these make the searching/deleting work
    public ArrayList<String> allMessages = new ArrayList<>(); // Stores ALL message texts in order 1-5
    public ArrayList<String> flags = new ArrayList<>(); // Stores Sent / Stored / Disregard for each message

    // Task 1.2: Populate arrays with the 5 test messages from the POE
    public void populateTestData() {
        // Clear first so we don't get duplicates if this method is called twice
        sentMessages.clear(); disregardedMessages.clear(); storedMessages.clear();
        messageHashes.clear(); messageIDs.clear(); recipients.clear();
        allMessages.clear(); flags.clear();

        // Message 1 - Sent - Did you get the cake?
        addMessage("+27834557896", "Did you get the cake?", "Sent", "ID001", "00:0:DID CAKE?");
        // Message 2 - Stored - Longest message, will be used for delete test
        addMessage("+27838884567", "Where are you? You are late! "
                + "I have asked you to be on time.", 
                "Stored", "ID002",
                "00:1:LATE");
        // Message 3 - Disregard - Yohoooo
        addMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard", "ID003", "00:2:GATE");
        
        // Message 4 - Sent - Developer number is 0838884567, used for search by ID test
        addMessage("0838884567", "It is dinner time!", "Sent", "ID004", "00:3:DINNER");
        
        // Message 5 - Stored - Same recipient as Message 2, used for search by recipient test
        addMessage("+27838884567", "Ok, I am leaving without you.", "Stored", "ID005", "00:4:WITHOUT");
    }

    // Helper method - adds one message to ALL lists at the same index
    // This keeps the arrays parallel so we never mix up Message 2 with Message 4
    private void addMessage(String recipient, String msg, String flag, String id, String hash) {
        recipients.add(recipient); // Add recipient to recipients list
        allMessages.add(msg); // Add message text to allMessages list
        flags.add(flag); // Add flag (Sent/Stored/Disregard) to flags list
        messageIDs.add(id); // Add ID to messageIDs list
        messageHashes.add(hash); // Add hash to messageHashes list

        // Also add the message to its category list based on its flag
        if (flag.equals("Sent")) {
            sentMessages.add(msg);
        } else if (flag.equals("Stored")) {
            storedMessages.add(msg);
        } else if (flag.equals("Disregard")) {
            disregardedMessages.add(msg);
        }
    }

    // Task 2a: Display sender and recipient of all STORED messages
    public String displaySenderAndRecipient() {
        String result = ""; // This will hold the final text to return
        // Loop through all messages
        for (int i = 0; i < allMessages.size(); i++) {
            // Only show messages that are Stored
            if (flags.get(i).equals("Stored")) {
                // Sender is always the developer 0838884567 in this POE
                result += "Sender: 0838884567, Recipient: " + recipients.get(i) + "\n";
            }
        }
        return result; // Return the built-up string
    }

    // Old method name - kept so old code that calls getSendersAndRecipients() still works
    // It just calls the correct method above
    public String getSendersAndRecipients() {
        return displaySenderAndRecipient();
    }

    // Task 2b: Find the longest message among all STORED messages
    public String getLongestMessage() {
        String longest = ""; // Start with empty, first stored message will beat it
        // Loop through all messages
        for (int i = 0; i < allMessages.size(); i++) {
            // Only check Stored messages
            if (flags.get(i).equals("Stored")) {
                // If this message is longer than our current longest, it becomes the new longest
                if (allMessages.get(i).length() > longest.length()) {
                    longest = allMessages.get(i);
                }
            }
        }
        return longest; // Return the longest one found
    }

    // Task 2c: Search by Message ID - should return "It is dinner time!" for "0838884567"
    public String searchByMessageID(String id) {
        // First check recipients - in this POE, 0838884567 is stored as a recipient (Message 4)
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(id)) {
                return allMessages.get(i); // Found it, return the message text
            }
        }
        // If not found in recipients, check the real messageIDs (ID001, ID002...)
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return allMessages.get(i); // Found it, return the message text
            }
        }
        return "Message not found"; // Nothing matched
    }

    // Old method name - kept so old code that calls searchByID() still works
    public String searchByID(String id) {
        return searchByMessageID(id);
    }

    // Task 2d: Search for all messages sent to a particular recipient
    public String searchByRecipient(String rec) {
        String result = ""; // Will hold all matching messages
        // Loop through all recipients
        for (int i = 0; i < recipients.size(); i++) {
            // If this recipient matches the one we are searching for
            if (recipients.get(i).equals(rec)) {
                result += allMessages.get(i) + "\n"; // Add its message to the result
            }
        }
        return result; // Return all matches (e.g. Message 2 + Message 5 for +27838884567)
    }

    // Task 2e: Delete a message using its hash
    public String deleteByHash(String hash) {
        // Loop through all hashes
        for (int i = 0; i < messageHashes.size(); i++) {
            // If this hash matches the one to delete
            if (messageHashes.get(i).equals(hash)) {
                String deleted = allMessages.get(i); // Remember the text before we delete it
                // Remove from ALL lists at the same index so arrays stay parallel
                allMessages.remove(i);
                recipients.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                flags.remove(i);
                // Also remove from the lists
                storedMessages.remove(deleted);
                sentMessages.remove(deleted);
                disregardedMessages.remove(deleted);
                return "Message: \"" + deleted + "\" successfully deleted.";
            }
        }
        return "Message hash not found"; // No hash matched
    }

    // Task 2f: Display full report - Hash, Recipient, Message for SENT messages only
    public String getFullReport() {
        String report = ""; // Will hold the full report text
        // Loop through all messages
        for (int i = 0; i < allMessages.size(); i++) {
            // Only include Sent messages
            if (flags.get(i).equals("Sent")) {
                // Build one block: Hash, then Recipient, then Message
                report += "Hash: " + messageHashes.get(i)
                        + "\nRecipient: " + recipients.get(i)
                        + "\nMessage: " + allMessages.get(i) + "\n\n";
            }
        }
        return report; // Return the full report
    }
}