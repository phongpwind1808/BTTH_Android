package com.example.lab18_searchkaraoke;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends TabActivity { // Sử dụng TabActivity

    public static SQLiteDatabase database = null;
    // Đảm bảo tên file trong assets là 'arirang.sqlite' và dùng tên này ở đây
    public static String DATABASE_NAME = "arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";

    TabHost tabHost;

    EditText edtTimKiem;
    ListView lvBaiHatTimKiem;
    ArrayList<BaiHat> dsBaiHatTimKiem;
    AdapterBaiHat adapterBaiHatTimKiem;

    ListView lvToanBoBaiHat;
    ArrayList<BaiHat> dsToanBoBaiHat;
    AdapterBaiHat adapterToanBoBaiHat;

    ListView lvBaiHatYeuThich;
    ArrayList<BaiHat> dsBaiHatYeuThich;
    AdapterBaiHat adapterBaiHatYeuThich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Sao chép CSDL vào hệ thống (nếu chưa có)
        // Gọi phương thức copyDatabase() để đảm bảo CSDL có sẵn
        copyDatabase(this);

        // 2. Mở database sau khi đã đảm bảo nó được sao chép
        // Gán vào biến static 'database' để các phương thức khác có thể truy cập
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // 3. Setup TabHost và các controls, load dữ liệu, thêm sự kiện
        // Đảm bảo các dòng này được gọi sau khi database đã được mở
        setupTabHost();
        addControls();
        loadData();
        addEvents();

        // Đoạn code đọc thử CSDL này có thể giữ lại để debug nhưng không cần thiết
        // trong hoạt động bình thường, và bạn không nên đóng 'database' ở đây
        // vì nó là biến static được sử dụng xuyên suốt Activity.
        // Nếu bạn muốn test, hãy dùng biến cục bộ như 'db' đã làm trước đó.
        // Tuy nhiên, việc mở lại CSDL ở đây là thừa nếu bạn đã gán vào biến 'database'.
        /*
        Cursor cursor = database.rawQuery("SELECT * FROM ArirangSongBook ORDER BY MABH ASC", null);
        if (cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(cursor.getColumnIndexOrThrow("MABH"));
                String ten = cursor.getString(cursor.getColumnIndexOrThrow("TENBH"));
                Log.d("SONG", ma + " - " + ten);  // In log
            } while (cursor.moveToNext());
        } else {
            Log.d("SONG", "No data found.");
        }
        cursor.close();
        // Không đóng database ở đây nếu nó là biến static dùng cho cả Activity
        // db.close(); // Dòng này sẽ gây lỗi nếu 'db' là 'database'
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đóng database khi Activity bị hủy để giải phóng tài nguyên
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("DB_CLOSE", "Database closed in onDestroy.");
        }
    }

    private void setupTabHost() {
        tabHost = getTabHost(); // Lấy TabHost từ TabActivity

        TabHost.TabSpec tabTimKiem = tabHost.newTabSpec("t1");
        // Kiểm tra xem các drawable có tồn tại không
        tabTimKiem.setIndicator("Tìm kiếm", getResources().getDrawable(R.drawable.ic_tab_search));
        tabTimKiem.setContent(R.id.tab_tim_kiem);
        tabHost.addTab(tabTimKiem);

        TabHost.TabSpec tabDanhSach = tabHost.newTabSpec("t2");
        tabDanhSach.setIndicator("Danh sách", getResources().getDrawable(R.drawable.ic_tab_list));
        tabDanhSach.setContent(R.id.tab_danh_sach);
        tabHost.addTab(tabDanhSach);

        TabHost.TabSpec tabYeuThich = tabHost.newTabSpec("t3");
        tabYeuThich.setIndicator("Yêu thích", getResources().getDrawable(R.drawable.ic_tab_favorite));
        tabYeuThich.setContent(R.id.tab_yeu_thich);
        tabHost.addTab(tabYeuThich);

        // Đặt tab mặc định là tab Tìm kiếm
        tabHost.setCurrentTabByTag("t1");
    }

    private void addControls() {
        edtTimKiem = findViewById(R.id.edtTimKiem);
        lvBaiHatTimKiem = findViewById(R.id.lvBaiHatTimKiem);
        dsBaiHatTimKiem = new ArrayList<>();
        adapterBaiHatTimKiem = new AdapterBaiHat(MainActivity.this, R.layout.item_bai_hat, dsBaiHatTimKiem);
        lvBaiHatTimKiem.setAdapter(adapterBaiHatTimKiem);

        lvToanBoBaiHat = findViewById(R.id.lvToanBoBaiHat);
        dsToanBoBaiHat = new ArrayList<>();
        adapterToanBoBaiHat = new AdapterBaiHat(MainActivity.this, R.layout.item_bai_hat, dsToanBoBaiHat);
        lvToanBoBaiHat.setAdapter(adapterToanBoBaiHat);

        lvBaiHatYeuThich = findViewById(R.id.lvBaiHatYeuThich);
        dsBaiHatYeuThich = new ArrayList<>();
        adapterBaiHatYeuThich = new AdapterBaiHat(MainActivity.this, R.layout.item_bai_hat, dsBaiHatYeuThich);
        lvBaiHatYeuThich.setAdapter(adapterBaiHatYeuThich);
    }

    private void loadData() {
        // Load toàn bộ bài hát cho tab danh sách và ban đầu cho tab tìm kiếm
        // Đảm bảo 'database' không null trước khi truy vấn
        if (database != null) {
            loadToanBoBaiHat();
            // Load danh sách yêu thích
            loadBaiHatYeuThich();
        } else {
            Log.e("DATABASE_ERROR", "Database is null when trying to load data.");
            Toast.makeText(this, "Lỗi: Không thể tải dữ liệu. CSDL chưa sẵn sàng.", Toast.LENGTH_LONG).show();
        }
    }

    private void loadToanBoBaiHat() {
        dsToanBoBaiHat.clear();
        dsBaiHatTimKiem.clear(); // Cập nhật danh sách tìm kiếm với toàn bộ bài hát ban đầu

        // Kiểm tra database trước khi truy vấn
        if (database == null || !database.isOpen()) {
            Log.e("DATABASE_ERROR", "Database is not open or null in loadToanBoBaiHat.");
            return;
        }

        Cursor cursor = database.query("ArirangSongBook", null, null, null, null, null, "MABH ASC");
        while (cursor.moveToNext()) {
            String maBH = cursor.getString(0);
            String tenBH = cursor.getString(1);
            String loiBH = cursor.getString(2);
            String tacGia = cursor.getString(3);
            int yeuThichInt = cursor.getInt(4);
            BaiHat baiHat = new BaiHat(maBH, tenBH, loiBH, tacGia, yeuThichInt == 1);
            dsToanBoBaiHat.add(baiHat);
            dsBaiHatTimKiem.add(baiHat); // Thêm vào ds tìm kiếm ban đầu
        }
        cursor.close();
        adapterToanBoBaiHat.notifyDataSetChanged();
        adapterBaiHatTimKiem.notifyDataSetChanged();
    }

    private void loadBaiHatYeuThich() {
        dsBaiHatYeuThich.clear();
        // Kiểm tra database trước khi truy vấn
        if (database == null || !database.isOpen()) {
            Log.e("DATABASE_ERROR", "Database is not open or null in loadBaiHatYeuThich.");
            return;
        }

        Cursor cursor = database.query("ArirangSongBook", null, "YEUTHICH=?", new String[]{"1"}, null, null, "MABH ASC");
        while (cursor.moveToNext()) {
            String maBH = cursor.getString(0);
            String tenBH = cursor.getString(1);
            String loiBH = cursor.getString(2);
            String tacGia = cursor.getString(3);
            // Giá trị isYeuThich luôn là true vì chúng ta đang truy vấn bài hát yêu thích
            BaiHat baiHat = new BaiHat(maBH, tenBH, loiBH, tacGia, true);
            dsBaiHatYeuThich.add(baiHat);
        }
        cursor.close();
        adapterBaiHatYeuThich.notifyDataSetChanged();
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t1")) { // Tab Tìm kiếm
                    // Khi chuyển sang tab tìm kiếm, nếu ô tìm kiếm rỗng, hiển thị toàn bộ
                    if (edtTimKiem.getText().length() == 0) {
                        loadToanBoBaiHat(); // load toàn bộ vào dsBaiHatTimKiem
                    } else {
                        // Nếu có text, thực hiện tìm kiếm lại
                        xuLyTimKiem(edtTimKiem.getText().toString());
                    }
                } else if (tabId.equalsIgnoreCase("t2")) { // Tab Danh sách
                    loadToanBoBaiHat(); // Luôn load lại toàn bộ khi chuyển tab này
                } else if (tabId.equalsIgnoreCase("t3")) { // Tab Yêu thích
                    loadBaiHatYeuThich(); // Luôn load lại danh sách yêu thích khi chuyển tab này
                }
            }
        });

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                xuLyTimKiem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Sự kiện click item cho cả 3 ListView
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiHat baiHatDuocChon = null;
                // Lấy đối tượng BaiHat từ adapter tương ứng
                if (parent.getId() == R.id.lvBaiHatTimKiem) {
                    baiHatDuocChon = adapterBaiHatTimKiem.getItem(position);
                } else if (parent.getId() == R.id.lvToanBoBaiHat) {
                    baiHatDuocChon = adapterToanBoBaiHat.getItem(position);
                } else if (parent.getId() == R.id.lvBaiHatYeuThich) {
                    baiHatDuocChon = adapterBaiHatYeuThich.getItem(position);
                }

                if (baiHatDuocChon != null) {
                    Intent intent = new Intent(MainActivity.this, SubActivity.class);
                    // Truyền toàn bộ đối tượng BaiHat hoặc các thuộc tính cần thiết
                    // Đảm bảo class BaiHat implements Parcelable hoặc Serializable nếu truyền cả object
                    // Ở đây bạn truyền MABH là đủ cho SubActivity để truy vấn lại
                    intent.putExtra("MABH_CHON", baiHatDuocChon.getMaBH());
                    startActivityForResult(intent, 100); // Dùng startActivityForResult để cập nhật nếu trạng thái like thay đổi
                }
            }
        };

        lvBaiHatTimKiem.setOnItemClickListener(itemClickListener);
        lvToanBoBaiHat.setOnItemClickListener(itemClickListener);
        lvBaiHatYeuThich.setOnItemClickListener(itemClickListener);
    }

    private void xuLyTimKiem(String query) {
        dsBaiHatTimKiem.clear();
        // Kiểm tra database trước khi truy vấn
        if (database == null || !database.isOpen()) {
            Log.e("DATABASE_ERROR", "Database is not open or null in xuLyTimKiem.");
            adapterBaiHatTimKiem.notifyDataSetChanged(); // Cập nhật để hiển thị trống
            return;
        }

        Cursor cursor;
        if (query.isEmpty()) {
            // Nếu query rỗng, hiển thị toàn bộ bài hát trong tab tìm kiếm
            cursor = database.query("ArirangSongBook", null, null, null, null, null, "MABH ASC");
        } else {
            // Thực hiện truy vấn CSDL với điều kiện LIKE
            String selection = "lower(TENBH) LIKE ?";
            String[] selectionArgs = new String[]{"%" + query.toLowerCase(Locale.getDefault()) + "%"};
            cursor = database.query("ArirangSongBook", null, selection, selectionArgs, null, null, "MABH ASC");
        }

        while (cursor.moveToNext()) {
            String maBH = cursor.getString(0);
            String tenBH = cursor.getString(1);
            String loiBH = cursor.getString(2);
            String tacGia = cursor.getString(3);
            int yeuThichInt = cursor.getInt(4);
            dsBaiHatTimKiem.add(new BaiHat(maBH, tenBH, loiBH, tacGia, yeuThichInt == 1));
        }
        cursor.close();
        adapterBaiHatTimKiem.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            // Trạng thái yêu thích có thể đã thay đổi trong SubActivity
            // Load lại dữ liệu cho các tab để đảm bảo tính nhất quán
            loadToanBoBaiHat(); // Sẽ cập nhật cả dsBaiHatTimKiem
            loadBaiHatYeuThich();
            // Nếu đang ở tab tìm kiếm và có query, gọi lại hàm tìm kiếm
            if(tabHost.getCurrentTabTag().equalsIgnoreCase("t1") && edtTimKiem.getText().length() > 0){
                xuLyTimKiem(edtTimKiem.getText().toString());
            }
        }
    }

    public void capNhatLaiDanhSachYeuThichKhiThaoTac() {
        // Được gọi khi có thay đổi trạng thái like/unlike từ adapter hoặc subactivity
        // để cập nhật lại danh sách ở tab YeuThich nếu đang mở tab đó
        if (tabHost.getCurrentTabTag() != null && tabHost.getCurrentTabTag().equalsIgnoreCase("t3")) {
            loadBaiHatYeuThich();
        }
        // Luôn cập nhật lại trạng thái trong dsToanBoBaiHat và dsBaiHatTimKiem
        loadToanBoBaiHat();
        // Nếu đang ở tab tìm kiếm và có query, cập nhật lại kết quả tìm kiếm
        if(tabHost.getCurrentTabTag().equalsIgnoreCase("t1") && edtTimKiem.getText().length() > 0) {
            xuLyTimKiem(edtTimKiem.getText().toString());
        } else if (tabHost.getCurrentTabTag().equalsIgnoreCase("t1")) {
            // Nếu không có query tìm kiếm, dsBaiHatTimKiem đã được cập nhật bởi loadToanBoBaiHat
            adapterBaiHatTimKiem.notifyDataSetChanged();
        }
    }

    // Phương thức sao chép CSDL duy nhất và được cải tiến
    private void copyDatabase(Context context) {
        try {
            // Đảm bảo tên này khớp với tên file trong thư mục assets và DATABASE_NAME
            String dbName = DATABASE_NAME; // "arirang.sqlite"
            File dbPath = context.getDatabasePath(dbName);

            // Chỉ sao chép nếu database chưa tồn tại
            if (!dbPath.exists()) {
                dbPath.getParentFile().mkdirs(); // Tạo thư mục nếu chưa có

                InputStream is = context.getAssets().open(dbName);
                OutputStream os = new FileOutputStream(dbPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();
                Log.d("DB_COPY", "Database copied successfully.");
                Toast.makeText(context, "Sao chép CSDL thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("DB_COPY", "Database already exists.");
            }

        } catch (IOException e) {
            Log.e("DB_COPY", "Error copying database", e);
            Toast.makeText(context, "Lỗi sao chép CSDL: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    // Xóa hoặc comment các phương thức sao chép CSDL cũ không dùng đến
    // private void xuLySaoChepCSDLTuAssetsVaoHeThong() { ... }
    // private void saoChepCSDLTuAssets() { ... }
    // private String layDuongDanLuuTruCSDL() { ... }
}