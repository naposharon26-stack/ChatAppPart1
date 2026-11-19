/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Part 3 unit tests, using the five test data messages from the brief.
 *
 * @author Student
 */
public class MessageManagerTest {

    private static final String TEST_STORE = "testStoredMessagesPart3.json";

    private MessageManager manager;
    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    void setUp() {
        Message.resetSession();
        Message.setStoreFile(TEST_STORE);
        new File(TEST_STORE).delete();

        manager = new MessageManager();

        m1 = new Message("1000000001", 1, "+27834557896", "Did you get the cake?");
        m2 = new Message("2000000002", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        m3 = new Message("3000000003", 3, "+27834484567", "Yohoooo, I am at your gate.");
        // Test data message 4 is the "Developer" entry: 0838884567
        m4 = new Message("0838884567", 4, "0838884567", "It is dinner time!");
        m5 = new Message("5000000005", 5, "+27838884567", "Ok, I am leaving without you.");
    }

    @AfterEach
    void tearDown() {
        new File(TEST_STORE).delete();
    }

    /** Populates the arrays with test data messages 1-4 and their flags. */
    private void loadMessages1to4() {
        manager.addMessage(m1, MessageManager.FLAG_SENT);
        manager.addMessage(m2, MessageManager.FLAG_STORED);
        manager.addMessage(m3, MessageManager.FLAG_DISREGARD);
        manager.addMessage(m4, MessageManager.FLAG_SENT);
    }

    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        loadMessages1to4();
        assertEquals(Arrays.asList("Did you get the cake?", "It is dinner time!"),
                MessageManager.texts(manager.getSentMessages()));
    }

    @Test
    void testDisregardedStoredHashAndIdArraysPopulated() {
        loadMessages1to4();
        manager.addMessage(m5, MessageManager.FLAG_STORED);
        assertEquals(Arrays.asList("Yohoooo, I am at your gate."),
                MessageManager.texts(manager.getDisregardedMessages()));
        assertEquals(Arrays.asList(m2.getMessage(), m5.getMessage()),
                MessageManager.texts(manager.getStoredMessages()));
        assertEquals(5, manager.getMessageHashes().size());
        assertEquals(5, manager.getMessageIDs().size());
        assertTrue(manager.getMessageHashes().contains(m2.getMessageHash()));
        assertTrue(manager.getMessageIDs().contains("0838884567"));
    }

    @Test
    void testDisplayTheLongestMessage() {
        loadMessages1to4();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                manager.getLongestMessage());
    }

    @Test
    void testSearchForMessageID() {
        loadMessages1to4();
        assertEquals("It is dinner time!", manager.searchByMessageID("0838884567"));
        assertNull(manager.searchByMessageID("9999999999"));
    }

    @Test
    void testSearchAllMessagesForARecipient() {
        loadMessages1to4();
        manager.addMessage(m5, MessageManager.FLAG_STORED);
        assertEquals(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."),
                manager.searchByRecipient("+27838884567"));
    }

    @Test
    void testDeleteAMessageUsingTheMessageHash() {
        loadMessages1to4();
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                manager.deleteByHash(m2.getMessageHash()));
        assertTrue(manager.getStoredMessages().isEmpty());
        assertFalse(manager.getMessageHashes().contains(m2.getMessageHash()));
        assertFalse(manager.getMessageIDs().contains(m2.getMessageID()));
    }

    @Test
    void testDeleteWithUnknownHash() {
        loadMessages1to4();
        assertEquals("No message found with hash NOPE.", manager.deleteByHash("NOPE"));
        assertEquals(4, manager.getMessageHashes().size());
    }

    @Test
    void testDisplayReport() {
        loadMessages1to4();
        manager.addMessage(m5, MessageManager.FLAG_STORED);
        String report = manager.displayReport();
        for (Message m : Arrays.asList(m2, m5)) {
            assertTrue(report.contains("Message Hash: " + m.getMessageHash()));
            assertTrue(report.contains("Recipient: " + m.getRecipient()));
            assertTrue(report.contains("Message: " + m.getMessage()));
        }
        assertFalse(report.contains(m1.getMessage()));
    }

    @Test
    void testSentReportShowsHashRecipientAndMessage() {
        loadMessages1to4();
        String report = manager.displaySentReport();
        assertTrue(report.contains("Message Hash: " + m1.getMessageHash()));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
    }

    @Test
    void testStoredMessagesAreReadFromJsonIntoArray() throws IOException {
        // Written by Part 2's storeMessage(), then read back into the array
        m2.SentMessage(Message.STORE);
        m5.SentMessage(Message.STORE);

        manager.loadStoredMessages(TEST_STORE);

        // org.json does not keep the file's order, so compare without order
        assertEquals(2, manager.getStoredMessages().size());
        assertTrue(MessageManager.texts(manager.getStoredMessages()).contains(m2.getMessage()));
        assertTrue(MessageManager.texts(manager.getStoredMessages()).contains(m5.getMessage()));
        assertEquals(2, manager.getMessageIDs().size());
    }

    @Test
    void testDeletedMessageIsRemovedFromJsonFile() throws IOException {
        m2.SentMessage(Message.STORE);
        m5.SentMessage(Message.STORE);
        manager.loadStoredMessages(TEST_STORE);

        manager.deleteByHash(m2.getMessageHash());
        manager.saveStoredMessages(TEST_STORE);

        assertEquals(1, Message.countStoredMessages());
        assertNull(Message.readStoredMessage("2000000002"));
    }
}