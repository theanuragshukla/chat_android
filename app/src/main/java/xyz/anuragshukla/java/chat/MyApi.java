package xyz.anuragshukla.java.chat;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyApi {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @POST("/joinRoom")
    Call<JoinResponse> joinRoom(@Body JoinRequest request);
}
