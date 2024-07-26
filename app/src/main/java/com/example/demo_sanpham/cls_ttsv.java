package com.example.demo_sanpham;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class cls_ttsv extends Activity {

    private cls_sqlite dbHelper;
    private EditText etAddress, etCCCD, etDayOfBirth, etPlaceOfBirth, etPassword;
    private TextView tvUsername, tvFullName, tvAddress, tvCCCD, tvDayOfBirth, tvPlaceOfBirth, tvPassword;
    private Button btnUpdateInfo;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ttsv);

        // Khởi tạo đối tượng cơ sở dữ liệu
        dbHelper = new cls_sqlite(this);

        // Lấy thông tin người dùng từ Intent
        currentUsername = getIntent().getStringExtra("USERNAME");

        // Kết nối các thành phần giao diện
        etAddress = findViewById(R.id.etAddress);
        etCCCD = findViewById(R.id.etCCCD);
        etDayOfBirth = findViewById(R.id.etDayOfBirth);
        etPlaceOfBirth = findViewById(R.id.etPlaceOfBirth);
        etPassword = findViewById(R.id.etPassword); // Thêm trường mật khẩu

        tvUsername = findViewById(R.id.tvUsername);
        tvFullName = findViewById(R.id.tvFullName);
        tvAddress = findViewById(R.id.tvAddress);
        tvCCCD = findViewById(R.id.tvCCCD);
        tvDayOfBirth = findViewById(R.id.tvDayOfBirth);
        tvPlaceOfBirth = findViewById(R.id.tvPlaceOfBirth);
        tvPassword = findViewById(R.id.tvPassword); // Thêm TextView cho mật khẩu

        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);

        // Hiển thị thông tin người dùng
        loadUserData();

        // Cập nhật thông tin người dùng khi nút được nhấn
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    private void loadUserData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Users", null, "username = ?", new String[]{currentUsername}, null, null, null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String fullname = cursor.getString(cursor.getColumnIndex("fullname"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String cccd = cursor.getString(cursor.getColumnIndex("CCCD"));
            String dayOfBirth = cursor.getString(cursor.getColumnIndex("day_of_birth"));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex("place_of_birth"));

            // Hiển thị dữ liệu vào các TextView
            tvUsername.setText("Username: " + username);
            tvFullName.setText("Full Name: " + fullname);
            tvAddress.setText("Address: " + address);
            tvCCCD.setText("CCCD: " + cccd);
            tvDayOfBirth.setText("Day of Birth: " + dayOfBirth);
            tvPlaceOfBirth.setText("Place of Birth: " + placeOfBirth);
            tvPassword.setText("Password: " + password); // Hiển thị mật khẩu (nên cẩn thận với điều này)

            // Để trống các EditText để người dùng có thể nhập thông tin mới
            etAddress.setText("");
            etCCCD.setText("");
            etDayOfBirth.setText("");
            etPlaceOfBirth.setText("");
            etPassword.setText(""); // Để trống mật khẩu để nhập thông tin mới
        }
        cursor.close();
        db.close(); // Đảm bảo đóng cơ sở dữ liệu sau khi sử dụng
    }


    private void updateUserInfo() {
        String address = etAddress.getText().toString().trim();
        String cccd = etCCCD.getText().toString().trim();
        String dayOfBirth = etDayOfBirth.getText().toString().trim();
        String placeOfBirth = etPlaceOfBirth.getText().toString().trim();
        String password = etPassword.getText().toString().trim(); // Lấy mật khẩu mới

        // Cập nhật thông tin người dùng trong cơ sở dữ liệu
        ContentValues values = new ContentValues();

        // Chỉ thêm vào ContentValues nếu giá trị không rỗng
        if (!address.isEmpty()) {
            values.put("address", address);
        }
        if (!cccd.isEmpty()) {
            values.put("CCCD", cccd);
        }
        if (!dayOfBirth.isEmpty()) {
            values.put("day_of_birth", dayOfBirth);
        }
        if (!placeOfBirth.isEmpty()) {
            values.put("place_of_birth", placeOfBirth);
        }
        if (!password.isEmpty()) {
            values.put("password", password);
        }

        // Nếu không có gì để cập nhật, không thực hiện cập nhật
        if (values.size() > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rowsUpdated = db.update("Users", values, "username = ?", new String[]{currentUsername});
            db.close(); // Đóng cơ sở dữ liệu sau khi cập nhật

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                loadUserData(); // Tải lại dữ liệu để phản ánh những thay đổi
            } else {
                Toast.makeText(this, "Lỗi cập nhật thông tin", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không có thông tin mới để cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

}
