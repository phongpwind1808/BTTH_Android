package com.example.lab7_2_calcintent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    Button btnBack;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setTitle("ResultActivity");
        btnBack = findViewById(R.id.btnBack);
        tvResult = findViewById(R.id.tvResult);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("mybackage");
        int a = bundle.getInt("SoA");
        int b = bundle.getInt("SoB");
        String Result = "";
        if(a==0 && b==0)
            Result = "Vô số nghiệm";
        else if(a==0 && b!=0)
            Result = "Vô nghiệm";
        else {
            DecimalFormat dcf = new DecimalFormat("0.00");
            Result= dcf.format(-b*1.0/a);

        }
        tvResult.setText(Result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}