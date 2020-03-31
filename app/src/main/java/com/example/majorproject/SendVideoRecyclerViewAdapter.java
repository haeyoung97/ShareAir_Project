package com.example.majorproject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SendVideoRecyclerViewAdapter extends RecyclerView.Adapter<SendVideoRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendVideo> sendVideos;
    private Context context;

    public SendVideoRecyclerViewAdapter(ArrayList<SendVideo> sendVideos, Context context) {
        this.sendVideos = sendVideos;
        this.context = context;
    }


    @NonNull
    @Override
    public SendVideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
      LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_video_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendVideoRecyclerViewAdapter.ViewHolder holder, int position) {
        SendVideo item = sendVideos.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return sendVideos.size();
    }

    public void addItem(SendVideo item){
        sendVideos.add(item);
    }

    public void setItems(ArrayList<SendVideo> item){
        this.sendVideos = item;
    }

    public SendVideo getItem(int position){
        return sendVideos.get(position);
    }

    public void setItem(int position, SendVideo item){
        sendVideos.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView filename;
        private TextView date;
        private TextView timeLength;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_video_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_video_checkbox);
            imageView = (ImageView)itemView.findViewById(R.id.send_video_image);
            filename = (TextView)itemView.findViewById(R.id.send_video_filename);
            timeLength = (TextView)itemView.findViewById(R.id.send_video_length);
            date = (TextView)itemView.findViewById(R.id.send_video_date);



            itemView.setOnClickListener(this);
        }
        public void setItem(SendVideo item){
                Picasso.with(context)
                        .load(Uri.parse("file://" + item.getFilepath()))
                        .into(imageView);

            filename.setText(item.getFilename());
            date.setText(item.getDate());
            timeLength.setText(item.getTimeLength());

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                if(!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    sendVideos.get(pos).setCheck(true);
                    MainActivity.selectList.add(sendVideos.get(pos).getFilepath());
//                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
                }
                else{
                    checkBox.setChecked(false);
                    sendVideos.get(pos).setCheck(false);
                    MainActivity.selectList.remove(sendVideos.get(pos).getFilepath());
//                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                }
//                Log.d("recentFIle?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
//                Log.d("selectList ", MainActivity.selectList.get(0).getName());
            }
        }
    }
}
