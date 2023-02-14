package xyz.anuragshukla.java.chat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends Activity {
    private EditText input;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;
    private String sessionID = null;
    private SharedPreferences prefs;
private Socket mSocket;

private void addNewMessage(String sender, String msg){
    this.messages.add(new Message(sender, msg));
    adapter.notifyDataSetChanged();
    recyclerView.smoothScrollToPosition(messages.size() - 1);
}

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String from;
                    String message;
                    try {
                        from = data.getString("from");
                        message = data.getString("message");
                        System.out.println(from);
                    } catch (JSONException e) {
                        System.out.println(e);
                        return;
                    }

                    // add the message to view
                    addNewMessage(from, message);
                }
            });
        }
    };
    private void connectSocket(){
        IO.Options options = IO.Options.builder()
                .setQuery("sessionID="+sessionID)
                .build();
        mSocket = IO.socket(URI.create(Constants.server), options);
        mSocket.connect();
        mSocket.on("newMessage", onNewMessage);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        input = findViewById(R.id.inputEditText);
        recyclerView = findViewById(R.id.recyclerView);
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        prefs = getSharedPreferences("SESSION", MODE_PRIVATE);
        sessionID = prefs.getString("sessionID", null);

        if(sessionID==null){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            connectSocket();
        }

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if(input.getText().toString().length()==0)
                        input.setHint("Message");
                    else
                        input.setHint("");
                } else {
                    input.setHint("");
                }
            }
        });
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String text = input.getText().toString();
                Message message = new Message("you", text);
                messages.add(message);
                mSocket.emit("newMessage", text);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messages.size() - 1);
                input.setText("");
                return false;
            }
        });
    }

}
