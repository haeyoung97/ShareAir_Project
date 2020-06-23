package com.example.majorproject;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class SendRecentRecyclerViewAdapter extends RecyclerView.Adapter<SendRecentRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendRecent> sendRecents;
    private Context context;
    private LinearLayout dynamicLinearLayout;
    private DynamicSendSelectLayout dynamicSendSelectLayout;

    public SendRecentRecyclerViewAdapter(ArrayList<SendRecent> sendRecents, Context context) {
        this.sendRecents = sendRecents;
        this.context = context;
    }

    public SendRecentRecyclerViewAdapter(ArrayList<SendRecent> sendRecents, Context context, LinearLayout dynamicLinearLayout) {
        this.sendRecents = sendRecents;
        this.context = context;
        this.dynamicLinearLayout = dynamicLinearLayout;
        MainActivity.selectCnt = 0;
        Log.e("dynamic recent", "view");
    }


    @NonNull
    @Override
    public SendRecentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.send_recent_cardview, parent, false);

//        SendRecentRecyclerViewAdapter.ViewHolder vh = new SendRecentRecyclerViewAdapter.ViewHolder(view);
        return new SendRecentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SendRecentRecyclerViewAdapter.ViewHolder holder, final int position) {
        final SendRecent item = sendRecents.get(position);
        holder.setItem(item);
        if(MainActivity.selectList.size() == 0){
            if(item.isSelected()){
                item.setSelected(!item.isSelected());
            }
            Log.e("recent : ", "0");
        }

//        int l = 0;

//        for (int i = 0; i < MainActivity.selectList.size(); i++){
//            if(item.getIndex() == MainActivity.selectList.get(i).getFileIdx()){
////                holder.views.setBackgroundColor(Color.GRAY);
//                Log.e("for ! ", "true?");
//                item.setSelected(true);
//                break;
//            }
//        }
        Log.e("h:",Integer.toString(position) +" " + Boolean.toString(item.isSelected()));
        holder.views.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);

        holder.views.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e("prev select color : ", Boolean.toString(item.isSelected()));
                item.setSelected(!item.isSelected());
                Log.e("click select color : ", Boolean.toString(item.isSelected()));
                holder.views.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);
                if(item.isSelected()){
                    item.setIndex(position);
//                    selectIdx++;
                    MainActivity.selectCnt++;
                    MainActivity.selectList.add(new FileNode(item.getFilePath(), item.getFileExtNum(), item.getIndex(), 1));
                    dynamicSendSelectLayout = new DynamicSendSelectLayout(context.getApplicationContext());
                    if (MainActivity.selectCnt == 1) {
                        dynamicLinearLayout.addView(dynamicSendSelectLayout);
                        SendTabFragment.viewPager.setPagingEnabled(false);
                        for(int i = 0; i < SendTabFragment.tabStrip.getChildCount(); i++) {
                            SendTabFragment.tabStrip.getChildAt(i).setClickable(false);
                        }
                    }

                    holder.selectCntTextView = (TextView) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_cnt);
                    holder.selectCntTextView.setText(MainActivity.selectCnt + "개 선택");

                    holder.selectSendButton = (Button) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_btn);
                    holder.selectSendButton.setOnClickListener(MainActivity.btnEventListener);
//                    holder.selectSendButton.setOnClickListener(new ButtonEventListener(MainActivity.static_mainActivity, context, sendRecents));

                }
                else if(MainActivity.selectList.size() != 0){
                    int k = 0;
                    while(true){
                        if((MainActivity.selectList.get(k).getFileTab() == 1) && (MainActivity.selectList.get(k).getFileIdx() == item.getIndex())){
                            break;
                        }
                        Log.e("remove idx? ", Integer.toString(MainActivity.selectList.get(k).getFileIdx()));
                        k++;
                    }
                    MainActivity.selectCnt--;
                    MainActivity.selectList.remove(k);
                    holder.selectCntTextView.setText(MainActivity.selectCnt + "개 선택");
                    if(MainActivity.selectCnt==0){
                        dynamicLinearLayout.removeAllViews();
                        SendTabFragment.viewPager.setPagingEnabled(true);
                        for(int i = 0; i < SendTabFragment.tabStrip.getChildCount(); i++) {
                            SendTabFragment.tabStrip.getChildAt(i).setClickable(true);
                        }
                    }

                }
//                lastSelectedPosition = holder.getAdapterPosition();
            }
        });
//        if(holder.isItemSelected(position)){
//            holder.itemView.setBackgroundColor(Color.GRAY);
//        }
//        else{
//            holder.itemView.setBackgroundColor(Color.WHITE);
//        }
//        holder.itemView.setSelected(holder.isItemSelected(position));
//        holder.checkBox.setOnCheckedChangeListener(null);

    }

    @Override
    public long getItemId(int position) {
        return sendRecents.get(position).getIndex();
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

//        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView filename;
        private TextView date;
        private TextView selectCntTextView;
        private Button selectSendButton;
        private View views;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_recent_cardview);
//            checkBox = (CheckBox)itemView.findViewById(R.id.send_recent_checkbox);
            imageView = (ImageView)itemView.findViewById(R.id.send_recent_image);
            filename = (TextView)itemView.findViewById(R.id.send_recent_filename);
            date = (TextView)itemView.findViewById(R.id.send_recent_date);
            views = itemView;
//            this.setIsRecyclable(false);
//            itemView.setOnClickListener(this);
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
//            int pos = getAdapterPosition();
//
//            if(lastSelectedPosition > 0){
//
//            }
//            sendRecents.get(pos).setSelected(!sendRecents.get(pos).isSelected());
//            v.setBackgroundColor(sendRecents.get(pos).isSelected() ?
//                    Color.GRAY : Color.WHITE);
//            Log.d("clickListener", "listen!");

//            if(pos != RecyclerView.NO_POSITION){
//                Log.d("aa", "god");
//                if(!checkBox.isChecked()) {
//                    sendRecents.get(pos).setIndex(selectIdx);
//                    selectIdx++;
//                    if(sendRecents.get(pos).getIndex() == selectCnt) {
//                        checkBox.setChecked(true);
//                        sendRecents.get(pos).setCheck(true);
//
//                        selectCnt++;
////                        MainActivity.selectList.add(new FileNode(sendRecents.get(pos).getFilePath(), 0));
//                        dynamicSendSelectLayout = new DynamicSendSelectLayout(context.getApplicationContext());
//                        if (selectCnt == 1) {
//                            dynamicLinearLayout.addView(dynamicSendSelectLayout);
//                        }
//
//                        selectCntTextView = (TextView) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_cnt);
//                        selectCntTextView.setText(selectCnt + "개 선택");
//
//                        selectSendButton = (Button) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_btn);
//                        selectSendButton.setOnClickListener(MainActivity.btnEventListener);
                    }
//
////                    Log.e("", "onClick: " + MainActivity.selectList.get(0).getFilePath());
////                    MainActivity.selectList.add(sendRecents.get(pos).getFilePath());
////                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
//                }
//                else{
//                    checkBox.setChecked(false);
//                    sendRecents.get(pos).setCheck(false);
////                    sendRecents.get(pos).setIndex(selectCnt);
//                    selectCnt--;
//                    MainActivity.selectList.remove(sendRecents.get(pos));
//                    selectCntTextView.setText(selectCnt + "개 선택");
//                    if(selectCnt==0){
//                        dynamicLinearLayout.removeAllViews();
//                    }
////                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
//                }

//                Log.d("recentFIle?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
//                Log.d("selectList ", MainActivity.selectList.get(0).getName());
//            }

    }
}
