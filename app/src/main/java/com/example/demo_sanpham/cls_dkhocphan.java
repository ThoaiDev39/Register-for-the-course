package com.example.demo_sanpham;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_sanpham.adapters.CourseAdapter;
import com.example.demo_sanpham.model.cls_Course;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class cls_dkhocphan extends AppCompatActivity {

    private RecyclerView rvCourses;
    private CourseAdapter courseAdapter;
    private cls_sqlite dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dkhocphan);


        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new cls_sqlite(this);

        //lấy username từ Intent
        username = getIntent().getStringExtra("USERNAME");

        // Lấy dữ liệu từ SQLite và hiển thị
        List<cls_Course> courseList = getAllCourses();
        courseAdapter = new CourseAdapter(courseList, dbHelper, this);
        rvCourses.setAdapter(courseAdapter);

        // Chuyểnh hướng về Menu
        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(cls_dkhocphan.this, cls_menu.class) ;
                it.putExtra("USERNAME", username);
                startActivity(it);
            }
        });
        // Bắt sự kiện click vào nút đăng ký
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onRegisterClick(int position, boolean isChecked) {
                cls_Course selectedCourse = courseList.get(position);

                if (isChecked) {
                    registerCourse(selectedCourse);
                } else {
                    unregisterCourse(selectedCourse);
                }

                selectedCourse.setChecked(isChecked);
                courseAdapter.notifyItemChanged(position);
            }
        });

        // Bắt sự kiện click vào nút "Xem Kết Quả Đăng Ký"
        Button btnRegisteredCourse = findViewById(R.id.btnRegisteredCourse);
        btnRegisteredCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cls_dkhocphan.this, cls_ketquadkhp.class);
                intent.putExtra("USERNAME", username); // Đảm bảo currentUsername không phải là null
                startActivity(intent);
            }
        });
    }
    private List<cls_Course> getAllCourses() {
        List<cls_Course> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Courses", null);

        if (cursor.moveToFirst()) {
            do {
                String courseCode = cursor.getString(cursor.getColumnIndex("course_code"));
                String courseName = cursor.getString(cursor.getColumnIndex("course_name"));
                String classDay = cursor.getString(cursor.getColumnIndex("class_day"));
                String classPeriod = cursor.getString(cursor.getColumnIndex("class_period"));
                String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
                int credits = cursor.getInt(cursor.getColumnIndex("credits")); // Lấy số tín chỉ

                cls_Course course = new cls_Course(courseCode, courseName, classDay, classPeriod, startDate, credits);

                // Kiểm tra xem khóa học đã được đăng ký hay chưa
                if (isCourseRegistered(courseCode)) {
                    course.setChecked(true);
                } else {
                    course.setChecked(false);
                }

                courseList.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courseList;
    }


    private boolean isCourseRegistered(String courseCode) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RegisteredCourses WHERE username = ? AND course_code = ?", new String[]{username, courseCode});
        boolean isRegistered = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isRegistered;
    }

    private void registerCourse(cls_Course course) {
        String courseCode = course.getCourseCode();

        if (courseCode != null) {
            dbHelper.registerCourse(username, courseCode);
            Toast.makeText(cls_dkhocphan.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(cls_dkhocphan.this, "Vui lòng chọn khóa học!", Toast.LENGTH_SHORT).show();
        }
    }

    private void unregisterCourse(cls_Course course) {
        dbHelper.unregisterCourse(username, course.getCourseCode());
        Snackbar.make(rvCourses, "Đã hủy đăng ký môn học " + course.getCourseName(), Snackbar.LENGTH_SHORT).show();
    }
}
