## A chat log server that will be used by other internal services.

### It has the following features: 


**POST /chatlogs/ < user >**
```
creates a new chatlog entry for the user <user>.  The POST data can either be
is JSON encoded.  The data should contain the following fields.
■ message - a String representing the message text
■ timestamp - a Long representing the timestamp
■ isSent - a Boolean/Integer representing if this message was sent by the user or received by the user

The response from the message is a unique messageID that we can refer to the message by.
```


**GET /chatlogs/ < user >**
```
Returns chatlogs for the given user.  It returns in reverse
timeorder (most recent messages first).Takes two optional parameters.
■ limit - an Integer stating how many messages should return.  Default to 10
■ start - a key of the same type as messageID to determine where to start from.  This is to help implement pagination.
If not set, the most recent messages are returned.
 ```
 
**DELETE /chatlogs/ < user >**
```
Deletes all the chatlogs for a given user.
```


**DELETE /chatlogs/ < user >/< msgid >**
 ```
○ Delete just the given chatlog for a given user.  Returns an appropriate HTTP error response if the msgid is not found.
```
===============================================================================================

**Steps to setup :**
```
 1.Clone the project.
 2.Create a local database in mysql:
   (a) Name : chat_server
   (b) db-user : chat
   (c) password : ThePassword
 3.To avoid setting up a new database remove the application.properties file in the resorces directory and an in memory db will 
   be used for each run.
 4.Run the following command from the parent folder-  ./mvnw spring-boot:run
 5.Verify Using postman.
```
