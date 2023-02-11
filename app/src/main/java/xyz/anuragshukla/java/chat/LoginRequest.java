package xyz.anuragshukla.java.chat;
public class LoginRequest {
    private String username;
    private String uid;

    LoginRequest(String username, String uid){
        this.uid = uid;
        this.username = username;
    }
    // constructor, getters, and setters
}
