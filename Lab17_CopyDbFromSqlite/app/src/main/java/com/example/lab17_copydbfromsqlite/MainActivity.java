package com.example.lab17_copydbfromsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database = null;
    String DATABASE_NAME = "qlsach.db";

    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo ListView và Adapter
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        if (processCopy()) {
            // Mở CSDL từ đúng đường dẫn
            String dbPath = getDatabasePath(DATABASE_NAME).getPath();
            database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

            // Truy vấn dữ liệu
            Cursor c = null;
            try {
                c = database.query("tbsach", null, null, null, null, null, null);
                if (c != null && c.moveToFirst()) {
                    do {
                        String data = c.getInt(0) + " - " + c.getString(1) + " - " + c.getString(2);
                        mylist.add(data);
                    } while (c.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error querying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if (c != null) c.close();
            }

            myadapter.notifyDataSetChanged();

            if (mylist.isEmpty()) {
                Toast.makeText(this, "No data found in the database.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Database is not available. Cannot load data.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);

        // Xóa file cũ nếu có
        if (dbFile.exists()) {
            dbFile.delete();
        }

        // Copy từ assets
        try {
            if (copyDatabaseFromAssets()) {
                Toast.makeText(this, "Database copied successfully from assets.", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Failed to copy database from assets.", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error during database setup: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    private boolean copyDatabaseFromAssets() {
        InputStream myInput = null;
        OutputStream myOutput = null;

        try {
            myInput = getAssets().open(DATABASE_NAME); // assets/qlsach.db
            File outFile = getDatabasePath(DATABASE_NAME);

            // Tạo thư mục nếu chưa tồn tại
            File parentDir = outFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            myOutput = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (myOutput != null) myOutput.close();
                if (myInput != null) myInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
