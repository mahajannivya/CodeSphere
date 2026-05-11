package Editor.Project.Controller;

import Editor.Project.Entity.JoinMessage;
import Editor.Project.Entity.UserUpdate;
import Editor.Project.Service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Controller
public class EditorController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;// Inbuilt spring utility helper class for websocket

    @MessageMapping("/join")//frontend send msgs to /join
    public void joinRoom(@Payload JoinMessage msg,//receives frontend json roomId, userId , username
                         SimpMessageHeaderAccessor headerAccessor)//used to access thr sessionId, headers, WebSocket info
    {

        String roomId = msg.getRoomId();
        String userId = msg.getUserId();
        String username = msg.getUsername();

        String sessionId = headerAccessor.getSessionId();

        roomService.mapSession(sessionId, userId, roomId);// this maps sessionId-->userId-->roomId

        int count = roomService.addUser(roomId, userId, username);

        messagingTemplate.convertAndSend(//this broadcast updated user list to everyone
                "/topic/" + roomId + "/users",
                new UserUpdate(count, roomService.getUserNames(roomId))
        );

        // send existing code to the new user instantly
        messagingTemplate.convertAndSend(
                "/topic/" + roomId + "/code",
                roomService.getCode(roomId)
        );
    }


   // this handles and run automatically when the websocket disconnects
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        String sessionId = event.getSessionId();//gets disconnected session

        String userId = roomService.getUserId(sessionId);// finds who disconnected
        String roomId = roomService.getRoomId(sessionId);//from which room

        if (userId != null && roomId != null) {

            int count = roomService.removeUser(roomId, userId);// removes user from room

            //this broadcasts updated user
            messagingTemplate.convertAndSend(
                    "/topic/" + roomId + "/users",
                    new UserUpdate(count, roomService.getUserNames(roomId))
            );

            //cleans memory
            roomService.removeSession(sessionId);
        }
    }
    //Synchronizes live code editing
    @MessageMapping("/code")
    public void syncCode(@Payload Map<String, String> payload) {
        String roomId = payload.get("roomId");// extract roomId
        String code = payload.get("code");//extract code

        roomService.updateCode(roomId, code);
        //broadcasts code to all the users instantly
        messagingTemplate.convertAndSend(
                "/topic/" + roomId + "/code",
                code
        );
    }

    //synchronises program output between users
    @MessageMapping("/output")
    public void sendOutput(Map<String, String> payload){

        String roomId = payload.get("roomId");
        String output = payload.get("output");
          //broadcasts output to room users
        messagingTemplate.convertAndSend(
                "/topic/" + roomId + "/output",
                output
        );
    }
}