package com.example.majorproject;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SendPhotoRecyclerViewAdapter extends RecyclerView.Adapter<SendPhotoRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendPhoto> sendPhotos;
    private Context context;

    public SendPhotoRecyclerViewAdapter(ArrayList<SendPhoto> sendPhotos, Context context) {
        this.sendPhotos = sendPhotos;
        this.context = context;
    }


    @NonNull
    @Override
    public SendPhotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_photo_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendPhotoRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d("PhotoBindHolder : ", sendPhotos.get(position).getImagePath());

//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
//        String datetime = dateformat.format(c.getTime());
//        Log.d("prevTime : ", datetime);

//       // Bitmap resizeBitmap = null;
//       // BitmapSizeModify bitmapThread = new BitmapSizeModify(sendPhotos.get(position).getImagePath(), resizeBitmap);
//       // bitmapThread.start();

//        while(true){
//            if(!bitmapThread.isAlive()){
//                holder.imageView.setImageBitmap(resizeBitmap);
//                holder.checkBox.setChecked(false);
//                break;
//            }
//        }
//
//      /  try {
//            bitmapThread.join();
//            resizeBitmap = bitmapThread.getResizeBitmap();
//            holder.imageView.setImageBitmap(resizeBitmap);
//            holder.checkBox.setChecked(false);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//       / }


//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 3;
//        Bitmap bitmap = BitmapFactory.decodeFile(sendPhotos.get(position).getImagePath());
//        Bitmap resizeBitmap;
//
//        if(bitmap.getWidth() > bitmap.getHeight()){
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//            resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        }
//        else{
//            resizeBitmap = Bitmap.createScaledBitmap(bitmap, 130, 140, true);
//
//        }

        SendPhoto item = sendPhotos.get(position);
        holder.setItem(item);

    }

    public void addItem(SendPhoto item){
        sendPhotos.add(item);
    }

    public void setItems(ArrayList<SendPhoto> item){
        this.sendPhotos = item;
    }

    public SendPhoto getItem(int position){
        return sendPhotos.get(position);
    }

    public void setItem(int position, SendPhoto item){
        sendPhotos.set(position, item);
    }

    @Override
    public int getItemCount() {
        return sendPhotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;

        private BitmapFactory.Options options = new BitmapFactory.Options();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            options.inSampleSize = 12;

            cardView = (CardView)itemView.findViewById(R.id.send_photo_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_photo_cardview_check);
            imageView = (ImageView)itemView.findViewById(R.id.send_photo_cardview_image);

            itemView.setOnClickListener(this);
        }

        public void setItem(SendPhoto item){

//            Log.d("picasso : ", item.getImagePath());

            Picasso.with(context)
                    .load(Uri.parse("file://"+item.getImagePath()))
                    .into(imageView);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("clickListener", "listen!");
            if(pos != RecyclerView.NO_POSITION){
                if(!checkBox.isChecked()) {
                    Log.d("check!", "yes");
                    checkBox.setChecked(true);
                    sendPhotos.get(pos).setCheck(true);
                    MainActivity.selectList.add(sendPhotos.get(pos).getImagePath());
                }
                else{
                    Log.d("check?", "No");
                    checkBox.setChecked(false);
                    sendPhotos.get(pos).setCheck(false);
                    MainActivity.selectList.remove(sendPhotos.get(pos));
                }
            }
        }
    }
}
