package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;
import static com.example.myapplication.MainActivity.returnSP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;

import java.nio.charset.StandardCharsets;
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
        private final ImageButton waveButton;

        ViewHolder(View itemView) {
            super(itemView);
            this.personName = itemView.findViewById(R.id.student_firstname);
            this.personIcon = itemView.findViewById(R.id.student_headshot);
            this.personMatchClasses = itemView.findViewById(R.id.number_matches);
            this.waveButton = itemView.findViewById(R.id.received_wave_button);
            favoritesStar = itemView.findViewById(R.id.favoriteStarDetailsPage);
            SharedPreferences.Editor insertStudentFav = returnSP().edit();
            Set<String> favoritesList = returnSP().getStringSet("favorites", null);
            //This method is for adding favorite students
            favoritesStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favoritesList == null) {
                        Log.d("Empty List", "No items");
                        Set<String> newSet = new HashSet<String>();
                        Toast.makeText(favoritesStar.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        newSet.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                        insertStudentFav.putStringSet("favorites", newSet);
                        insertStudentFav.apply();
                        favoritesStar.setTag("ON");
                        favoritesStar.setBackgroundResource(R.drawable.ic_star);
                    } else {
                        Log.d("Current", String.valueOf(favoritesList));
                        if(favoritesStar.getTag() == null){
                            favoritesStar.setTag("OFF");
                        }
                        if (favoritesStar.getTag() != null) {
                            if (favoritesStar.getTag().equals("ON")) {
                                favoritesStar.setBackgroundResource(R.drawable.ic_star_empty);
                                favoritesStar.setTag("OFF");
                                //Remove from favorites
                                Log.d("Before", String.valueOf(favoritesList));
                                favoritesList.remove(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                insertStudentFav.remove("favorites");
                                insertStudentFav.apply();
                                insertStudentFav.putStringSet("favorites", favoritesList);
                                insertStudentFav.apply();
                                Log.d("After", String.valueOf(favoritesList));
                                //insertStudentFav.remove("favorites");
                                Toast.makeText(favoritesStar.getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("HEy", "I ran");
                                favoritesStar.setBackgroundResource(R.drawable.ic_star);
                                favoritesStar.setTag("ON");
                                Toast.makeText(favoritesStar.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                                Log.d("1", String.valueOf(returnSP().getStringSet("favorites", null)));
                                Log.d("Info", student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                //If it works it works
                                favoritesList.remove(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                insertStudentFav.remove("favorites");
                                insertStudentFav.apply();
                                insertStudentFav.putStringSet("favorites", favoritesList);
                                insertStudentFav.apply();
                                favoritesList.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                                insertStudentFav.remove("favorites");
                                insertStudentFav.putStringSet("favorites", favoritesList);
                                insertStudentFav.apply();

                                Log.d("2", String.valueOf(returnSP().getStringSet("favorites", null)));


                            }
                        } else {
                            Log.d("Rammy", "I ran!");
                            favoritesStar.setBackgroundResource(R.drawable.ic_star);
                            favoritesStar.setTag("ON");
                            //save(favoritesStar, insertStudentFav, favoritesList );

                            Log.d("First click", String.valueOf(returnSP().getStringSet("favorites", null)));
                        }
                    }
                Log.d("CLICKED HEHEHE", "Completed");

                }
            });
            itemView.setOnClickListener(this);

        }
        public void save(ImageButton favoritesStar, SharedPreferences.Editor insertStudentFav, Set<String> favoritesList){
            favoritesList.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
            insertStudentFav.remove("favorites");
            insertStudentFav.putStringSet("favorites", favoritesList);
            insertStudentFav.apply();
            favoritesStar.setTag("ON");


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

            Log.d("isWaveReceived", String.valueOf(student.isWaveReceived()));
            this.personName.setText(student.getName());
            String matchClassesText = "Number of Shared Courses: " + String.valueOf(student.getNumSharedCourses());
            this.personMatchClasses.setText(matchClassesText);
            if(student.isWaveReceived()){
                waveButton.setVisibility(View.VISIBLE);
            }
            else{
                waveButton.setVisibility(View.INVISIBLE);
            }

            //This method updates the star shape if a student is a favorite.
            Set<String> favoritesList = sp.getStringSet("favorites", null);

            SharedPreferences.Editor insertStudentFav =  returnSP().edit();
            Set<String> favoritesList2 =  returnSP().getStringSet("favorites", null);

            if (favoritesList2 != null) {
                if (favoritesList2.size() != 0) {
                    if (favoritesList2.contains(this.student.getId() + " " + this.student.getName() + " " + this.student.getHeadShotURL() )) {
                        favoritesStar.setBackgroundResource(R.drawable.ic_star);
                        favoritesStar.setTag("ON");
                    }
                }
            }
        }

        @Override
        public void onClick(View view) {
            SharedPreferences userInfo = returnSP();
            String searchInfo = userInfo.getString("nearby_message", "");
            Message mMessage = new Message(searchInfo.getBytes(StandardCharsets.UTF_8));
            Context mainContext = MyApplication.getContext();
            Nearby.getMessagesClient(mainContext).unpublish(mMessage);

            Context context = view.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra("student_id", this.student.getId());
            context.startActivity(intent);
        }


    }
}
