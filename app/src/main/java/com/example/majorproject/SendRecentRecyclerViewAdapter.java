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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        Log.d("SendRecentRecycler : ", sendRecents.get(1).getImagePath());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_photo_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendRecentRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d("BindHolder : ", sendRecents.get(position).getImagePath());
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
//        String datetime = dateformat.format(c.getTime());
//        Log.d("prevTime : ", datetime);

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
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime1 = dateformat1.format(c1.getTime());
        Log.d("TimeNow : ", datetime1);
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
        private TextView filename;
        private TextView year;
        private TextView month;
        private TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_recent_cardview);
            checkBox = (CheckBox)itemView.findViewById(R.id.send_recent_checkbox);
            imageView = (ImageView)itemView.findViewById(R.id.send_recent_image);
            filename = (TextView)itemView.findViewById(R.id.send_recent_filename);
            year = (TextView)itemView.findViewById(R.id.send_recent_date_year);
            month = (TextView)itemView.findViewById(R.id.send_recent_date_month);
            day = (TextView)itemView.findViewById(R.id.send_recent_date_day);


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
