package com.example.zeusops.presentation.event;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Event;
import com.example.zeusops.data.models.Member;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private EventViewModel eventViewModel;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    private TextView attendance;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
        setSupportActionBar((Toolbar)(findViewById(R.id.toolbar)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventViewModel = new EventViewModel(getApplicationContext());
        eventViewModel.members = new MutableLiveData<>();
        eventAdapter = new EventAdapter(getApplicationContext(), new ArrayList<Member>());
        eventViewModel.members.observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> members) {
                System.out.println("attendees: " + members);
                eventAdapter.updateMembers(members);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        attendance = findViewById(R.id.textViewAttendance);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventAdapter);

        Event event = (Event) getIntent().getSerializableExtra("event");
        toolbar.setTitle(event.getDate());
        eventViewModel.getAttendees(event.getAttendees());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right);
    }
}
