package com.example.lab22parsexml;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnparse;
    ListView lvi;
    ArrayAdapter<String> myadapter;
    ArrayList<String> mylist;
    String URL = "https://www.w3schools.com/xml/plant_catalog.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần UI
        btnparse = (Button) findViewById(R.id.butbtnparseon);
        lvi = (ListView) findViewById(R.id.lvi);

        // Khởi tạo danh sách và adapter
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
        lvi.setAdapter(myadapter);

        // Thêm sự kiện click cho nút parse
        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadExampleTask task = new LoadExampleTask();
                task.execute();
            }
        });
    }
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> list = new ArrayList<>();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                XMLParser xmlParser = new XMLParser();
                String xml = xmlParser.getXmlFromUrl(URL);

                Log.d("XML_DEBUG", "Nội dung XML nhận được:\n" + xml); // <-- In ra XML để kiểm tra

                if (xml != null && !xml.isEmpty()) {
                    parser.setInput(new StringReader(xml));

                    int eventType = parser.getEventType();
                    String nodeName = "";
                    String dataShow = "";

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                nodeName = parser.getName();
                                if (nodeName.equals("COMMON")) { // Ví dụ, dùng COMMON thay vì id/name
                                    dataShow += parser.nextText() + " - ";
                                } else if (nodeName.equals("ZONE")) {
                                    dataShow += "Zone: " + parser.nextText();
                                }
                                break;

                            case XmlPullParser.END_TAG:
                                nodeName = parser.getName();
                                if (nodeName.equals("PLANT")) {
                                    list.add(dataShow);
                                    dataShow = "";
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                } else {
                    Log.e("XMLParser", "Không có dữ liệu XML để parse");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("XMLParser", "Lỗi parse XML: ", e);
            }

            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear(); // Xóa dữ liệu cũ trong adapter
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            myadapter.clear(); // Xóa dữ liệu cũ trong adapter
            myadapter.addAll(result); // Thêm dữ liệu mới vào adapter
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}