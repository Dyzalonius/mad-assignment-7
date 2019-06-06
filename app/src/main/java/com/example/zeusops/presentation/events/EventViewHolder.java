package com.example.zeusops.presentation.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zeusops.R;

public class EventViewHolder extends RecyclerView.ViewHolder {

    TextView text;
    View view;

    public EventViewHolder(@NonNull View eventView) {
        super(eventView);
        text = eventView.findViewById(R.id.textViewDate);
        view = eventView;
    }
}