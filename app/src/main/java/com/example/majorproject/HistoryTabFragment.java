package com.example.majorproject;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.DeniedByServerException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryTabFragment extends Fragment {
    private RecyclerView historyRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<HistoryDBArray> historyDBArrays;
    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;

    private SQLiteDatabase database;
    private HistoryDatabase helper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_frame_fragment, container, false);

        historyRecyclerview = (RecyclerView)view.findViewById(R.id.history_recyclerview);
        layoutManager = new LinearLayoutManager(this.getContext());
        historyRecyclerview.setLayoutManager(layoutManager);
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(historyDBArrays, getActivity());
        historyRecyclerview.setAdapter(historyRecyclerViewAdapter);

        openDatabase ("historyDB");
        ArrayList<HistoryDBArray> result = load_values();
        historyRecyclerViewAdapter.setItems(result);
        historyRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }

    private void openDatabase(String databaseName){
        try {
            database = SQLiteDatabase.openOrCreateDatabase(databaseName + ".db", null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        helper = new HistoryDatabase(this.getActivity(), databaseName, null, 3);

    }

    private ArrayList<HistoryDBArray> load_values(){
        ArrayList<HistoryDBArray> result = new ArrayList<>();
        database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(DBstruct.SQL_SELECT, null);

        while (cursor.moveToNext()){
            String date = cursor.getString(1);
            String deviceName = cursor.getString(2);
            int fileSize = cursor.getInt(3);
            int isSuccess= cursor.getInt(4);
            String sucOrFail = (isSuccess == 1) ? "Success" : "Fail";

            HistoryDBArray dbArray = new HistoryDBArray(date, deviceName, Integer.toString(fileSize), sucOrFail);
            result.add(dbArray);
        }
        return result;
    }
}
