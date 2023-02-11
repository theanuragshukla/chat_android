package xyz.anuragshukla.java.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {
    private EditText input;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = findViewById(R.id.inputEditText);

        recyclerView = findViewById(R.id.recyclerView);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

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
                Message message = new Message(text);
                messages.add(message);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messages.size() - 1);
                input.setText("");
                return false;
            }
        });
    }

}
