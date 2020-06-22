package com.example.majorproject;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

public class SendVideoRecyclerViewAdapter extends RecyclerView.Adapter<SendVideoRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendVideo> sendVideos;
    private Context context;
    private LinearLayout dynamicLinearLayout;
    private DynamicSendSelectLayout dynamicSendSelectLayout;
    private int selectCnt;

    public SendVideoRecyclerViewAdapter(ArrayList<SendVideo> sendVideos, Context context) {
        this.sendVideos = sendVideos;
        this.context = context;
    }

    public SendVideoRecyclerViewAdapter(ArrayList<SendVideo> sendVideos, Context context, LinearLayout dynamicLinearLayout) {
        this.sendVideos = sendVideos;
        this.context = context;
        this.dynamicLinearLayout = dynamicLinearLayout;
    }

    @NonNull
    @Override
    public SendVideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
      LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.send_video_cardview, parent, false);
        return new SendVideoRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SendVideoRecyclerViewAdapter.ViewHolder holder, final int position) {
        final SendVideo item = sendVideos.get(position);
        holder.setItem(item);

        if(MainActivity.selectList.size() == 0){
            item.setSelected(false);
        }
        holder.views.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);

        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(!item.isSelected());
                holder.views.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);
                if (item.isSelected()) {
                    item.setIndex(position);
//                    selectIdx++;
                    selectCnt++;
                    MainActivity.selectList.add(new FileNode(item.getFilepath(), 7, item.getIndex(), 3));
                    dynamicSendSelectLayout = new DynamicSendSelectLayout(context.getApplicationContext());
                    if (selectCnt == 1) {
                        dynamicLinearLayout.addView(dynamicSendSelectLayout);
                        SendTabFragment.viewPager.setPagingEnabled(false);
                        for(int i = 0; i < SendTabFragment.tabStrip.getChildCount(); i++) {
                            SendTabFragment.tabStrip.getChildAt(i).setClickable(false);
                        }
                    }

                    holder.selectCntTextView = (TextView) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_cnt);
                    holder.selectCntTextView.setText(selectCnt + "개 선택");

                    holder.selectSendButton = (Button) dynamicLinearLayout.findViewById(R.id.dynamic_send_select_btn);
                    holder.selectSendButton.setOnClickListener(MainActivity.btnEventListener);

                } else {
                    int k = 0;
                    while (true) {
                        if ((MainActivity.selectList.get(k).getFileTab() == 3) && (MainActivity.selectList.get(k).getFileIdx() == item.getIndex())) {
                            break;
                        }
                        Log.e("remove idx? ", Integer.toString(MainActivity.selectList.get(k).getFileIdx()));
                        k++;
                    }
                    selectCnt--;
                    MainActivity.selectList.remove(k);
                    holder.selectCntTextView.setText(selectCnt + "개 선택");
                    if (selectCnt == 0) {
                        dynamicLinearLayout.removeAllViews();
                        SendTabFragment.viewPager.setPagingEnabled(true);
                        for(int i = 0; i < SendTabFragment.tabStrip.getChildCount(); i++) {
                            SendTabFragment.tabStrip.getChildAt(i).setClickable(true);
                        }
                    }

                }
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView filename;
        private TextView date;
        private TextView timeLength;
        private TextView selectCntTextView;
        private Button selectSendButton;
        private View views;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.send_video_cardview);
            imageView = (ImageView)itemView.findViewById(R.id.send_video_image);
            filename = (TextView)itemView.findViewById(R.id.send_video_filename);
            timeLength = (TextView)itemView.findViewById(R.id.send_video_length);
            date = (TextView)itemView.findViewById(R.id.send_video_date);
            views = itemView;
        }
        public void setItem(SendVideo item){
                Picasso.with(context)
                        .load(Uri.parse("file://" + item.getFilepath()))
                        .into(imageView);

            filename.setText(item.getFilename());
            date.setText(item.getDate());
            timeLength.setText(item.getTimeLength());

        }

//        @Override
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//            if(pos != RecyclerView.NO_POSITION){
//                if(!checkBox.isChecked()) {
//                    checkBox.setChecked(true);
//                    sendVideos.get(pos).setCheck(true);
//                    selectCnt++;
//                    MainActivity.selectList.add(new FileNode(sendVideos.get(pos).getFilepath(), 2));
//
//                    dynamicSendSelectLayout = new DynamicSendSelectLayout(context.getApplicationContext());
//                    if(selectCnt==1){
//                        dynamicLinearLayout.addView(dynamicSendSelectLayout);
//                    }
//
//                    selectCntTextView = (TextView)dynamicLinearLayout.findViewById(R.id.dynamic_send_select_cnt);
//                    selectCntTextView.setText(selectCnt + "개 선택");
//                    selectSendButton = (Button)dynamicLinearLayout.findViewById(R.id.dynamic_send_select_btn);
//                    selectSendButton.setOnClickListener(MainActivity.btnEventListener);
////                    MainActivity.selectList.add(sendVideos.get(pos).getFilepath());
////                    MainActivity.selectList.add(MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getFile());
//                }
//                else{
//                    checkBox.setChecked(false);
//                    sendVideos.get(pos).setCheck(false);
//                    selectCnt--;
//                    MainActivity.selectList.remove(sendVideos.get(pos).getFilepath());
//                    selectCntTextView.setText(selectCnt + "개 선택");
//                    if(selectCnt==0){
//                        dynamicLinearLayout.removeAllViews();
//                    }
////                    MainActivity.selectList.remove(MainActivity.imageList.get(sendRecents.get(pos).getIndex()));
//                }
////                Log.d("recentFIle?List", MainActivity.imageList.get(sendRecents.get(pos).getIndex()).getName());
////                Log.d("selectList ", MainActivity.selectList.get(0).getName());
//            }
//        }
    }
}
