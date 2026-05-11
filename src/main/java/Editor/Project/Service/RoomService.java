package Editor.Project.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    private final Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();
    private final Map<String, String> sessionRoomMap = new ConcurrentHashMap<>();
    private final Map<String, String> roomCodeMap = new ConcurrentHashMap<>();
    private final Map<String, String> userNames = new ConcurrentHashMap<>();

    public int addUser(String roomId, String userId, String username) {
        roomUsers.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
        roomUsers.get(roomId).add(userId);
        userNames.put(userId, username);
        return roomUsers.get(roomId).size();
    }



    public int removeUser(String roomId, String userId) {

        if (roomUsers.containsKey(roomId)) {
            roomUsers.get(roomId).remove(userId);
            return roomUsers.get(roomId).size();
        }
        return 0;
    }
    public Set<String> getUserNames(String roomId) {

        Set<String> names = ConcurrentHashMap.newKeySet();

        for (String userId : roomUsers.getOrDefault(roomId, Set.of())) {
            names.add(userNames.get(userId));
        }

        return names;
    }




    public String getUsername(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    public String getRoomId(String sessionId) {
        return sessionRoomMap.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessionUserMap.remove(sessionId);
        sessionRoomMap.remove(sessionId);
    }
    public void updateCode(String roomId, String code) {
        roomCodeMap.put(roomId, code);
    }

    public String getCode(String roomId) {
        return roomCodeMap.getOrDefault(roomId, "");
    }
    public void mapSession(String sessionId, String userId, String roomId) {
        sessionUserMap.put(sessionId, userId);
        sessionRoomMap.put(sessionId, roomId);
    }

    public String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }
}