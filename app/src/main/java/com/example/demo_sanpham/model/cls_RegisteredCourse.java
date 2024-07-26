package com.example.demo_sanpham.model;

public class cls_RegisteredCourse {
    private String username;
    private String courseCode;
    private String courseName;
    private String classDay;
    private String classPeriod;
    private String startDate;
    private String credits; // Đảm bảo kiểu String

    public cls_RegisteredCourse(String username, String courseCode, String courseName, String classDay, String classPeriod, String startDate, String credits) {
        this.username = username;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classDay = classDay;
        this.classPeriod = classPeriod;
        this.startDate = startDate;
        this.credits = credits; // Khởi tạo số tín chỉ
    }

    // Getter và Setter cho số tín chỉ
    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    // Các getter và setter khác
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getClassDay() { return classDay; }
    public void setClassDay(String classDay) { this.classDay = classDay; }
    public String getClassPeriod() { return classPeriod; }
    public void setClassPeriod(String classPeriod) { this.classPeriod = classPeriod; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
}
