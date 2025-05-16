package com.example.lab13_2_gridviewphone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtResult = findViewById(R.id.txtResult);
        String phoneName = getIntent().getStringExtra("phone_name");
        txtResult.setText("Bạn đã chọn: " + phoneName);
    }
}
