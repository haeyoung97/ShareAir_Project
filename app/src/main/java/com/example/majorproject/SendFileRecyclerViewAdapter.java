package com.example.majorproject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SendFileRecyclerViewAdapter extends RecyclerView.Adapter<SendFileRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendFile> sendFiles;
    private Context context;
    private LinearLayout dynamicLinearLayout;
    private DynamicSendSelectLayout dynamicSendSelectLayout;
    private int selectCnt;

    public SendFileRecyclerViewAdapter(ArrayList<SendFile> sendFiles, Context context) {
        this.sendFiles = sendFiles;
        this.context = context;
    }

    public SendFileRecyclerViewAdapter(ArrayList<SendFile> sendFiles, Context context, LinearLayout dynamicLinearLayout) {
        this.sendFiles = sendFiles;
        this.context = context;
        this.dynamicLinearLayout = dynamicLinearLayout;
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
        private TextView selectCntTextView;

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
                    selectCnt++;
                    MainActivity.selectList.add(new FileNode(sendFiles.get(pos).getFilepath(), 3));
//                    MainActivity.selectList.add(sendFiles.get(pos).getFilepath());
//                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
                    dynamicSendSelectLayout = new DynamicSendSelectLayout(context.getApplicationContext());
                    if(selectCnt==1){
                        dynamicLinearLayout.addView(dynamicSendSelectLayout);
                    }
                    selectCntTextView = (TextView)dynamicLinearLayout.findViewById(R.id.dynamic_send_select_cnt);
                    selectCntTextView.setText(selectCnt + "개 선택");
                }
                else{
                    checkBox.setChecked(false);
                    sendFiles.get(pos).setCheck(false);
                    selectCnt--;
                    MainActivity.selectList.remove(sendFiles.get(pos));
                    selectCntTextView.setText(selectCnt + "개 선택");
//                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
                    if(selectCnt==0){
                        dynamicLinearLayout.removeAllViews();
                    }
                }
            }
        }
    }
}
