package xyz.anuragshukla.java.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

interface LoginResult {
    void onResponse(boolean success);
}


public class MainActivity extends AppCompatActivity {

    private void login(String username, String password, final LoginResult callback) {
        LoginRequest request = new LoginRequest(username, password);
        MyApi myApi = APIClient.getClient().create(MyApi.class);
        Call<LoginResponse> call = myApi.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    System.out.println(response.body());
                    Log.d("TAG", loginResponse.getName());
                    callback.onResponse(true);

                } else {
                    System.out.println(response.body());
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                callback.onResponse(false);

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText usernameTextInputEditText = findViewById(R.id.usernameTextInputEditText);
        TextInputEditText passwordTextInputEditText = findViewById(R.id.passwordTextInputEditText);
        MaterialButton loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the login action here
                String username = usernameTextInputEditText.getText().toString();
                String password = passwordTextInputEditText.getText().toString();
              login(username, password, new LoginResult() {
                  @Override
                  public void onResponse(boolean success) {

                      if(true){
                          Intent moveToChat = new Intent(MainActivity.this, ChatActivity.class);
                          startActivity(moveToChat);
                      }
                  }
              });
            }
        });

    }
}