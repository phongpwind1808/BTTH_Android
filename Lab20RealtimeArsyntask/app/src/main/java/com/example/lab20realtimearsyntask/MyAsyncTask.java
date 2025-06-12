package com.example.lab20realtimearsyntask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    // Khai báo context để lưu trữ context của MainActivity
    private Activity contextCha;

    // Constructor này được truyền vào là MainActivity
    public MyAsyncTask(Activity ctx) {
        contextCha = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Hiển thị thông báo khi task bắt đầu
        Toast.makeText(contextCha, "onPreExecute!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        for (int i = 0; i <= 100; i++) {
            // Giảm tốc độ tăng tiến trình
            SystemClock.sleep(100);

            // Gửi giá trị tiến trình lên UI
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        // Lấy control ProgressBar trong MainActivity
        ProgressBar progressBar = contextCha.findViewById(R.id.progressBar);

        // Lấy control TextView trong MainActivity
        TextView textView = contextCha.findViewById(R.id.textView);

        // Cập nhật giá trị của ProgressBar
        if (progressBar != null) {
            progressBar.setProgress(values[0]);
        }

        // Cập nhật giá trị của TextView
        if (textView != null) {
            textView.setText(values[0] + "%");
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Hiển thị thông báo khi task hoàn thành
        Toast.makeText(contextCha, "Update xong roi do!", Toast.LENGTH_LONG).show();
    }
}
