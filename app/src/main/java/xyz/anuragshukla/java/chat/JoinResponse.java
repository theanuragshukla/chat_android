package xyz.anuragshukla.java.chat;

public class JoinResponse {
private String sessionToken;
public JoinResponse(String sessionToken){
    this.sessionToken = sessionToken;
}

public String getToken(){
    return  this.sessionToken;
}
}
