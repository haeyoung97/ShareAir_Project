package com.example.majorproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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

public class SendPhotoRecyclerViewAdapter extends RecyclerView.Adapter<SendPhotoRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SendPhoto> sendPhotos;
    private Context context;
    private LinearLayout dynamicLinearLayout;
    private DynamicSendSelectLayout dynamicSendSelectLayout;
    private int selectCnt;

    public SendPhotoRecyclerViewAdapter(ArrayList<SendPhoto> sendPhotos, Context context) {
        this.sendPhotos = sendPhotos;
        this.context = context;
    }

    public SendPhotoRecyclerViewAdapter(ArrayList<SendPhoto> sendPhotos, Context context, LinearLayout dynamicLinearLayout) {
        this.sendPhotos = sendPhotos;
        this.context = context;
        this.dynamicLinearLayout = dynamicLinearLayout;
    }

    @NonNull
    @Override
    public SendPhotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.send_photo_cardview, parent, false);
        return new SendPhotoRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SendPhotoRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d("PhotoBindHolder : ", sendPhotos.get(position).getImagePath());


        final SendPhoto item = sendPhotos.get(position);
        holder.setItem(item);
        final Drawable highlight = context.getDrawable(R.drawable.highlight);
        final Drawable noHighlight = context.getDrawable(R.drawable.nohighlight);
        if(MainActivity.selectList.size() == 0){
            item.setSelected(false);
        }
//        holder.imageView.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);
        holder.imageView.setColorFilter(item.isSelected() ?
                context.getResources().getColor(R.color.selectItem) :
                context.getResources().getColor(R.color.unselectItem));
//        holder.views.setBackground(item.isSelected() ? highlight : noHighlight);
        holder.views.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                item.setSelected(!item.isSelected());
                holder.imageView.setColorFilter(item.isSelected() ?
                        context.getResources().getColor(R.color.selectItem) :
                        context.getResources().getColor(R.color.unselectItem));
////                holder.views.setBackgroundColor(item.isSelected() ? Color.GRAY : Color.WHITE);
//                holder.views.setBackground(item.isSelected() ? highlight : noHighlight);
                if (item.isSelected()) {
                    item.setIndex(position);
//                    selectIdx++;
                    selectCnt++;
                    MainActivity.selectList.add(new FileNode(item.getImagePath(), 5, item.getIndex(), 2));
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
                        if ((MainActivity.selectList.get(k).getFileTab() == 2) && (MainActivity.selectList.get(k).getFileIdx() == item.getIndex())) {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox;
        private ImageView imageView;
        private CardView cardView;
        private TextView selectCntTextView;
        private Button selectSendButton;
        private View views;

        private BitmapFactory.Options options = new BitmapFactory.Options();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            options.inSampleSize = 12;

            cardView = (CardView)itemView.findViewById(R.id.send_photo_cardview);
            imageView = (ImageView)itemView.findViewById(R.id.send_photo_cardview_image);
            views = itemView;
        }

        public void setItem(SendPhoto item){

            Picasso.with(context)
                    .load(Uri.parse("file://"+item.getImagePath()))
                    .into(imageView);

        }
    }
}
