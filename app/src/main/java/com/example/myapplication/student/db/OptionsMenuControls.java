package com.example.myapplication.student.db;

import android.content.Context;
import android.content.res.Resources;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OptionsMenuControls {
    public static Boolean showMenu(Resources res, FloatingActionButton FavoritesTab, FloatingActionButton ListSesh, FloatingActionButton FilterOptions){
        //Adding UI controls soon
        FavoritesTab.animate().translationY(-res.getDimension(R.dimen._55));
        //Adding UI controls soon
        ListSesh.animate().translationY(-res.getDimension(R.dimen._105));
        //Adding UI controls soon
        FilterOptions.animate().translationY(-res.getDimension(R.dimen._155));

        return true;
    }
    public static Boolean closeMenu(FloatingActionButton FavoritesTab, FloatingActionButton ListSesh, FloatingActionButton FilterOptions){
        FavoritesTab.animate().translationY(0);
        ListSesh.animate().translationY(0);
        FilterOptions.animate().translationY(0);
        return false;
    }
}
