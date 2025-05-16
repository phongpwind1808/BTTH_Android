package com.example.lab12_2_calendarnotes;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtWork, edtHour, edtMinute;
    Button btnAdd;
    ListView lvWork;
    ArrayList<String> workList;
    ArrayAdapter<String> adapter;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        edtWork = findViewById(R.id.edtWork);
        edtHour = findViewById(R.id.edtHour);
        edtMinute = findViewById(R.id.edtMinute);
        btnAdd = findViewById(R.id.btnAdd);
        lvWork = findViewById(R.id.lvWork);
        tvDate = findViewById(R.id.tvDate);

        // Lấy ngày hiện tại
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        tvDate.setText(getString(R.string.today) + " " + currentDate);

        workList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workList);
        lvWork.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String work = edtWork.getText().toString().trim();
                String hour = edtHour.getText().toString().trim();
                String minute = edtMinute.getText().toString().trim();

                if (!work.isEmpty() && !hour.isEmpty() && !minute.isEmpty()) {
                    String item = "+ " + work + " - " + hour + ":" + minute;
                    workList.add(item);
                    adapter.notifyDataSetChanged();

                    // Reset
                    edtWork.setText("");
                    edtHour.setText("");
                    edtMinute.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
