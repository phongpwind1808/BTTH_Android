package com.example.lab41_changetemperature;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Khai báo các phần tử giao diện
    EditText edtF, edtC;
    Button btnToC, btnToF,btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Tham chiếu thuộc tính tới các phần tử giao diện
        edtF = findViewById(R.id.edtF);
        edtC = findViewById(R.id.edtC);
        btnToC = findViewById(R.id.btnToC);
        btnToF = findViewById(R.id.btnToF);
        btnClear = findViewById(R.id.btnClear);
        btnToC.setOnClickListener(v -> {
            String strF = edtF.getText().toString();
            if (!strF.isEmpty()) {
                double f = Double.parseDouble(strF);
                double c = (f - 32) * 5 / 9;
                edtC.setText(String.valueOf(c));
            }
        });
        btnToF.setOnClickListener(v -> {
            String strC = edtC.getText().toString();
            if (!strC.isEmpty()) {
                double c = Double.parseDouble(strC);
                double f = (c * 9 / 5) + 32;
                edtF.setText(String.valueOf(f));
            }
        });
        btnClear.setOnClickListener(v -> {
            edtF.setText("");
            edtC.setText("");
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}