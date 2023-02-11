package xyz.anuragshukla.java.chat;


public class LoginResponse {
    private String token;
    LoginResponse(String token){
        this.token = token;
    }
    public String getToken(){
        return (this.token);
    }
}

