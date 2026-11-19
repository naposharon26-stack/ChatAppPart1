/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 
 * 
 *
 * 
 * 
 * 
 *
 * @author Student
 */
public class MessageManager {

    public static final String FLAG_SENT = "Sent";
    public static final String FLAG_STORED = "Stored";
    public static final String FLAG_DISREGARD = "Disregard";

    // The five arrays required by the brief
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private final ArrayList<Message> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();

    // ---------- Populating the arrays ----------

    /** Adds a message to the array that matches its flag, plus the hash and ID arrays. */
    public void addMessage(Message message, String flag) {
        switch (flag) {
            case FLAG_SENT:
                sentMessages.add(message);
                break;
            case FLAG_STORED:
                storedMessages.add(message);
                break;
            case FLAG_DISREGARD:
                disregardedMessages.add(message);
                break;
            default:
                throw new IllegalArgumentException("Unknown flag: " + flag);
        }
        messageHashes.add(message.getMessageHash());
        messageIDs.add(message.getMessageID());
    }

    /**
     * Reads the JSON file written by Message.storeMessage() into the stored
     * messages array. Messages that are already loaded are skipped.
     */
    public void loadStoredMessages(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || Files.size(path) == 0) {
            return;
        }
        try {
            JSONObject all = new JSONObject(Files.readString(path, StandardCharsets.UTF_8));
            Iterator<String> keys = all.keys();
            while (keys.hasNext()) {
                Message m = Message.fromJSON(all.getJSONObject(keys.next()));
                if (!messageIDs.contains(m.getMessageID())) {
                    addMessage(m, FLAG_STORED);
                }
            }
        } catch (JSONException e) {
            throw new IOException("Could not read the JSON file: " + e.getMessage(), e);
        }
    }

    /** Rewrites the JSON file so it matches the stored messages array. */
    public void saveStoredMessages(String filePath) throws IOException {
        try {
            JSONObject all = new JSONObject();
            for (Message m : storedMessages) {
                all.put(m.getMessageID(), m.toJSON());
            }
            Files.writeString(Paths.get(filePath), all.toString(2), StandardCharsets.UTF_8);
        } catch (JSONException e) {
            throw new IOException("Could not write the JSON file: " + e.getMessage(), e);
        }
    }

    // ---------- Stored Messages menu features ----------

    /** a. Sender and recipient of every stored message. */
    public String displaySenderAndRecipient(String sender) {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(sender)
              .append(" | Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString().trim();
    }

    /** b. The longest stored message, or null if there are none. */
    public String getLongestMessage() {
        Message longest = null;
        for (Message m : storedMessages) {
            if (longest == null || m.getMessage().length() > longest.getMessage().length()) {
                longest = m;
            }
        }
        return longest == null ? null : longest.getMessage();
    }

    /** c. Searches by message ID and returns the message text (null if not found). */
    public String searchByMessageID(String id) {
        Message m = findByID(id);
        return m == null ? null : m.getMessage();
    }

    /** c. Recipient and message for a message ID, ready to print. */
    public String searchByMessageIDDetails(String id) {
        Message m = findByID(id);
        if (m == null) {
            return "No message found with ID " + id + ".";
        }
        return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessage();
    }

    /** d. Every sent or stored message for a recipient. */
    public List<String> searchByRecipient(String recipient) {
        List<String> found = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipient)) {
                found.add(m.getMessage());
            }
        }
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) {
                found.add(m.getMessage());
            }
        }
        return found;
    }

    /** e. Deletes a message using its hash from whichever array holds it. */
    public String deleteByHash(String hash) {
        for (ArrayList<Message> list : List.of(sentMessages, storedMessages, disregardedMessages)) {
            for (int i = 0; i < list.size(); i++) {
                Message m = list.get(i);
                if (m.getMessageHash().equals(hash)) {
                    list.remove(i);
                    int index = messageHashes.indexOf(hash);
                    messageHashes.remove(index);
                    messageIDs.remove(index);
                    return "Message: \"" + m.getMessage() + "\" successfully deleted.";
                }
            }
        }
        return "No message found with hash " + hash + ".";
    }

    /** f. Report with the full details of all stored messages. */
    public String displayReport() {
        return buildReport("STORED MESSAGES REPORT", storedMessages);
    }

    /** Same report for the sent messages. */
    public String displaySentReport() {
        return buildReport("SENT MESSAGES REPORT", sentMessages);
    }

    private String buildReport(String title, List<Message> list) {
        StringBuilder sb = new StringBuilder("===== " + title + " =====\n");
        if (list.isEmpty()) {
            sb.append("No messages.\n");
        }
        for (Message m : list) {
            sb.append("Message Hash: ").append(m.getMessageHash()).append("\n")
              .append("Message ID: ").append(m.getMessageID()).append("\n")
              .append("Recipient: ").append(m.getRecipient()).append("\n")
              .append("Message: ").append(m.getMessage()).append("\n")
              .append("-----------------------------\n");
        }
        return sb.toString();
    }

    private Message findByID(String id) {
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);
        for (Message m : all) {
            if (m.getMessageID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    // ---------- Getters ----------

    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }

    /** Message texts of a list, in order (handy for assertions). */
    public static List<String> texts(List<Message> list) {
        List<String> out = new ArrayList<>();
        for (Message m : list) {
            out.add(m.getMessage());
        }
        return out;
    }
}
