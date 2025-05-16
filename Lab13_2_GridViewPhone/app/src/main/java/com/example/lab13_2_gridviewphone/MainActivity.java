package com.example.lab13_2_gridviewphone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    String[] phones = {
            "Ipad", "Iphone", "New Ipad", "SamSung", "Nokia",
            "Sony Ericson", "LG", "Q-Mobile", "HTC",
            "Blackberry", "G Phone", "FPT - Phone", "HK Phone"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.grid_item,
                R.id.txtItem,
                phones
        );
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedPhone = phones[position];
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("phone_name", selectedPhone);
            startActivity(intent);
        });
    }
}
