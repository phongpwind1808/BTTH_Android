package com.example.lab22parsexml;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {

    public String getXmlFromUrl(String urlString) {
        String xml = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập kết nối
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);

            // Bắt đầu kết nối
            connection.connect();

            int responseCode = connection.getResponseCode();
            Log.d("XMLParser", "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                xml = stringBuilder.toString();

                inputStream.close();
                reader.close();
            } else {
                Log.e("XMLParser", "Server trả về mã lỗi: " + responseCode);
            }

        } catch (Exception e) {
            Log.e("XMLParser", "Lỗi khi lấy XML từ URL: ", e);
        }

        return xml;
    }
}
