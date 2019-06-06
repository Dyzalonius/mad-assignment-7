package com.example.zeusops.presentation.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Event;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private Context context;
    public List<Event> events;

    public EventsAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.text.setText(event.getDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateEvents(List<Event> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }
}