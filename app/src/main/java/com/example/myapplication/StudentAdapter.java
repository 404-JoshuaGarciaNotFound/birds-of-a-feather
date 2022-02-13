package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final List<Student> listOfStudent;

    public StudentAdapter(List<Student> listOfStudent) {
        super();
        this.listOfStudent = listOfStudent;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_student_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setStudent(listOfStudent.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listOfStudent.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Student student;
        private final TextView personName;
        private final ImageView personIcon;
        private final TextView personMatchClasses;
        ViewHolder(View itemView) {
            super(itemView);
            this.personName = itemView.findViewById(R.id.student_firstname);
            this.personIcon = itemView.findViewById(R.id.student_headshot);
            this.personMatchClasses = itemView.findViewById(R.id.number_matches);
        }


        public void setStudent(Student student) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            this.student = student;
            Log.d("name of student being processed ", student.getName());

            //This thread is for processing image connection in the background.
            ImageView im = itemView.findViewById(R.id.student_headshot);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap imageToDisplay = getBitmapFromURL(student.getHeadShotURL());
                    im.post(new Runnable() {
                        @Override
                        public void run() {
                            im.setImageBitmap(imageToDisplay);
                        }
                    });
                }
            }).start();


            this.personName.setText(student.getName());
            this.personMatchClasses.setText(String.valueOf(student.getNumSharedCourses()));
        }
    }
}
