package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.Set;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {
    private final Set<String> listOfSessions;
    public SessionsAdapter(Set<String> ListOfSessions){
        super();
        this.listOfSessions = ListOfSessions;

    }
    @NonNull
    @Override
    public SessionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_session_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsAdapter.ViewHolder holder, int position) {
        Object[] b = listOfSessions.toArray();

        holder.setSession(b[position]);
    }

    @Override
    public int getItemCount() {
        return this.listOfSessions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView sessionDat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sessionDat = itemView.findViewById(R.id.sessionName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Context context = view.getContext();
            //Intent intent = new Intent(context, )
        }

        public void setSession(Object o) {
            this.sessionDat.setText(o.toString());
        }
    }

}
