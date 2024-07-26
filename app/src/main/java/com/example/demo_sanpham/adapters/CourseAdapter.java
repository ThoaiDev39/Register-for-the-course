package com.example.demo_sanpham.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_sanpham.R;
import com.example.demo_sanpham.model.cls_Course;
import com.example.demo_sanpham.cls_sqlite;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<cls_Course> courseList;
    private OnItemClickListener listener;
    private cls_sqlite dbHelper;
    private Context context;

    public interface OnItemClickListener {
        void onRegisterClick(int position, boolean isChecked);
    }

    public CourseAdapter(List<cls_Course> courseList, cls_sqlite dbHelper, Context context) {
        this.courseList = courseList;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourseCode, tvCourseName, tvClassDay, tvClassPeriod, tvStartDate, tvCredits;
        public Button btnRegister;

        public CourseViewHolder(View view) {
            super(view);
            tvCourseCode = view.findViewById(R.id.tvCourseCode);
            tvCourseName = view.findViewById(R.id.tvCourseName);
            tvClassDay = view.findViewById(R.id.tvClassDay);
            tvClassPeriod = view.findViewById(R.id.tvClassPeriod);
            tvStartDate = view.findViewById(R.id.tvStartDate);
            tvCredits = view.findViewById(R.id.tvCredits); // TextView cho số tín chỉ
            btnRegister = view.findViewById(R.id.btnRegister);
        }

        public void bind(final cls_Course course, final OnItemClickListener listener, final cls_sqlite dbHelper, final Context context) {
            tvCourseCode.setText("Mã học phần: " + course.getCourseCode());
            tvCourseName.setText("Tên học phần: " + course.getCourseName());
            tvClassDay.setText("Ngày: " + course.getClassDay());
            tvClassPeriod.setText("Tiết: " + course.getClassPeriod());
            tvStartDate.setText("Ngày bắt đầu: " + course.getStartDate());
            tvCredits.setText("Số tín chỉ: " + course.getCredits()); // Hiển thị số tín chỉ

            btnRegister.setText(course.isChecked() ? "Hủy đăng ký" : "Đăng ký");

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRegisterClick(position, !course.isChecked());
                        }
                    }
                }
            });
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        cls_Course course = courseList.get(position);
        holder.bind(course, listener, dbHelper, context);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
