package com.example.lab21readfromjson;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    Button btnParse;
    ListView lv;
    ArrayList<String> myList;
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        btnParse = findViewById(R.id.button);
        lv = findViewById(R.id.lv);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);

        // Set click listener for the button
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJson();
            }
        });
    }
    private void parseJson() {
        String json;
        try {
            // Mở file JSON từ thư mục assets
            InputStream is = getAssets().open("computer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            // Parse JSON gốc thành JSONObject
            JSONObject root = new JSONObject(json);

            // Lấy mảng SanPhams
            JSONArray sanPhamsArray = root.getJSONArray("SanPhams");

            // Duyệt qua từng sản phẩm trong mảng
            for (int i = 0; i < sanPhamsArray.length(); i++) {
                JSONObject sp = sanPhamsArray.getJSONObject(i);

                String masp = sp.getString("MaSP");
                String tensp = sp.getString("TenSP");
                int soluong = sp.getInt("SoLuong");
                int dongia = sp.getInt("DonGia");
                int thanhtien = sp.getInt("ThanhTien");
                String hinh = sp.getString("Hinh");

                // Thêm vào danh sách hiển thị
                myList.add(masp + " - " + tensp + "\n" +
                        "Số lượng: " + soluong + ", Đơn giá: " + dongia + ", Thành tiền: " + thanhtien + "\n" +
                        "Hình ảnh: " + hinh);
            }

            // Cập nhật giao diện
            myAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Không thể đọc file JSON", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi cú pháp JSON", Toast.LENGTH_LONG).show();
        }
    }
}