package com.example.lab14_1_tabselector_calc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> history;

    public HistoryAdapter(Context context, ArrayList<String> history) {
        super(context, R.layout.history_item, history);
        this.context = context;
        this.history = history;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Sử dụng ViewHolder để tối ưu hiệu suất
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.history_item, parent, false);

            holder = new ViewHolder();
            holder.textViewCalculation = convertView.findViewById(R.id.textViewCalculation);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Hiển thị dữ liệu tại vị trí position
        String calculation = history.get(position);
        holder.textViewCalculation.setText(calculation);

        return convertView;
    }

    // Lớp ViewHolder để lưu trữ tham chiếu đến các thành phần giao diện
    private static class ViewHolder {
        TextView textViewCalculation;
    }
}