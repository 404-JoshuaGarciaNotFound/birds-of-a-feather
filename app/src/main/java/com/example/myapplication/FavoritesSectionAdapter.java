package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;
import static com.example.myapplication.MainActivity.returnSP;
import static com.example.myapplication.MainActivity.userInfo;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
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

import java.util.List;
import java.util.Set;

public class FavoritesSectionAdapter extends RecyclerView.Adapter<FavoritesSectionAdapter.ViewHolder> {

    private final List<String> ListFavorites;
    public static String dat;
    public FavoritesSectionAdapter(List<String> favoriteStudents){
        super();
        this.ListFavorites = favoriteStudents;

    }
    @NonNull
    @Override
    public FavoritesSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_favorites_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesSectionAdapter.ViewHolder holder, int position) {
        holder.setFavorite(ListFavorites.get(position));
    }

    @Override
    public int getItemCount() {
        return this.ListFavorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView favoriteName;
        private final SharedPreferences sharep = returnSP();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.favoriteName = itemView.findViewById(R.id.favoriteName);
            //Able to unfavorite
            ImageButton favoritesStar = itemView.findViewById(R.id.favoriteStarFavoritesPage);
            SharedPreferences.Editor insertStudentFav =  returnSP().edit();
            Set<String> favoritesList =  returnSP().getStringSet("favorites", null);
            favoritesStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("ACTION", "Pressed on favorites button");
                    Log.d("INFO", dat);
                    if (favoritesStar.getTag() != null) {
                            if (favoritesStar.getTag().equals("ON")) {
                                favoritesStar.setBackgroundResource(R.drawable.ic_star_empty);
                                favoritesStar.setTag("OFF");
                                //Remove from favorites
                                Log.d("newL", String.valueOf(favoritesList));
                                favoritesList.remove(dat.split(" ")[0] + " " + dat.split(" ")[1] + " " + dat.split(" ")[2]);
                                //Log.d("newL", String.valueOf(favoritesList));
                                insertStudentFav.remove("favorites");
                                insertStudentFav.apply();
                                insertStudentFav.putStringSet("favorites", favoritesList);
                                insertStudentFav.apply();

                                Toast.makeText(favoritesStar.getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                            } else {
                                favoritesStar.setBackgroundResource(R.drawable.ic_star);
                                favoritesStar.setTag("ON");
                                Toast.makeText(favoritesStar.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                                favoritesList.add(dat.split(" ")[0] + " " + dat.split(" ")[1] + " " + dat.split(" ")[2]);
                                insertStudentFav.remove("favorites");
                                insertStudentFav.putStringSet("favorites", favoritesList);
                                insertStudentFav.apply();

                            }
                    }else{
                        favoritesStar.setBackgroundResource(R.drawable.ic_star);
                        favoritesStar.setTag("ON");
                        Log.d("prev", String.valueOf(favoritesList));
                    }
                }
            });
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Clicking favorites does nothing
        }
        public void setFavorite(String s){
            this.favoriteName.setText(s.split(" ")[1]);
            dat = s;
            ImageView im = itemView.findViewById(R.id.student_headshot);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap imageToDisplay = getBitmapFromURL(s.split(" ")[2]);
                    im.post(new Runnable() {
                        @Override
                        public void run() {
                            im.setImageBitmap(imageToDisplay);
                        }
                    });
                }
            }).start();
            ImageButton favoritesStar = itemView.findViewById(R.id.favoriteStarFavoritesPage);
            SharedPreferences.Editor insertStudentFav =  returnSP().edit();
            Set<String> favoritesList =  returnSP().getStringSet("favorites", null);
            if(favoritesList.contains(dat.split(" ")[0] + " " + dat.split(" ")[1] + " " + dat.split(" ")[2])){
                favoritesStar.setBackgroundResource(R.drawable.ic_star);
                favoritesStar.setTag("ON");
            }
        }
    }
}
