package com.example.lab19readdatafromxml;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    Button btnParse;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        btnParse = findViewById(R.id.btnParse);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        // Set click listener for the button
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseXML();
            }
        });
    }

    private void parseXML() {
        try {
            // Open the employee.xml file from assets
            InputStream inputStream = getAssets().open("employee.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            String dataShow = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("employee".equals(nodeName)) {
                            dataShow += parser.getAttributeValue(0) + " - ";
                        } else if ("name".equals(nodeName)) {
                            dataShow += parser.nextText() + " - ";
                        } else if ("phone".equals(nodeName)) {
                            dataShow += parser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("employee".equals(nodeName)) {
                            mylist.add(dataShow);
                            dataShow = "";
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

            // Notify the adapter that the data has changed
            myadapter.notifyDataSetChanged();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}