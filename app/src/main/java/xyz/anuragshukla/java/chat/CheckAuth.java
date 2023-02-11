package xyz.anuragshukla.java.chat;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.PhantomReference;

public class CheckAuth implements Runnable{
    Context context;

    VerifyAuth callback;
   SharedPreferences prefs;
    public CheckAuth(Context context, VerifyAuth callback){
        this.context = context;
        this.callback = callback;
        this.prefs =  context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
    }

    @Override
    public void run() {
        String authToken = prefs.getString("token", null);
       this.callback.onResponse(authToken);
    }
}
