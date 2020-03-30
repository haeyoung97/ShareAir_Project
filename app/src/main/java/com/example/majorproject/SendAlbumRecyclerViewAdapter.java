package com.example.majorproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SendAlbumRecyclerViewAdapter extends RecyclerView.Adapter<SendAlbumRecyclerViewAdapter.ViewHolder> {

    private ArrayList<SendAlbum> sendAlbums;
    private Context context;

    public SendAlbumRecyclerViewAdapter(ArrayList<SendAlbum> sendAlbums, Context context) {
        this.sendAlbums = sendAlbums;
        this.context = context;
    }

    @NonNull
    @Override
    public SendAlbumRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_album_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendAlbumRecyclerViewAdapter.ViewHolder holder, int position) {
//        Bitmap resizeBitmap = null;
//        BitmapSizeModify bitmapThread = new BitmapSizeModify(sendAlbums.get(position).getImagePath(), resizeBitmap);
//        bitmapThread.start();
//        while(true){
//            if(!bitmapThread.isAlive()){
//                holder.imageView.setImageBitmap(resizeBitmap);
//                holder.checkBox.setChecked(false);
//                break;
//            }
//        }
//        try {
//            bitmapThread.join();
//            resizeBitmap = bitmapThread.getResizeBitmap();
//            Log.d("albumBind : ", "yes");
//            holder.albumImage.setImageBitmap(resizeBitmap);
//            holder.albumName.setText(sendAlbums.get(position).getImageName());
//            holder.albumSum.setText(Integer.toString(sendAlbums.get(position).getImageSum()));
//            holder.checkBox.setChecked(false);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return sendAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox checkBox;
        private TextView albumName;
        private TextView albumSum;
        private ImageView albumImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_album_cardview_check);
            albumName = (TextView)itemView.findViewById(R.id.send_album_cardview_imageName);
            albumSum = (TextView)itemView.findViewById(R.id.send_album_cardview_sumImage);
            albumImage = (ImageView)itemView.findViewById(R.id.send_album_cardview_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("clickListener", "listen!");
            if(pos != RecyclerView.NO_POSITION){
                if(!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    sendAlbums.get(pos).setImageCheck(true);
//                    int startIdx = MainActivity.albumList.get(sendAlbums.get(pos).getIndex()).getStartIdx();
//                    for(int i = 0; i < sendAlbums.get(pos).getImageSum(); i++) {
//                        MainActivity.selectList.add(MainActivity.imageList.get(startIdx + i).getFile());
//                    }
//                    MainActivity.selectList.add(MainActivity.albumList.get(sendAlbums.get(pos).getIndex()));
                }
                else{
                    checkBox.setChecked(false);
                    sendAlbums.get(pos).setImageCheck(false);
//                    int startIdx = MainActivity.albumList.get(sendAlbums.get(pos).getIndex()).getStartIdx();
//                    for(int i = 0; i < sendAlbums.get(pos).getImageSum(); i++) {
//                        MainActivity.selectList.remove(MainActivity.imageList.get(startIdx + i));
//                    }
//                    MainActivity.selectList.remove(MainActivity.albumList.get(sendAlbums.get(pos).getIndex()));
                }
            }

        }
    }
}
