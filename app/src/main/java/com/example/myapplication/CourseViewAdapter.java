package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.Course;

import java.util.List;


//ViewAdapter that list the shared courses in StudentDetailActivity
public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder>{

    private final List<Course> listOfCourse;


    //Connect with course_row layout
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


        //connect with layout
        public ViewHolder(View itemView) {
            super(itemView);
            this.courseDetail = itemView.findViewById(R.id.course_detail);
        }

        //Set course format on ui
        public void setCourse(Course course){
            this.course = course;
            String[] courseCodeInfo = course.getCourseCode().split(",");
            String courseInfo = course.getYear() + " " + course.getQuarter() + " " + courseCodeInfo[0] + " " + courseCodeInfo[1];
            this.courseDetail.setText(courseInfo);
        }
    }
}
