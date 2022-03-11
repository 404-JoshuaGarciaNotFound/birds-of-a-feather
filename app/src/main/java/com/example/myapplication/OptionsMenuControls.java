package com.example.myapplication;

import static com.example.myapplication.CreateBuilderAlert.buildBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CreateBuilderAlert;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Set;

public class OptionsMenuControls {
    public static Context contexty;


    public static Boolean showMenu(SharedPreferences sp, LayoutInflater inflater, Resources res, FloatingActionButton FavoritesTab, FloatingActionButton ListSesh){
        FavoritesTab.animate().translationY(-res.getDimension(R.dimen._55));
        ListSesh.animate().translationY(-res.getDimension(R.dimen._105));

        return true;
    }
    public static Boolean closeMenu(FloatingActionButton FavoritesTab, FloatingActionButton ListSesh){
        FavoritesTab.animate().translationY(0);
        ListSesh.animate().translationY(0);
        return false;
    }



}
