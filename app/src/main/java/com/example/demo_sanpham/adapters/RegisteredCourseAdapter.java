package com.example.demo_sanpham.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_sanpham.R;
import com.example.demo_sanpham.model.cls_RegisteredCourse;

import java.util.List;

public class RegisteredCourseAdapter extends RecyclerView.Adapter<RegisteredCourseAdapter.ViewHolder> {

    private List<cls_RegisteredCourse> registeredCourses;

    public RegisteredCourseAdapter(List<cls_RegisteredCourse> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cls_RegisteredCourse course = registeredCourses.get(position);
        holder.tvCourseName.setText("Tên học phần: " + course.getCourseName());
        holder.tvCourseCode.setText("Mã học phần: " + course.getCourseCode());
        holder.tvClassDay.setText("Ngày: " + course.getClassDay());
        holder.tvClassPeriod.setText("Tiết: " + course.getClassPeriod());
        holder.tvStartDate.setText("Ngày bắt đầu: " + course.getStartDate());
        holder.tvCredits.setText("Số tín chỉ: " + course.getCredits()); // Hiển thị số tín chỉ
    }

    @Override
    public int getItemCount() {
        return registeredCourses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvCourseCode, tvClassDay, tvClassPeriod, tvStartDate, tvCredits;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseCode = itemView.findViewById(R.id.tvCourseCode);
            tvClassDay = itemView.findViewById(R.id.tvClassDay);
            tvClassPeriod = itemView.findViewById(R.id.tvClassPeriod);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvCredits = itemView.findViewById(R.id.tvCreditsRC); // Thêm TextView cho số tín chỉ
        }
    }
}
