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

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private final List<String> LSession;

    public SessionAdapter(List<String> LS){
        super();
        this.LSession = LS;

    }
    @NonNull
    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_session_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setSession(LSession.get(position));
    }

    @Override
    public int getItemCount() {
        return this.LSession.size();

    }
    public static class ViewHolder extends  RecyclerView.ViewHolder
            implements View.OnClickListener{
        private final TextView SessionName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.SessionName = itemView.findViewById(R.id.sessionName);
            itemView.setOnClickListener(this);
        }

        public void setSession(String s){
            Log.d("CLICKED", s);
            this.SessionName.setText(s);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, SessionDetailActivity.class);
            intent.putExtra("name", this.SessionName.getText());
            context.startActivity(intent);
        }
    }
}
