package com.example.lab12_3_spinner;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        txtResult = findViewById(R.id.txtResult);

        // Lấy dữ liệu từ strings.xml
        String[] categories = getResources().getStringArray(R.array.category_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Xử lý sự kiện chọn item
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selected = categories[position];
                txtResult.setText(getString(R.string.you_selected) + " " + selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtResult.setText("");
            }
        });
    }
}
