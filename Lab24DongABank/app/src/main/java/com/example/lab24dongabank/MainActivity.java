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

        @Override
        protected ArrayList<Tygia> doInBackground(Void... params) {
            ArrayList<Tygia> ds = new ArrayList<Tygia>();
            try {
                URL url = new URL("https://api.exchangerate-api.com/v4/latest/USD");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                String json = builder.toString();
                Log.d("JSON_DONGA", "Chuỗi JSON nhận được: " + json);

                JSONObject jsonObject = new JSONObject(json);
                JSONObject ratesObject = jsonObject.getJSONObject("rates");

                // Bạn muốn hiển thị các loại tiền cụ thể → duyệt qua keys của rates
                Iterator<String> keys = ratesObject.keys();
                while (keys.hasNext()) {
                    String currencyCode = keys.next(); // Ví dụ: "VND", "EUR"
                    String rate = ratesObject.get(currencyCode).toString();

                    Tygia tyGia = new Tygia();
                    tyGia.setType(currencyCode);
                    tyGia.setMuatienmat(rate); // Tạm dùng làm "mua tiền mặt"
                    tyGia.setMuack(""); // Không có trong API này
                    tyGia.setBantienmat(""); // Không có trong API này
                    tyGia.setBanck(""); // Không có trong API này
                    tyGia.setImageurl(""); // Không có hình ảnh

                    ds.add(tyGia);
                }

                if (is != null) is.close();
                connection.disconnect();
            } catch (Exception ex) {
                Log.e("LoiTyGiaTask", ex.toString());
            }
            return ds;
        }


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
