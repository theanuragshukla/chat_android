package xyz.anuragshukla.java.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

interface JoinResult {
    void onResponse(String sessionID);
}

public class MainActivity extends AppCompatActivity {
    private String authToken = null;
    private String sessionToken = null;
    private void joinRoom(String  roomId, final JoinResult callback) {
        JoinRequest request = new JoinRequest(roomId, authToken);
        MyApi myApi = APIClient.getClient().create(MyApi.class);
        Call<JoinResponse> call = myApi.joinRoom(request);
        call.enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                if (response.isSuccessful()) {
                    JoinResponse joinResponse = response.body();
                    callback.onResponse(joinResponse.getToken());

                } else {
                    System.out.println(response.body());
                    callback.onResponse(null);
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                callback.onResponse(null);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextInputEditText roomTextInputEditText = findViewById(R.id.roomTextInputEditText);
        MaterialButton loginButton = findViewById(R.id.joinButton);
        new Thread(new CheckAuth(this, new VerifyAuth() {
            @Override
            public void onResponse(String token) {
                Intent redirect;
                System.out.println(token);
                if(token==null){
                    System.out.println("token null");
                    redirect = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(redirect);
                }else{
                    authToken = token;
                }
            }
        })).start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the login action here
                String roomId = roomTextInputEditText.getText().toString();

                joinRoom(roomId,  new JoinResult() {
                    @Override
                    public void onResponse(String sessionID) {
                        if(sessionID!=null){
                            SharedPreferences prefs = getSharedPreferences("SESSION",MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("sessionID", sessionID);
                            editor.commit();
                            sessionToken = sessionID;
                            Intent moveToChat = new Intent(MainActivity.this, ChatActivity.class);
                            startActivity(moveToChat);
                        }
                    }
                });
            }
        });
    }
}