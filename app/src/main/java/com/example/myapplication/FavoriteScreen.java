package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FavoriteScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.listfavorites);
        super.onCreate(savedInstanceState);
        //Back button on screen
        FloatingActionButton returnBack = findViewById(R.id.returnToHomeFromFavorites);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        ArrayList<String> keys = extras.getStringArrayList("ListFav");
        if(keys.size() != 0) {
            Log.d("Extras", keys.get(0));
            RecyclerView rv = (RecyclerView) findViewById(R.id.List_Of_Favorites);
            RecyclerView.LayoutManager RVLM = new LinearLayoutManager(this);
            rv.setLayoutManager(RVLM);
            FavoritesSectionAdapter SA = new FavoritesSectionAdapter(keys);
            rv.setAdapter(SA);
            rv.setVisibility(View.VISIBLE);
        }
    }
}
