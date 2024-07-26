package com.example.demo_sanpham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class cls_sqlite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_app_db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public cls_sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTables(db);
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        // Tạo bảng Users
        String CREATE_USERS_TABLE = "CREATE TABLE Users (" +
                "username TEXT PRIMARY KEY," +
                "fullname TEXT," +
                "password TEXT," +
                "address TEXT," +
                "CCCD TEXT," +
                "day_of_birth TEXT," +
                "place_of_birth TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Tạo bảng Courses
        String CREATE_COURSES_TABLE = "CREATE TABLE Courses (" +
                "course_code TEXT PRIMARY KEY, " +
                "course_name TEXT, " +
                "class_day TEXT, " +
                "credits INTEGER, " +
                "class_period TEXT, " +
                "start_date TEXT)";
        db.execSQL(CREATE_COURSES_TABLE);

        // Tạo bảng RegisteredCourses
        String CREATE_REGISTERED_COURSES_TABLE = "CREATE TABLE RegisteredCourses (" +
                "username TEXT, " +
                "course_code TEXT, " +
                "PRIMARY KEY (username, course_code), " +
                "FOREIGN KEY(username) REFERENCES Users(username), " +
                "FOREIGN KEY(course_code) REFERENCES Courses(course_code))";
        db.execSQL(CREATE_REGISTERED_COURSES_TABLE);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Courses");
        db.execSQL("DROP TABLE IF EXISTS RegisteredCourses");
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Chèn dữ liệu vào bảng Users (ví dụ)
        insertUser(db, "211a010325", "010325", "Lê Thoại", "123 Đường ABC", "123456789", "2003-22-11", "Thanh Hóa");
        insertUser(db, "211a010352", "010352", "Mỹ Hân", "123 Đường TQB", "987654321", "2003-15-02", "Hồ Chí Minh");
        // Chèn dữ liệu vào bảng Courses (ví dụ)
        insertCourse(db, "IT001", "Lập trình C", "Thứ 2", 5, "1-5", "2024-09-01");
        insertCourse(db, "IT002", "Lập trình OOP", "Thứ 3", 4, "4-8", "2024-09-02");
        insertCourse(db, "VN001", "Kĩ năng sử dụng tiếng Việt", "Thứ 5", 3, "1-5", "2024-09-03");
        insertCourse(db, "IT003", "Lập trình Android", "Thứ 6", 4, "11-15", "2024-09-04");
        insertCourse(db, "IT009", "Đồ án Khoa học máy tính", "Thứ 7", 6, "4-8", "2024-09-05");
    }

    private void insertUser(SQLiteDatabase db, String username, String password, String fullname, String address, String CCCD, String dayOfBirth, String placeOfBirth) {
        ContentValues userValues = new ContentValues();
        userValues.put("username", username);
        userValues.put("password", password);
        userValues.put("fullname", fullname);
        userValues.put("address", address);
        userValues.put("CCCD", CCCD);
        userValues.put("day_of_birth", dayOfBirth);
        userValues.put("place_of_birth", placeOfBirth);
        db.insert("Users", null, userValues);
    }

    private void insertCourse(SQLiteDatabase db, String courseCode, String courseName, String classDay, int credits, String classPeriod, String startDate) {
        ContentValues courseValues = new ContentValues();
        courseValues.put("course_code", courseCode);
        courseValues.put("course_name", courseName);
        courseValues.put("class_day", classDay);
        courseValues.put("credits", credits);
        courseValues.put("class_period", classPeriod);
        courseValues.put("start_date", startDate);
        db.insert("Courses", null, courseValues);
    }

    public void registerCourse(String username, String courseCode) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("course_code", courseCode);
        db.insert("RegisteredCourses", null, values);
        db.close();
    }

    public void unregisterCourse(String username, String courseCode) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("RegisteredCourses", "username = ? AND course_code = ?", new String[]{username, courseCode});
        db.close();
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteTables(db);
        createTables(db);
        insertInitialData(db);
    }

    public Cursor getUserInfo(String username) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("Users", null, "username = ?", new String[]{username}, null, null, null);
    }

    public Cursor getRegisteredCourses(String username) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT c.course_code, c.course_name, c.class_day, c.credits, c.class_period, c.start_date " +
                "FROM RegisteredCourses rc " +
                "JOIN Courses c ON rc.course_code = c.course_code " +
                "WHERE rc.username = ?", new String[]{username});
    }

    // Thêm hàm xóa dữ liệu người dùng
    public void deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Users", "username = ?", new String[]{username});
        db.delete("RegisteredCourses", "username = ?", new String[]{username});
        db.close();
    }

    // Thêm hàm xóa dữ liệu khóa học
    public void deleteCourse(String courseCode) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Courses", "course_code = ?", new String[]{courseCode});
        db.delete("RegisteredCourses", "course_code = ?", new String[]{courseCode});
        db.close();
    }

    // Thêm hàm xóa cơ sở dữ liệu
    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}
