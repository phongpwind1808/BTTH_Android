// MainActivity.java
package com.example.lab5_2_quadraticequation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextA, editTextB, editTextC;
    private TextView textViewResult;
    private Button buttonSolve, buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần của giao diện
        editTextA = findViewById(R.id.editTextA);
        
        editTextB = findViewById(R.id.editTextB);
        editTextC = findViewById(R.id.editTextC);
        textViewResult = findViewById(R.id.txtResult);
        buttonSolve = findViewById(R.id.buttonCalc);
        buttonExit = findViewById(R.id.buttonExit);

        // Thiết lập sự kiện khi nhấn nút Giải PT
        buttonSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solveEquation();
            }
        });

        // Thiết lập sự kiện khi nhấn nút Thoát
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void solveEquation() {
        try {
            // Lấy giá trị từ các EditText
            double a = Double.parseDouble(editTextA.getText().toString());
            double b = Double.parseDouble(editTextB.getText().toString());
            double c = Double.parseDouble(editTextC.getText().toString());

            // Tính delta
            double delta = b * b - 4 * a * c;

            // Hiển thị kết quả
            String result;
            if (a == 0) {
                // Phương trình bậc nhất: bx + c = 0
                if (b == 0) {
                    if (c == 0) {
                        result = "Phương trình có vô số nghiệm";
                    } else {
                        result = "Phương trình vô nghiệm";
                    }
                } else {
                    double x = -c / b;
                    result = "Phương trình có nghiệm x = " + x;
                }
            } else {
                // Phương trình bậc hai: ax² + bx + c = 0
                if (delta < 0) {
                    result = "Phương trình vô nghiệm";
                } else if (delta == 0) {
                    double x = -b / (2 * a);
                    result = "Phương trình có nghiệm kép x1 = x2 = " + x;
                } else {
                    double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                    double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                    result = "x1 = " + x1 + ", x2 = " + x2;
                }
            }

            // Hiển thị công thức ở dưới
            String formula = "PT: " + a + "x² + " + b + "x + " + c + " = 0";
            textViewResult.setText(formula + "\n" + result);

        } catch (NumberFormatException e) {
            textViewResult.setText("Vui lòng nhập đúng số");
        }
    }
}