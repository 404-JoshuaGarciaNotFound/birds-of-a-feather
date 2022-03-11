package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesSectionAdapter extends RecyclerView.Adapter<FavoritesSectionAdapter.ViewHolder> {

    private final List<String> ListFavorites;
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.favoriteName = itemView.findViewById(R.id.favoriteName);

        }

        @Override
        public void onClick(View view) {
            //Clicking favorites does nothing
        }
        public void setFavorite(String s){
            this.favoriteName.setText(s.split(" ")[1]);
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
        }
    }
}
