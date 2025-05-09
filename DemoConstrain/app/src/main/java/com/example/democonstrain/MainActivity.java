package com.example.democonstrain; // Đổi thành package của bạn

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Vòng đời activity
    // intent và điều hướng
    // explicit intent - dùng để chuyển acticity
    // implicit intent - android sẽ tìm ứng dụng phù hợp để xử lý yêu cầu.
    // làm sao để ứng dụng hiểu được ...
    // Tất cả Activity cần đc khai báo trong AndroidManifest.xml
    // hệ điều hành sẽ đọc các activity và ý nghĩa của nó ở AndroidManifest.xml
    // kiem tra ket qua va ma code (co phai ban tin toi gui di hay kô hay cua ban tin quang ba )

    // 1. Khai báo các biến giao diện
    EditText edtA, edtB, edtKQ;
    Button btnCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // thiết lập layout hiển thị
        // R lớp đặc biệt trong java: R -viết tắt của Resources liên kết với các nguồn tài nguyên


        // 2. tham chiếu thuộc tính đến các phần tử giao diện
        // Ánh xạ ID các thành phần giao diện
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKQ = findViewById(R.id.edtKQ);
        btnCong = findViewById(R.id.btnCong);
        // 3. bắt sự kiện: có 3 cách
        // Xử lý khi bấm nút "Cộng"
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            // nội dung sự kiện cần xử lý.
            public void onClick(View v) {
                try {
                    // Lấy dữ liệu từ EditText, ép kiểu về số nguyên
                    int a = Integer.parseInt(edtA.getText().toString().trim());
                    int b = Integer.parseInt(edtB.getText().toString().trim());

                    int c = a + b;


                // Tạo Intent để chuyển sang Activity_main_result
                    Intent intent = new Intent(MainActivity.this, MainResultactivity.class);
                    // Gửi biến c sang Activity khác
                    intent.putExtra("result", a);

// Bắt đầu Activity
                    startActivity(intent);


                    // Hiển thị kết quả
                    // edtKQ.setText(String.valueOf(c));
                    // 3Toast.makeText(MainActivity.this, "Tính toán thành công!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // edtKQ.setText("Lỗi nhập!");
                }
            }
        });
    }
}
