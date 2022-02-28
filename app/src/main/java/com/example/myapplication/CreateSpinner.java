package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateSpinner {
    public static Spinner newSpinnerSpinny(Context context, View view, int selectYearID, int YEARID, int SimpleSpinnerID, int LAYOUT, int previousYearInd){
            Spinner spinnySpiner = (Spinner) view.findViewById(selectYearID);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context,
                    YEARID, SimpleSpinnerID);
            adapter1.setDropDownViewResource(LAYOUT);
            spinnySpiner.setAdapter(adapter1);
            spinnySpiner.setSelection(previousYearInd);
        return spinnySpiner;
    }
    public static Spinner newSpinnerSpinny(Context context, View view, int selectYearID, int YEARID, int SimpleSpinnerID, int LAYOUT){
        Spinner spinnySpiner = (Spinner) view.findViewById(selectYearID);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context,
                YEARID, SimpleSpinnerID);
        adapter1.setDropDownViewResource(LAYOUT);
        spinnySpiner.setAdapter(adapter1);
        return spinnySpiner;
    }
}
