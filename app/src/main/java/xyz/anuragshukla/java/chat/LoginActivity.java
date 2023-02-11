package xyz.anuragshukla.java.chat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


interface LoginResult {
    void onResponse(boolean success);
}

public class LoginActivity extends AppCompatActivity {

    private void login(String username, String uid, final LoginResult callback) {
        LoginRequest request = new LoginRequest(username, uid);
        MyApi myApi = APIClient.getClient().create(MyApi.class);
        Call<LoginResponse> call = myApi.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String authToken = loginResponse.getToken();
                    SharedPreferences prefs = getSharedPreferences("AUTH", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", authToken);
                    editor.commit();
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

    private String getUid(){
        String UID = Build.BOARD+ Build.BRAND +
                Build.CPU_ABI + Build.DEVICE+
                Build.DISPLAY + Build.HOST+
                Build.ID + Build.MANUFACTURER+
                Build.MODEL + Build.PRODUCT +
                Build.TAGS + Build.TYPE +
                Build.USER ;

        return UID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_login);
        new Thread(new CheckAuth(this, new VerifyAuth() {
            @Override
            public void onResponse(String token) {
                Intent redirect;
                System.out.println(token);
                if(token!=null){
                    System.out.println("token null");
                    redirect = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(redirect);
                }
            }
        })).start();
        TextInputEditText usernameTextInputEditText = findViewById(R.id.usernameTextInputEditText);
        MaterialButton loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextInputEditText.getText().toString();
                String uid = getUid();
                login(username, uid, new LoginResult() {
                    @Override
                    public void onResponse(boolean success) {
                        if(success){
                        Intent moveToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(moveToMain);
                    }}
                });
            }
        });
    }
}
