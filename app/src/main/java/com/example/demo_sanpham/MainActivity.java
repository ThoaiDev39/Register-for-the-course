package com.example.demo_sanpham;

import com.google.android.material.snackbar.Snackbar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private cls_sqlite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
//        resetAppDatabase();
        // Khởi tạo các view
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Khởi tạo đối tượng SQLiteOpenHelper
        dbHelper = new cls_sqlite(this);

        // Xử lý sự kiện khi click vào nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    // Hiển thị thông báo nếu tên đăng nhập hoặc mật khẩu trống
                    Snackbar.make(v, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra đăng nhập từ SQLite
                    if (checkLogin(username, password)) {
                        // Đăng nhập thành công, chuyển hướng sang màn hình khác
                        Intent intent = new Intent(MainActivity.this, cls_menu.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        finish(); // Đóng activity đăng nhập sau khi đăng nhập thành công
                    } else {
                        // Đăng nhập thất bại, hiển thị thông báo lỗi
                        Snackbar.make(v, "Tên đăng nhập hoặc mật khẩu không đúng.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Phương thức kiểm tra thông tin đăng nhập từ SQLite
    private boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM Users WHERE username = ?", new String[]{username});

        boolean result = false;
        if (cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndex("password"));
            result = password.equals(storedPassword);
        }

        cursor.close();
        db.close();

        return result;
    }

    public void resetAppDatabase() {
        cls_sqlite dbHelper = new cls_sqlite(this);
        dbHelper.deleteDatabase();
        // Tạo lại cơ sở dữ liệu và bảng
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
    }
}
