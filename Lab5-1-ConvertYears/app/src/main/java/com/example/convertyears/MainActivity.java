package com.example.convertyears;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TableLayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        EditText editNam = findViewById(R.id.editnamduonglich);
        TextView txtKetQua = findViewById(R.id.textView5);
        Button btnDoi = findViewById(R.id.button1);

        // Xử lý nút chuyển đổi
        btnDoi.setOnClickListener(v -> {
            String namStr = editNam.getText().toString().trim();
            if (!namStr.isEmpty()) {
                int namDuong = Integer.parseInt(namStr);

                String[] canList = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
                String[] chiList = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

                String can = canList[namDuong % 10];
                String chi = chiList[namDuong % 12];

                String namAmLich = can + " " + chi;
                txtKetQua.setText(namAmLich);
            } else {
                txtKetQua.setText("Vui lòng nhập năm!");
            }
        });
    }
}
