package com.example.majorproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SendTabRecentFragment extends Fragment {
    private RecyclerView recentRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SendRecent> sendRecentArrayList;
    private SendRecentRecyclerViewAdapter recentRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_viewpager_recyclerview, container, false);

        sendRecentArrayList = new ArrayList<SendRecent>();
        recentRecyclerview = (RecyclerView) view.findViewById(R.id.send_recyclerview);
        layoutManager = new LinearLayoutManager(this.getContext());
        recentRecyclerview.setLayoutManager(layoutManager);
        recentRecyclerViewAdapter = new SendRecentRecyclerViewAdapter(sendRecentArrayList, getActivity());
        recentRecyclerview.setAdapter(recentRecyclerViewAdapter);
        return view;
    }
}
