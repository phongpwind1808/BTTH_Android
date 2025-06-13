package com.example.lab24dongabank;

import androidx.appcompat.app.AppCompatActivity; // Sử dụng AppCompatActivity cho các tính năng hiện đại
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lvTygia;
    TextView txtdate;
    ArrayList<Tygia> dstygia;
    MyArrayAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTygia = (ListView) findViewById(R.id.lv1); // ID trong activity_main.xml là lv1
        txtdate = (TextView) findViewById(R.id.txtdate);

        getdate();

        dstygia = new ArrayList<Tygia>();
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.item, dstygia);
        lvTygia.setAdapter(myadapter);

        // Tạo và thực thi AsyncTask
        TyGiaTask task = new TyGiaTask();
        task.execute();
    }

    // Phương thức lấy ngày giờ hệ thống
    public void getdate() {
        Date currentDate = Calendar.getInstance().getTime();
        // Format theo định dạng dd/MM/yyyy
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        // Hiển thị lên TextView
        txtdate.setText("Hôm Nay: " + simpleDate.format(currentDate));
    }

    // Lớp AsyncTask để thực hiện việc lấy dữ liệu từ API trong nền
    class TyGiaTask extends AsyncTask<Void, Void, ArrayList<Tygia>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (myadapter != null) { // Kiểm tra myadapter trước khi clear
                myadapter.clear();
            }
        }

//        @Override



        @Override
        protected void onPostExecute(ArrayList<Tygia> result) {
            super.onPostExecute(result);
            if (myadapter != null && result != null) { // Kiểm tra null cho result
                myadapter.clear();
                myadapter.addAll(result);
                // myadapter.notifyDataSetChanged(); // addAll đã gọi notifyDataSetChanged()
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
