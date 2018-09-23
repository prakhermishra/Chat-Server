package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Service
public class ChatService {

    private static Long defaultLimit = 10L;

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessagesByUserNameFromStartIdTillLimit(String userName, Long startId, Long limit){
        if(limit <0 )
            limit = defaultLimit;
        return (List<Message>)messageRepository.findTopNUserNameOrderByMessageIdDescTillLimit(userName,startId,limit.intValue());
    }


    public long addMessage(Message message){
        return  messageRepository.save(message).getMessageId();
    }

    public ResponseEntity<String> deleteMessageById(long messageId){
        Message deleteMessage = messageRepository.findByMessageId(messageId);
        try{
            if(deleteMessage != null) {
                messageRepository.deleteById(deleteMessage.getMessageId());
                return ResponseEntity.ok(String.format("message deleted with id %d", messageId));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("no message with id %d exists in the message logs", messageId));
        }
        catch (NullPointerException npe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("no message with id %d exists in the message logs", messageId));
        }
    }

    public ResponseEntity<String> deleteMessageForUserById(String userName, Long messageId){
        List<Message> messagesForUser = messageRepository.findAllByUserName(userName);
        for(Message msg : messagesForUser){
            if(msg.getMessageId() == messageId){
                return deleteMessageById(messageId);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                String.format("No message with id %d exists for user %s", messageId, userName));
    }

    public ResponseEntity<String> deleteMessageByUser(String userName){
        List<Message> messagesToBeDeleted = messageRepository.findAllByUserName(userName);
        try{
            if(messagesToBeDeleted.size() != 0){
                for(Message message : messagesToBeDeleted)
                    messageRepository.deleteById(message.getMessageId());
                return ResponseEntity.ok(String.format("all messages for user %s are deleted", userName));
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        String.format("there is no message for the user %s", userName));
        }
        catch (NullPointerException npe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("there is no message for the user %s", userName));
        }
    }
}