package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/chatlog")
public class ChatServerController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(method = RequestMethod.POST, value="/{user}") // Map ONLY GET Requests
    public @ResponseBody long addNewLog(@PathVariable("user") String userName,
                                           @RequestBody Message newMessage) {
        newMessage.setUserName(userName);
        System.out.println(userName);
        System.out.println(newMessage.toString());
        return chatService.addMessage(newMessage);
    }

    @RequestMapping(method = RequestMethod.GET, path="/{user}")
    public @ResponseBody Iterable<Message> getAllMessagesByUser(
                                           @PathVariable("user") String userName,
                                           @RequestParam(value = "limit", required = false, defaultValue = "0") Long limit,
                                           @RequestParam(value= "start", required = false, defaultValue = "0") Long startId
                                           ) {
        return chatService.getAllMessagesByUserNameFromStartIdTillLimit(userName, startId, limit);
    }

    @RequestMapping(method = RequestMethod.DELETE, path="/{user}")
    public @ResponseBody String deleteUserMessages(@PathVariable("user") String userName){
        return chatService.deleteMessageByUser(userName);
    }

    @RequestMapping(method = RequestMethod.DELETE, path="/{user}/{messageId}")
    public @ResponseBody String deleteMessageById(@PathVariable("user") String userName,
                                  @PathVariable("messageId") Long messageId){
        return chatService.deleteMessageForUserById(userName, messageId);
    }


}