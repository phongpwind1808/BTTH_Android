package com.example.lab14_1_tabselector_calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private EditText editTextA, editTextB;
    private Button buttonCalc;
    private ListView listViewHistory;
    private ArrayList<String> calculationHistory;
    private HistoryAdapter historyAdapter;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo TabHost và thiết lập sự kiện OnTabChangeListener
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.setOnTabChangedListener(this);

        // Tạo Tab 1 - Pháp công
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator(getString(R.string.tab1_name));
        tabHost.addTab(tab1);

        // Tạo Tab 2 - Lịch sử
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator(getString(R.string.tab2_name));
        tabHost.addTab(tab2);

        // Ánh xạ các thành phần giao diện
        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        buttonCalc = findViewById(R.id.buttonCalc);
        listViewHistory = findViewById(R.id.listViewHistory);

        // Khởi tạo lịch sử tính toán và thiết lập Custom ListView Adapter
        calculationHistory = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, calculationHistory);
        listViewHistory.setAdapter(historyAdapter);

        // Thiết lập sự kiện khi nhấn nút tính
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSum();
            }
        });
    }

    // Phương thức xử lý sự kiện khi chuyển tab
    @Override
    public void onTabChanged(String tabId) {
        // Có thể xử lý các sự kiện khi chuyển tab ở đây
        if (tabId.equals("tab2")) {
            // Khi chuyển sang tab lịch sử, đảm bảo ListView được cập nhật
            if (historyAdapter != null) {
                historyAdapter.notifyDataSetChanged();
            }
        }
    }

    private void calculateSum() {
        String strA = editTextA.getText().toString().trim();
        String strB = editTextB.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (strA.isEmpty() || strB.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ hai số", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Chuyển đổi chuỗi sang số
            int a = Integer.parseInt(strA);
            int b = Integer.parseInt(strB);

            // Tính tổng
            int sum = a + b;

            // Tạo chuỗi kết quả và thêm vào lịch sử
            String result = a + " + " + b + " = " + sum;
            calculationHistory.add(result);

            // Cập nhật giao diện
            historyAdapter.notifyDataSetChanged();

            // Xóa dữ liệu đã nhập
            editTextA.setText("");
            editTextB.setText("");

            // Chuyển đến tab2 để xem lịch sử
            tabHost.setCurrentTab(1);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dữ liệu nhập không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}