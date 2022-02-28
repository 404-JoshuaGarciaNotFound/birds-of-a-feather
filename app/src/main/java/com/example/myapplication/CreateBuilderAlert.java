package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import org.xmlpull.v1.XmlPullParser;

public class CreateBuilderAlert {
    //this method is what allows the main method to read the values stored
    public static class returningVals {
        public AlertDialog alertDiag;
        public View viewy;
    }
    //This method builds the alert window popup.
    public static returningVals buildBuilder(Context context, int view1, LayoutInflater inflater, Boolean cancellable, String title){
        AlertDialog.Builder toReturn = new AlertDialog.Builder(context);
        View viewCurrOn = inflater.inflate(view1, null);
        toReturn.setView(viewCurrOn);
        toReturn.setCancelable(cancellable);
        AlertDialog saveSesh = toReturn.create();
        saveSesh.setTitle(title);
        returningVals Cb = new returningVals();
        Cb.alertDiag = saveSesh;
        Cb.viewy = viewCurrOn;

        return Cb;
    }
}
