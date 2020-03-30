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

public class SendFileRecyclerViewAdapter extends RecyclerView.Adapter<SendFileRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendFile> sendFiles;
    private Context context;

    public SendFileRecyclerViewAdapter(ArrayList<SendFile> sendFiles, Context context) {
        this.sendFiles = sendFiles;
        this.context = context;
    }


    @NonNull
    @Override
    public SendFileRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        Log.d("SendRecentRecycler : ", sendRecents.get(1).getFilePath());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_file_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendFileRecyclerViewAdapter.ViewHolder holder, int position) {
        SendFile item = sendFiles.get(position);
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
        return sendFiles.size();
    }

    public void addItem(SendFile item){
        sendFiles.add(item);
    }

    public void setItems(ArrayList<SendFile> item){
        this.sendFiles = item;
    }

    public SendFile getItem(int position){
        return sendFiles.get(position);
    }

    public void setItem(int position, SendFile item){
        sendFiles.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView filename;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_file_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_file_checkbox);
            imageView = (ImageView)itemView.findViewById(R.id.send_file_image);
            filename = (TextView)itemView.findViewById(R.id.send_file_filename);
            date = (TextView)itemView.findViewById(R.id.send_file_date);



            itemView.setOnClickListener(this);
        }
        public void setItem(SendFile item){


//            Log.d("picasso : ", item.getImagePath());
            if(item.getFileExtNum() == 5) {
                Picasso.with(context)
                        .load(Uri.parse("file://" + item.getFilepath()))
                        .into(imageView);
            }
            else{
                Log.d("resource? ", Integer.toString(item.getResourceFile()));
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
                    sendFiles.get(pos).setCheck(true);
//                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
                }
                else{
                    checkBox.setChecked(false);
                    sendFiles.get(pos).setCheck(false);
//                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                }
//                Log.d("recentFIle?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
//                Log.d("selectList ", MainActivity.selectList.get(0).getName());
            }
        }
    }
}
