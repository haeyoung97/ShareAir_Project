package com.example.majorproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

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
        Log.d("SendRecentRecycler : ", sendRecents.get(1).getImagePath());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_recent_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendRecentRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d("BindHolder : ", sendRecents.get(position).getImagePath());


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeFile(sendRecents.get(position).getImagePath());
        Bitmap resizeBitmap;

        if(bitmap.getWidth() > bitmap.getHeight()){
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        else{
            resizeBitmap = Bitmap.createScaledBitmap(bitmap, 130, 140, true);

        }
        holder.imageView.setImageBitmap(resizeBitmap);
        holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return sendRecents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_recent_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_recent_cardview_check);
            imageView = (ImageView)itemView.findViewById(R.id.send_recent_cardview_image);

            itemView.setOnClickListener(this);
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
                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                }
                else{
                    checkBox.setChecked(false);
                    sendRecents.get(pos).setCheck(false);
                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                }
                Log.d("image?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
                Log.d("selectList ", MainActivity.selectList.get(0).getName());
            }
        }
    }
}
