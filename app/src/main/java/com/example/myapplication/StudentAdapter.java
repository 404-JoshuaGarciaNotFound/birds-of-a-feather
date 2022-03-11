package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final List<Student> listOfStudent;
    private final SharedPreferences sp;
    public StudentAdapter(SharedPreferences SP, List<Student> listOfStudent) {
        super();
         this.sp = SP;
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
    public int getItemCount() { return this.listOfStudent.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Student student;
        private final TextView personName;
        private final ImageView personIcon;
        private final ImageButton favoritesStar;
        private final TextView personMatchClasses;
        ViewHolder(View itemView) {
            super(itemView);
            this.personName = itemView.findViewById(R.id.student_firstname);
            this.personIcon = itemView.findViewById(R.id.student_headshot);
            this.personMatchClasses = itemView.findViewById(R.id.number_matches);
            this.favoritesStar = itemView.findViewById(R.id.favoriteStarDetailsPage);
            //This method is for adding favorite students
            favoritesStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton favoritesStar = view.findViewById(R.id.favoriteStarDetailsPage);
                    if(favoritesStar != null) {
                        favoritesStar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor insertStudentFav = sp.edit();
                                Set<String> favoritesList = sp.getStringSet("favorites", null);
                                if(favoritesList == null){
                                    Set<String> newSet = new HashSet<String>();
                                    newSet.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                    insertStudentFav.putStringSet("favorites", newSet);
                                    insertStudentFav.apply();
                                    favoritesStar.setBackgroundResource(R.drawable.ic_star);
                                }
                                else{
                                    if(!favoritesList.contains(student.getId() + student.getName()+ " " + student.getHeadShotURL())) {
                                        Log.d("CLICKED", "Inserting student");
                                        Log.d("List", String.valueOf(favoritesList));
                                        insertStudentFav.remove("favorites");
                                        favoritesList.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                        insertStudentFav.putStringSet("favorites", favoritesList);
                                        insertStudentFav.apply();
                                        Log.d("List", String.valueOf(favoritesList));
                                        favoritesStar.setBackgroundResource(R.drawable.ic_star);
                                    }
                                    else{
                                        Log.d("Error", "No douplicates in favorites");
                                    }
                                }
                                Log.d("CLICKED HEHEHE", "Completed");

                            }
                        });
                    }
                }
            });
            itemView.setOnClickListener(this);
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
            //This method updates the star shape if a student is a favorite.
            Set<String> favoritesList = sp.getStringSet("favorites", null);
            if(favoritesList != null){
                if(favoritesList.size() != 0) {
                    if (favoritesList.contains(this.student.getId() + this.student.getName())) {
                        favoritesStar.setBackgroundResource(R.drawable.ic_star);
                    }
                }
            }

        }

        @Override
        public void onClick(View view) {

            Context context = view.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra("student_id", this.student.getId());
            context.startActivity(intent);
        }


    }
}
