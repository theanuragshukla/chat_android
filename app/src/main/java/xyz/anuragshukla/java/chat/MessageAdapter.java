package xyz.anuragshukla.java.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class Message {

    private String msg;
    private String sender;

    public Message(String sender, String msg) {
        this.sender = sender;
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }
    public String getMsg(){
        return msg;
    }
}

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.msgView.setText(message.getMsg());
        holder.senderView.setText(message.getSender());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView msgView;
        TextView senderView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            senderView = itemView.findViewById(R.id.senderView);
            msgView = itemView.findViewById(R.id.textView);
        }
    }
}
