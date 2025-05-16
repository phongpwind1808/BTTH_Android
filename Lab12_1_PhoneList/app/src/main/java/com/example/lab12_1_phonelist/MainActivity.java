package com.example.lab12_1_phonelist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView tvSelection;
    ListView listView;

    ArrayList<HashMap<String, String>> data;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelection = findViewById(R.id.tvSelection);
        listView = findViewById(R.id.listView);

        // Dữ liệu mẫu
        data = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("title", "Item " + i);
            item.put("subtitle", "Sub Item " + i);
            data.add(item);
        }

        // Adapter hiển thị 2 dòng (title & subtitle)
        adapter = new SimpleAdapter(
                this,
                data,
                R.layout.list_item,
                new String[]{"title", "subtitle"},
                new int[]{R.id.tvTitle, R.id.tvSubtitle}
        );

        listView.setAdapter(adapter);

        // Sự kiện chọn item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = data.get(position).get("title");
                tvSelection.setText(getString(R.string.selected_prefix) + selected);
            }
        });
    }
}
