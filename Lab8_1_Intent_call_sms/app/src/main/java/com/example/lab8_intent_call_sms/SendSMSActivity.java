package com.example.lab8_intent_call_sms;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSMSActivity extends AppCompatActivity {
    EditText edtSms;
    Button btnSendSms, btnBackSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        edtSms = findViewById(R.id.edtSms);
        btnSendSms = findViewById(R.id.btnSms);
        btnBackSms = findViewById(R.id.btnBackSMS);
        btnSendSms.setOnClickListener(v->{
            Intent callIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtSms.getText().toString()));
            startActivity(callIntent);
        });
        btnBackSms.setOnClickListener(v -> {
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}