package com.example.demo_sanpham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class cls_menu extends AppCompatActivity {

    private Button btnStudentProfile, btnCourseRegistration, btnRegisteredCoursesResults, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        // Liên kết các button với các thành phần trong layout
        btnStudentProfile = findViewById(R.id.btnStudentProfile);
        btnCourseRegistration = findViewById(R.id.btnCourseRegistration);
        btnRegisteredCoursesResults = findViewById(R.id.btnRegisteredCoursesResults);
        btnLogout = findViewById(R.id.btnLogout);

        // Xử lý sự kiện khi click vào các button
        btnStudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến màn hình Hồ Sơ Sinh Viên
                Intent intent = new Intent(cls_menu.this, cls_ttsv.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        btnCourseRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến màn hình Đăng Ký Học Phần
                Intent intent = new Intent(cls_menu.this, cls_dkhocphan.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        btnRegisteredCoursesResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến màn hình Kết Quả Đăng Ký Học Phần
                Intent intent = new Intent(cls_menu.this, cls_ketquadkhp.class);
                String username = getIntent().getStringExtra("USERNAME");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến màn hình đăng nhập
                Intent intent = new Intent(cls_menu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
