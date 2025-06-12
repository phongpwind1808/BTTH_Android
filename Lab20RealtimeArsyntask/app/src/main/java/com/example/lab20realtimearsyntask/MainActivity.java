package com.example.lab20realtimearsyntask;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Khai báo button
    Button btnStart;

    // Khai báo MyAsyncTask
    MyAsyncTask mytt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm view
        btnStart = findViewById(R.id.button);

        // Đặt listener cho nút Start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });
    }

    private void doStart() {
        // Tạo instance của MyAsyncTask và truyền context (this)
        mytt = new MyAsyncTask(this);

        // Kích hoạt tiến trình
        mytt.execute();
    }
}