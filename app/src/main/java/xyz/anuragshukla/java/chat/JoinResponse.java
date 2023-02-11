package xyz.anuragshukla.java.chat;

public class JoinResponse {
private String token;
public JoinResponse(String token){
    this.token = token;
}

public String getToken(){
    return  this.token;
}
}
