package com.example.myapplication.student.db;

import static com.example.myapplication.CreateBuilderAlert.buildBuilder;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.CreateBuilderAlert;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class OptionsMenuControls {
    public static AlertDialog ListSessionsMenu;
    public static AlertDialog ListFavorites;
    public static AlertDialog ListFilters;

    public static void buildListSession(Context context, LayoutInflater inflator){
        CreateBuilderAlert.returningVals AD = buildBuilder(context, R.layout.listsessions,
                inflator , true, "Saved Sessions");
        ListSessionsMenu = AD.alertDiag;
    }
    public static void buildListFilters(Context context, LayoutInflater inflator){
        CreateBuilderAlert.returningVals ADc = buildBuilder(context, R.layout.filteroptions,
                inflator , true, "Filters");
        ListFilters = ADc.alertDiag;
    }
    public static void buildFavoritesSection(Context context, LayoutInflater inflator){
        CreateBuilderAlert.returningVals ADa = buildBuilder(context, R.layout.listfavorites,
                inflator , true, "Favorites");
         ListFavorites  = ADa.alertDiag;
    }

    public static Boolean showMenu(LayoutInflater inflater, Resources res, FloatingActionButton FavoritesTab, FloatingActionButton ListSesh, FloatingActionButton FilterOptions){
        FavoritesTab.animate().translationY(-res.getDimension(R.dimen._55));
        FavoritesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListFavorites.show();
            }
        });
        ListSesh.animate().translationY(-res.getDimension(R.dimen._105));
        ListSesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSessionsMenu.show();
            }
        });
        FilterOptions.animate().translationY(-res.getDimension(R.dimen._155));
        FilterOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListFilters.show();
            }
        });
        return true;
    }
    public static Boolean closeMenu(FloatingActionButton FavoritesTab, FloatingActionButton ListSesh, FloatingActionButton FilterOptions){
        FavoritesTab.animate().translationY(0);
        ListSesh.animate().translationY(0);
        FilterOptions.animate().translationY(0);
        return false;
    }



}
