package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {

    private static Long defaultLimit = 0L;

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessagesByUserNameFromStartIdTillLimit(String userName, Long startId, Long limit){
        if(limit <=0 )
            limit = defaultLimit;
        return (List<Message>)messageRepository.findTopNUserNameOrderByMessageIdAscTillLimit(userName,startId,limit.intValue());
    }


    public long addMessage(Message message){
        defaultLimit =  messageRepository.save(message).getMessageId();
        return defaultLimit;
    }

    public String deleteMessageById(long messageId){
        Message deleteMessage = messageRepository.findByMessageId(messageId);
        try{
            if(deleteMessage != null) {
                messageRepository.deleteById(deleteMessage.getMessageId());
                return String.format("message deleted with id %d", messageId);
            }
            return String.format("no message with id %d exists in the message logs", messageId);
        }
        catch (NullPointerException npe){
            return "no message with such Id exists";
        }
    }

    public String deleteMessageForUserById(String userName, Long messageId){
        List<Message> messagesForUser = messageRepository.findAllByUserName(userName);
        for(Message msg : messagesForUser){
            if(msg.getMessageId() == messageId){
                return deleteMessageById(messageId);
            }
        }
        return String.format("No message with id %d exists for user %s", messageId, userName);
    }

    public String deleteMessageByUser(String userName){
        List<Message> messagesToBeDeleted = messageRepository.findAllByUserName(userName);
        try{
            if(messagesToBeDeleted.size() != 0){
                for(Message message : messagesToBeDeleted)
                    messageRepository.deleteById(message.getMessageId());
                return String.format("all messages for user %s are deleted", userName);
            }
            else
                return String.format("there is no message for the user %s", userName);
        }
        catch (NullPointerException npe){
            return String.format("there is no message for the user %s", userName);
        }
    }
}