package com.example.majorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SendRecentRecyclerViewAdapter extends RecyclerView.Adapter<SendRecentRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendRecent> sendRecents;
    private Context context;
    private SendTabRecentFragment fragment;

    public SendRecentRecyclerViewAdapter(ArrayList<SendRecent> sendRecents, Context context, SendTabRecentFragment fragment) {
        this.sendRecents = sendRecents;
        this.context = context;
        this.fragment = fragment;
    }

    public SendRecentRecyclerViewAdapter(ArrayList<SendRecent> sendRecents, Context context) {
        this.sendRecents = sendRecents;
        this.context = context;
    }


    @NonNull
    @Override
    public SendRecentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_recent_cardview, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendRecentRecyclerViewAdapter.ViewHolder holder, int position) {
        //holder.imageView.setImageBitmap(sendRecents.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return sendRecents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.send_recent_cardview_check);
            imageView = (ImageView)itemView.findViewById(R.id.send_recent_cardview_image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
