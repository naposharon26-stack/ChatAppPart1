QuickChat – Part 3: Store Data and Display Task

Overview
QuickChat is a Java console chat application built with Maven in NetBeans.
Part 1 added registration and login, Part 2 added sending messages, and
Part 3 (this part) adds arrays, JSON storage and a "Stored Messages" menu.

Learning outcomes covered in Part 3:
- Handling and manipulating strings
- Creating and working with arrays

What I added in Part 3

1. Arrays (no hard-coding)
A new class, `MessageManager`, holds five `ArrayList`s. They are filled while
the program runs, never hard-coded:

 Array | Contents 

Sent messages | All messages that were sent 
Disregarded messages | All messages that were disregarded 
Stored messages | Messages read from the JSON file (and newly stored ones) 
Message hashes | The hash of every message 
Message IDs | The ID of every message 

Messages are added with `addMessage(message, flag)`, where the flag is
`Sent`, `Stored` or `Disregard`.

 2. Reading the JSON file into an array
Messages stored with option 3 ("Store Message") are written to
`storedMessages.json` by the Part 2 `Message` class. When the app starts,
`MessageManager.loadStoredMessages()` reads this file into the stored messages
array using the `org.json` library
(org.json (2024) *JSON-java, JSONObject documentation*.
Available at: https://stleary.github.io/JSON-java/org/json/JSONObject.html).

 3. New main menu option: "4) Stored Messages"
The sub-menu lets the user:

| Option | Feature |
a | Display the sender and recipient of all stored messages |
b | Display the longest stored message |
c | Search for a message ID and display the recipient and message |
d | Search all sent or stored messages for a particular recipient |
e | Delete a message using its message hash (the JSON file is updated too) |
f | Display a report with the full details of all stored messages |
g | Return to the main menu |

A matching report for sent messages is also available (`displaySentReport()`).

Files
Main code (`src/main/java/com/mycompany/chatapppart1`):
- `ChatAppPart1.java` – main class; menu now includes option 4 and fills the arrays
- `MessageManager.java` – *new*: the arrays and the Stored Messages features
- `Message.java` – Part 2 (unchanged)
- `Login.java` – Part 1 (unchanged)

Tests (`src/test/java/com/mycompany/chatapppart1`):
- `MessageManagerTest.java` – *new*: Part 3 unit tests
- `MessageTest.java`, `LoginTest.java` – earlier parts (unchanged)

Data file in the project root:
- `storedMessages.json`

 Unit tests
`MessageManagerTest` uses the five test data messages from the brief:

| # | Recipient | Message | Flag |
| 1 | +27834557896 | Did you get the cake? | Sent |
| 2 | +27838884567 | Where are you? You are late! I have asked you to be on time. | Stored |
| 3 | +27834484567 | Yohoooo, I am at your gate. | Disregard |
| 4 | Developer 0838884567 | It is dinner time! | Sent |
| 5 | +27838884567 | Ok, I am leaving without you. | Stored |

Tests (11 in total):
- Sent messages array is correctly populated (messages 1–4)
- Disregarded, stored, hash and ID arrays are populated
- Display the longest message
- Search for a message ID (`0838884567` returns "It is dinner time!")
- Search all messages for a recipient (`+27838884567`)
- Delete a message using its hash (message 2)
- Delete with an unknown hash
- Display report (stored) and sent report
- Stored messages are read from the JSON file into the array
- A deleted message is removed from the JSON file

Expected delete output:
`Message: "Where are you? You are late! I have asked you to be on time" successfully deleted.`

 How to run
1. Open the project in NetBeans (the Maven dependencies `org.json` and
   JUnit 5 must be in `pom.xml`).
2. Run `ChatAppPart1` (right-click the project, *Run*).
3. Register, log in, then use the menu:
   `1) Send Messages  2) Show recently sent messages  3) Quit  4) Stored Messages`
4. To run the tests, right-click the project and choose *Test*.

Notes and assumptions
- Message 4 (Developer `0838884567`) has no international code, so its number
  is used as its message ID in the tests.
- When the JSON file is read, `org.json` does not keep the file's order, so
  stored messages may be listed in a different order after a restart.
- `storedMessages.json` must sit in the project root (next to `pom.xml`). If it
  is missing, it is created the first time a message is stored.
