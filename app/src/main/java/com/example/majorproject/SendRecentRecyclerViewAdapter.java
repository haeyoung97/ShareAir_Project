package com.example.majorproject;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SendRecentRecyclerViewAdapter extends RecyclerView.Adapter<SendRecentRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendRecent> sendRecents;
    private Context context;

    public SendRecentRecyclerViewAdapter(ArrayList<SendRecent> sendRecents, Context context) {
        this.sendRecents = sendRecents;
        this.context = context;
    }


    @NonNull
    @Override
    public SendRecentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        Log.d("SendRecentRecycler : ", sendRecents.get(1).getFilePath());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_recent_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendRecentRecyclerViewAdapter.ViewHolder holder, int position) {
        SendRecent item = sendRecents.get(position);
        holder.setItem(item);
//        Log.d("RecentBindHolder : ", sendRecents.get(position).getImagePath());
//        Bitmap resizeBitmap = null;
//        Log.d("recentThumbPath : ", sendRecents.get(position).getThumbPath());
//        BitmapSizeModify bitmapThread = new BitmapSizeModify(sendRecents.get(position).getThumbPath(), resizeBitmap, sendRecents.get(position).getFileExtNum(), context);
//        bitmapThread.start();
//
//        try {
//            bitmapThread.join();
//            Bitmap rresizeBitmap = bitmapThread.getResizeBitmap();
//            if(rresizeBitmap == null){
//                Log.d("resize ", "NULL??????");
//            }
//            holder.imageView.setImageBitmap(rresizeBitmap);
//            holder.filename.setText(sendRecents.get(position).getFilename());
//            holder.date.setText(sendRecents.get(position).getDate());
//            holder.checkBox.setChecked(false);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return sendRecents.size();
    }

    public void addItem(SendRecent item){
        sendRecents.add(item);
    }

    public void setItems(ArrayList<SendRecent> item){
        this.sendRecents = item;
    }

    public SendRecent getItem(int position){
        return sendRecents.get(position);
    }

    public void setItem(int position, SendRecent item){
        sendRecents.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView filename;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_recent_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_recent_checkbox);
            imageView = (ImageView)itemView.findViewById(R.id.send_recent_image);
            filename = (TextView)itemView.findViewById(R.id.send_recent_filename);
            date = (TextView)itemView.findViewById(R.id.send_recent_date);



            itemView.setOnClickListener(this);
        }
        public void setItem(SendRecent item){


//            Log.d("picasso : ", item.getImagePath());
            if(item.getFileExtNum() == 5) {
                Picasso.with(context)
                        .load(Uri.parse("file://" + item.getFilePath()))
                        .into(imageView);
            }
            else{
                Log.d("resourcefile? ", Integer.toString(item.getResourceFile()));
                Picasso.with(context)
                        .load(item.getResourceFile())
                        .into(imageView);
            }
            filename.setText(item.getFilename());
            date.setText(item.getDate());

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("clickListener", "listen!");
            if(pos != RecyclerView.NO_POSITION){
                Log.d("aa", "god");
                if(!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    sendRecents.get(pos).setCheck(true);
                    MainActivity.selectList.add(new FileNode(sendRecents.get(pos).getFilePath(), 0));

                    Log.e("", "onClick: " + MainActivity.selectList.get(0).getFilePath());
//                    MainActivity.selectList.add(sendRecents.get(pos).getFilePath());
//                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
                }
                else{
                    checkBox.setChecked(false);
                    sendRecents.get(pos).setCheck(false);
                    MainActivity.selectList.remove(sendRecents.get(pos));
//                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                }
//                Log.d("recentFIle?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
//                Log.d("selectList ", MainActivity.selectList.get(0).getName());
            }
        }
    }
}
