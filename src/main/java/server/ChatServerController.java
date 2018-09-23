package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/chatlogs")
public class ChatServerController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(method = RequestMethod.POST, value="/{user}") // Map ONLY GET Requests
    public ResponseEntity<Long> addNewLog(@PathVariable("user") String userName,
                                           @RequestBody Message newMessage) {
        newMessage.setUserName(userName);
        System.out.println(userName);
        System.out.println(newMessage.toString());
        return ResponseEntity.ok(chatService.addMessage(newMessage));
    }

    @RequestMapping(method = RequestMethod.GET, path="/{user}")
    public ResponseEntity<Iterable<Message>> getAllMessagesByUser(
                                           @PathVariable("user") String userName,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") Long limit,
                                           @RequestParam(value= "start", required = false, defaultValue = "0") Long startId
                                           ) {
        return ResponseEntity.ok(chatService.getAllMessagesByUserNameFromStartIdTillLimit(userName, startId, limit));
    }

    @RequestMapping(method = RequestMethod.DELETE, path="/{user}")
    public ResponseEntity<String> deleteUserMessages(@PathVariable("user") String userName){
        return chatService.deleteMessageByUser(userName);
    }

    @RequestMapping(method = RequestMethod.DELETE, path="/{user}/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable("user") String userName,
                                  @PathVariable("messageId") Long messageId){
        return chatService.deleteMessageForUserById(userName, messageId);
    }

    @RequestMapping(method = RequestMethod.GET, path="/test")
    public ResponseEntity<List<Message>> getMessage(){
        return ResponseEntity.ok(chatService.getAllMessagesByUserNameFromStartIdTillLimit("prakkk",0L,10L));
    }


}