package com.example.majorproject;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Rect;
import android.nfc.cardemulation.CardEmulation;
import android.provider.MediaStore;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HistoryDBArray> historyDBArrays;
    private Context context;
    private int selectCnt;
    private DynamicSelectLayout dynamicSelectLayout;
    private LinearLayout dynamicLinearLayout;
    private int count = 0;

    public HistoryRecyclerViewAdapter(ArrayList<HistoryDBArray> historyDBArrays, Context context) {
        this.historyDBArrays = historyDBArrays;
        this.context = context;
    }

    public HistoryRecyclerViewAdapter(ArrayList<HistoryDBArray> historyDBArrays, Context context, LinearLayout dynamicLinearLayout) {
        this.historyDBArrays = historyDBArrays;
        this.context = context;
        this.dynamicLinearLayout = dynamicLinearLayout;
        Log.e("history recyclerview : ", Integer.toString(count++));
    }

    @NonNull
    @Override
    public HistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.history_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryDBArray item = historyDBArrays.get(position);
        holder.setItem(item);
    }


    @Override
    public int getItemCount() {
        return historyDBArrays.size();
    }

    public void addItem(HistoryDBArray item){
        historyDBArrays.add(item);
    }

    public void setItems(ArrayList<HistoryDBArray> item){
        this.historyDBArrays = item;
    }

    public HistoryDBArray getItem(int position){
        return historyDBArrays.get(position);
    }

    public void setItem(int position, HistoryDBArray item){
        historyDBArrays.set(position, item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckBox checkBox;
        private ImageView imageView;
        private TextView deviceName;
        private TextView fileSize;
        private TextView sucOrFail;
        private TextView date;
        private CardView cardView;
        private TextView historySelectCntView;
        private Button historyDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.history_cardview);
            imageView = (ImageView)itemView.findViewById(R.id.history_image);
            checkBox = (CheckBox)itemView.findViewById(R.id.history_checkbox);
            deviceName = (TextView)itemView.findViewById(R.id.history_device);
            fileSize = (TextView)itemView.findViewById(R.id.history_filesize);
            sucOrFail = (TextView)itemView.findViewById(R.id.history_SucOrFail);
            date = (TextView)itemView.findViewById(R.id.history_date);


            itemView.setOnClickListener(this);
        }

        public void setItem(HistoryDBArray item){
            deviceName.setText(item.getDeviceName());
            fileSize.setText(item.getFileSize() + "KB");
            sucOrFail.setText(item.getSucOrFail());
            date.setText(item.getDate());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                if(!checkBox.isChecked()){
                    checkBox.setChecked(true);
                    historyDBArrays.get(pos).setCheck(true);
                    selectCnt++;
                    if(selectCnt==1){
                        dynamicSelectLayout = new DynamicSelectLayout(context.getApplicationContext());
                        dynamicLinearLayout.addView(dynamicSelectLayout);
                    }
                    historySelectCntView = (TextView) dynamicLinearLayout.findViewById(R.id.dynamic_select_cnt);
                    historyDeleteBtn = (Button) dynamicLinearLayout.findViewById(R.id.dynamic_delete_btn);
                    historySelectCntView.setText(selectCnt + "개 선택");
                }
                else{
                    checkBox.setChecked(false);
                    historyDBArrays.get(pos).setCheck(false);
                    selectCnt--;
                    historySelectCntView.setText(selectCnt + "개 선택");
                    if(selectCnt==0){
                       dynamicLinearLayout.removeAllViews();
                    }
                }
            }
        }
    }
}