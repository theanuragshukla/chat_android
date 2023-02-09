package xyz.anuragshukla.java.chat;


public class LoginResponse {
    private String firstName;
    private String lastName;
    LoginResponse(String fName, String lName){
        this.firstName = fName;
        this.lastName = lName;
    }
    public String getName(){
        return (this.firstName + " " + this.lastName);
    }
}

