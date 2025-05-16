package com.example.lab13_1_autocomplatetextview;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] cities = {
            "Hà Nội", "Huế", "Hà Giang", "Hà Nam Ninh", "Hội An",
            "TP. Hồ Chí Minh", "Đà Nẵng", "Cần Thơ", "Hải Phòng", "Hưng Yên"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cities
        );
        autoCompleteTextView.setAdapter(adapter);

        // 👉 Thêm đoạn này vào để hiển thị Toast khi người dùng chọn
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = parent.getItemAtPosition(position).toString();
            Toast.makeText(MainActivity.this, "Bạn đã chọn: " + selected, Toast.LENGTH_SHORT).show();
        });
    }

}
