package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.database.Course;

import java.util.List;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder>{

    private final List<Course> listOfCourse;

    @NonNull
    @Override
    public CourseViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.courses_row, parent, false);


        return new CourseViewAdapter.ViewHolder(view);
    }

    public CourseViewAdapter(List<Course> listOfCourse) {
        super();
        this.listOfCourse = listOfCourse;
    }

    @Override
    public int getItemCount() {
        return this.listOfCourse.size();
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter.ViewHolder holder, int position) {
        holder.setCourse(listOfCourse.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Course course;
        private final TextView courseDetail;


        public ViewHolder(View itemView) {
            super(itemView);
            this.courseDetail = itemView.findViewById(R.id.course_detail);
        }

        public void setCourse(Course course){
            this.course = course;
            String courseInfo = course.getQuarter() + course.getYear() + course.getCourseCode();
            this.courseDetail.setText(courseInfo);
        }
    }
}
