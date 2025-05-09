package com.example.lab3_project_cal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtResult;
    Button btnSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtResult = findViewById(R.id.edtResult);
        btnSum = findViewById(R.id.btnSum);

        // Xử lý sự kiện click cho nút btnSum
        btnSum.setOnClickListener(v -> {
            try {
                // Lấy giá trị từ EditText
                String strA = edtA.getText().toString();
                String strB = edtB.getText().toString();

                // Chuyển đổi sang số
                int a = Integer.parseInt(strA);
                int b = Integer.parseInt(strB);

                // Tính tổng
                int result = a + b;

                // Hiển thị kết quả
                edtResult.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu nhập không phải số
                edtResult.setText("Lỗi nhập liệu");
            }
        });
    }

}