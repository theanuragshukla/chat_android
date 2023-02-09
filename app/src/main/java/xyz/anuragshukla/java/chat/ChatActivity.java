package xyz.anuragshukla.java.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {
    private EditText input;
    private Button sendButton;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = findViewById(R.id.inputEditText);
        sendButton = findViewById(R.id.sendButton);
        recyclerView = findViewById(R.id.recyclerView);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                Message message = new Message(text);
                messages.add(message);
                adapter.notifyDataSetChanged();
                input.setText("");
            }
        });

    }
}
