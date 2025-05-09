package com.example.lab4_2_bodymassindex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnDiagnosis;
    EditText edtName,
            edtHeight,
            edtWeight,
            edtBMI,
            edtDiagnosis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnDiagnosis = findViewById(R.id.btnDiagnosis);
        edtName = findViewById(R.id.edtName);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        edtBMI = findViewById(R.id.edtBMI);
        edtDiagnosis = findViewById(R.id.edtDiagnosis);
        btnDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double H = Double.parseDouble(edtHeight.getText().toString());
                double W = Double.parseDouble(edtWeight.getText().toString());
                double BMI = W / Math.pow(H / 100, 2);  // Chuyển cm sang mét luôn tại đây

                String chandoan = "";

                if (BMI < 18) {
                    chandoan = "Bạn gầy";
                } else if (BMI <= 24.9) {
                    chandoan = "Bạn bình thường";
                } else if (BMI <= 29.9) {
                    chandoan = "Bạn béo phì độ 1";
                } else if (BMI <= 34.9) {
                    chandoan = "Bạn béo phì độ 2";
                } else {
                    chandoan = "Bạn béo phì độ 3";
                }

                DecimalFormat dcf = new DecimalFormat("#.0");
                edtBMI.setText(dcf.format(BMI));
                edtDiagnosis.setText(chandoan);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}