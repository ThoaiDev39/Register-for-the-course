package com.example.demo_sanpham.model;

public class cls_Course {
    private String courseCode;
    private String courseName;
    private String classDay;
    private String classPeriod;
    private String startDate;
    private int credits; // Số tín chỉ
    private boolean isChecked; // Trạng thái đăng ký

    public cls_Course(String courseCode, String courseName, String classDay, String classPeriod, String startDate, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classDay = classDay;
        this.classPeriod = classPeriod;
        this.startDate = startDate;
        this.credits = credits;
        this.isChecked = false; // Mặc định là chưa đăng ký
    }

    // Getter và Setter cho các thuộc tính

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public String getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(String classPeriod) {
        this.classPeriod = classPeriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
