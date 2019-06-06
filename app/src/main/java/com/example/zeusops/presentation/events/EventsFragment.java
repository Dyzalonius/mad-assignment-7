package com.example.zeusops.presentation.events;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Event;
import com.example.zeusops.presentation.event.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    private EventsViewModel eventsViewModel;
    private EventsAdapter eventsAdapter;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsViewModel = new EventsViewModel(getContext());
        eventsViewModel.events = new MutableLiveData<>();
        eventsAdapter = new EventsAdapter(getContext(), new ArrayList<Event>());
        eventsViewModel.events.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                eventsAdapter.updateEvents(events);
            }
        });

        eventsViewModel.getEvents();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        eventsAdapter = new EventsAdapter(getContext(), eventsAdapter.events);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventsAdapter);
        recyclerView.addOnItemTouchListener(this);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    Event event = eventsAdapter.events.get(adapterPosition);
                    Intent intent = new Intent(getContext(), EventActivity.class);
                    intent.putExtra("event", event);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });

        return root;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}