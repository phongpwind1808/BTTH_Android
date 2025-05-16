package com.example.lab6_registerinformation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    // Declare UI components
    private TextInputEditText editHoten, editCMND;
    private EditText editBosung;
    private RadioGroup radioGroupBangCap;
    private CheckBox chkDocbao, chkDocsach, chkDocCoding;
    private MaterialButton btnGuiTT;

    // Flag to track if exit is allowed
    private boolean canExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editHoten = findViewById(R.id.editHoten);
        editCMND = findViewById(R.id.editCMND);
        editBosung = findViewById(R.id.editBosung);
        radioGroupBangCap = findViewById(R.id.radioGroup);
        chkDocbao = findViewById(R.id.chkDocbao);
        chkDocsach = findViewById(R.id.chkDocsach);
        chkDocCoding = findViewById(R.id.chkDocCoding);
        btnGuiTT = findViewById(R.id.btnguiTT);

        // Set click listener for "Gửi thông tin" button
        btnGuiTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // Collect user information
                    String hoTen = editHoten.getText().toString().trim();
                    String cmnd = editCMND.getText().toString().trim();

                    // Get selected education level
                    String bangCap = "";
                    int selectedRadioId = radioGroupBangCap.getCheckedRadioButtonId();
                    if (selectedRadioId == R.id.radTrungCap) bangCap = "Trung cấp";
                    else if (selectedRadioId == R.id.radCaoDang) bangCap = "Cao đẳng";
                    else if (selectedRadioId == R.id.radDaiHoc) bangCap = "Đại học";

                    // Collect hobbies
                    StringBuilder sothich = new StringBuilder();
                    if (chkDocbao.isChecked()) sothich.append("Đọc báo, ");
                    if (chkDocsach.isChecked()) sothich.append("Đọc sách, ");
                    if (chkDocCoding.isChecked()) sothich.append("Đọc coding, ");

                    // Remove last comma and space if exists
                    if (sothich.length() > 0) {
                        sothich.setLength(sothich.length() - 2);
                    }

                    String boSung = editBosung.getText().toString().trim();

                    // Create alert dialog with user information
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Thông Tin Cá Nhân");
                    builder.setMessage(
                            "Họ tên: " + hoTen + "\n" +
                                    "CMND: " + cmnd + "\n" +
                                    "Bằng cấp: " + bangCap + "\n" +
                                    "Sở thích: " + (sothich.length() > 0 ? sothich.toString() : "Không có") + "\n" +
                                    "Thông tin bổ sung: " + (TextUtils.isEmpty(boSung) ? "Không có" : boSung)
                    );
                    builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    // Input validation method
    private boolean validateInput() {
        boolean isValid = true;

        // Validate Họ tên (Name)
        String hoTen = editHoten.getText().toString().trim();
        if (hoTen.length() < 3) {
            editHoten.setError("Họ tên phải có ít nhất 3 ký tự");
            isValid = false;
        }

        // Validate CMND (ID Number)
        String cmnd = editCMND.getText().toString().trim();
        if (cmnd.length() != 9) {
            editCMND.setError("CMND phải có chính xác 9 chữ số");
            isValid = false;
        }

        // Validate at least one hobby is selected
        if (!chkDocbao.isChecked() && !chkDocsach.isChecked() && !chkDocCoding.isChecked()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một sở thích", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
            return;
        }

        // Show exit confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng")
                .setMessage("Bạn có muốn thoát khỏi ứng dụng?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canExit = true;
                        finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canExit = false;
                        dialog.dismiss();
                    }
                })
                .setCancelable(false) // Prevents dismissing the dialog by touching outside
                .show();
    }
}