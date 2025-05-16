package com.example.lab8_intent_call_sms;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CallPhoneActivity extends AppCompatActivity {
    EditText edtPhone;
    Button btnPhone,btnBackPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_phone);
        edtPhone = findViewById(R.id.edtPhone);
        btnPhone = findViewById(R.id.btnPhone);
        btnBackPhone = findViewById(R.id.btnBackPhone);
        btnPhone.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+edtPhone.getText().toString()));
            if(ActivityCompat.checkSelfPermission(CallPhoneActivity.this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(CallPhoneActivity.this,new String[]{android.Manifest.permission.CALL_PHONE},1);
                return;
            };
            startActivity(callIntent);
        });
        btnBackPhone.setOnClickListener(v -> {
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}