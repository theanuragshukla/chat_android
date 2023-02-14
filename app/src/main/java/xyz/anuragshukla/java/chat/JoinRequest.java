package xyz.anuragshukla.java.chat;

public class JoinRequest {
    private String roomId;
    private String authToken;

    JoinRequest(String room, String authToken){
        this.roomId = room;
        this.authToken = authToken;
    }
}
