/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.javapoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Message class - Page 14/15 of POE brief
public class messageTest {
    
    // Test Data from brief: +27718693002 should be valid
    @Test
    public void testCheckRecipientCell_Valid() {
        // Create message with valid number
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 0);
        String result = msg.checkRecipientCell();
        // Should return success message
        assertEquals("Cell phone number successfully captured.", result);
    }

    // Test Data from brief: 08575975889 should be invalid (no +)
    @Test
    public void testCheckRecipientCell_Invalid() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?", 1);
        String result = msg.checkRecipientCell();
        // Should contain incorrect formatting message
        assertTrue(result.contains("incorrectly formatted"));
    }

    // Test message length - valid under 250 chars
    @Test
    public void testCheckMessageLength_Valid() {
        Message msg = new Message("+27718693002", "Hi Mike", 0);
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    // Test message length - invalid over 250 chars
    @Test
    public void testCheckMessageLength_Invalid() {
        // Build 251 character message
        String longText = "a".repeat(251);
        Message msg = new Message("+27718693002", longText, 0);
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250"));
    }

    // Test Message Hash creation - should contain HITONIGHT
    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 0);
        String hash = msg.createMessageHash();
        // Hash format: XX:0:HITONIGHT - check it contains HITONIGHT
        assertTrue(hash.contains("HITONIGHT"));
        assertTrue(hash.contains(":0:"));
    }

    // Test MessageID length - must be 10 digits
    @Test
    public void testMessageID_Length() {
        Message msg = new Message("+27718693002", "Test", 0);
        assertEquals(10, msg.getMessageID().length());
    }

    // Test Send choice 1 - Message successfully sent
    @Test
    public void testSentMessage_Send() {
        Message msg = new Message("+27718693002", "Test", 0);
        String result = msg.SentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    // Test Disregard choice 2
    @Test
    public void testSentMessage_Disregard() {
        Message msg = new Message("+27718693002", "Test", 0);
        String result = msg.SentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    // Test Store choice 3
    @Test
    public void testSentMessage_Store() {
        Message msg = new Message("+27718693002", "Test", 0);
        String result = msg.SentMessage(3);
        assertEquals("Message successfully stored.", result);
    }
}