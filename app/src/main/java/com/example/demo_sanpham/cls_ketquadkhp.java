package com.example.demo_sanpham;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_sanpham.adapters.RegisteredCourseAdapter;
import com.example.demo_sanpham.model.cls_RegisteredCourse;

import java.util.ArrayList;
import java.util.List;

public class cls_ketquadkhp extends Activity {
    private RecyclerView rvRegisteredCourses;
    private RegisteredCourseAdapter registeredCourseAdapter;
    private cls_sqlite dbHelper;
    private Button btnBackToRegister;
    private String currentUsername; // Thay đổi để lưu username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ketquadkhp);

        rvRegisteredCourses = findViewById(R.id.rvRegisteredCourses);
        rvRegisteredCourses.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new cls_sqlite(this);

        // Lấy username từ Intent
        currentUsername = getIntent().getStringExtra("USERNAME");

        // Kiểm tra xem currentUsername có phải là null không
        if (currentUsername == null) {
            Toast.makeText(this, "Username không được phép null", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc activity nếu username không hợp lệ
            return;
        }

        // Lấy dữ liệu đã đăng ký và hiển thị
        List<cls_RegisteredCourse> registeredCourseList = getAllRegisteredCourses();
        registeredCourseAdapter = new RegisteredCourseAdapter(registeredCourseList);
        rvRegisteredCourses.setAdapter(registeredCourseAdapter);

        // Thiết lập sự kiện cho nút Quay lại
        btnBackToRegister = findViewById(R.id.btnBackToRegister);
        btnBackToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToRegister(v);
            }
        });
    }

    private List<cls_RegisteredCourse> getAllRegisteredCourses() {
        List<cls_RegisteredCourse> registeredCourseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn với username, kiểm tra không null
        Cursor cursor = db.rawQuery("SELECT * FROM RegisteredCourses WHERE username = ?", new String[]{currentUsername});

        if (cursor.moveToFirst()) {
            do {
                String courseCode = cursor.getString(cursor.getColumnIndex("course_code"));

                // Lấy thông tin khóa học từ bảng Courses
                Cursor courseCursor = db.rawQuery("SELECT * FROM Courses WHERE course_code = ?", new String[]{courseCode});
                String courseName = "";
                String classDay = "";
                String classPeriod = "";
                String startDate = "";
                String credits = ""; // Thêm biến số tín chỉ

                if (courseCursor.moveToFirst()) {
                    courseName = courseCursor.getString(courseCursor.getColumnIndex("course_name"));
                    classDay = courseCursor.getString(courseCursor.getColumnIndex("class_day"));
                    classPeriod = courseCursor.getString(courseCursor.getColumnIndex("class_period"));
                    startDate = courseCursor.getString(courseCursor.getColumnIndex("start_date"));
                    credits = courseCursor.getString(courseCursor.getColumnIndex("credits")); // Lấy số tín chỉ
                }

                courseCursor.close();

                cls_RegisteredCourse registeredCourse = new cls_RegisteredCourse(currentUsername, courseCode, courseName, classDay, classPeriod, startDate, credits);
                registeredCourseList.add(registeredCourse);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registeredCourseList;
    }

    // Phương thức xử lý sự kiện nút "Quay lại Đăng Ký"
    public void backToRegister(View view) {
        Intent intent = new Intent(cls_ketquadkhp.this, cls_dkhocphan.class);
        intent.putExtra("USERNAME", currentUsername); // Truyền lại username
        startActivity(intent);
        finish(); // Kết thúc activity hiện tại để người dùng không quay lại trang kết quả
    }
}
