package com.mycompany.javapoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {

    MessageManager manager;

    @BeforeEach
    public void setUp() {
        manager = new MessageManager();
        manager.setSender("+27834557896");
        manager.populateTestData();
    }

    // a) Display sender and recipient of all stored messages
    @Test
    public void testDisplaySenderAndRecipient() {
        String result = manager.displaySenderAndRecipient();
        assertTrue(result.contains("+27834557896"));
        assertTrue(result.contains("+27838884567"));
        assertEquals(2, manager.storedMessages.size());
    }

    // b) Display longest stored message
    @Test
    public void testGetLongestMessage() {
        String longest = manager.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // c) Search for a message by its Message ID - stored message
    @Test
    public void testSearchByMessageID() {
        String result = manager.searchByMessageID("2222222222");
        assertTrue(result.contains("+27838884567"));
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
    }

    // c) Search for Message ID 0838884567 - the special Sent message from POE
    @Test
    public void testSearchByMessageID_0838884567() {
        String result = manager.searchByMessageID("0838884567");
        assertTrue(result.contains("Recipient: 0838884567"));
        assertTrue(result.contains("It is dinner time !"));
    }

    // c) Search for ID that does not exist
    @Test
    public void testSearchByMessageID_NotFound() {
        String result = manager.searchByMessageID("9999999999");
        assertEquals("Message ID not found.", result);
    }

    // d) Search for all messages for a recipient
    @Test
    public void testSearchByRecipient() {
        String result = manager.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    // d) Search for recipient with no messages
    @Test
    public void testSearchByRecipient_NotFound() {
        String result = manager.searchByRecipient("+27000000000");
        assertEquals("No messages for this recipient.", result);
    }

    // e) Delete a message using message hash - Hash for message 2 is 22:1:TIME
    @Test
    public void testDeleteByHash() {
        String result = manager.deleteByHash("22:1:TIME");
        assertTrue(result.contains("successfully deleted"));
        assertTrue(result.contains("Where are you? You are late!"));
        assertEquals(1, manager.storedMessages.size());
        assertEquals("Message ID not found.", manager.searchByMessageID("2222222222"));
    }

    // e) Delete with wrong hash
    @Test
    public void testDeleteByHash_NotFound() {
        String result = manager.deleteByHash("00:0:WRONG");
        assertEquals("Hash not found.", result);
        assertEquals(2, manager.storedMessages.size());
    }
    
    // f) Display full report
    @Test
    public void testGetFullReport() {
        String report = manager.getFullReport();
        assertTrue(report.contains("Message Hash: 22:1:TIME"));
        assertTrue(report.contains("Message Hash: 55:4:YOU"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(report.contains("Ok, I am leaving without you."));
    }

    // Extra - Test makeHash formula
    @Test
    public void testMakeHash() {
        String hash = manager.makeHash("2222222222", 1, "Where are you? You are late! I have asked you to be on time.");
        assertEquals("22:1:TIME", hash);
    }
}
