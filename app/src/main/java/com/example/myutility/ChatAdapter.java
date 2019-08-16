package com.example.myutility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickname;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_nickname;
        public TextView tv_message;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            tv_nickname = v.findViewById(R.id.tv_nickname);
            tv_message = v.findViewById(R.id.tv_message);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickname) {
        mDataset = myDataset;
        this.myNickname = myNickname;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatting, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatData chat = mDataset.get(position);

        holder.tv_nickname.setText(chat.getNickname());
        holder.tv_message.setText(chat.getMsg());

        if(chat.getNickname().equals(this.myNickname)){
            holder.tv_message.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.tv_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        else {
            holder.tv_message.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.tv_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public ChatData getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
    }
}
