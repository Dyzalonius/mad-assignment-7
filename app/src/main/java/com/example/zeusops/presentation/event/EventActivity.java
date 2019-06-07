package com.example.zeusops.presentation.event;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Event;
import com.example.zeusops.data.models.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventActivity extends AppCompatActivity {

    private EventViewModel eventViewModel;
    private EventAdapter eventAdapter;
    private Event event;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
        setSupportActionBar((Toolbar)(findViewById(R.id.toolbar)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventViewModel = new EventViewModel(getApplicationContext());
        eventAdapter = new EventAdapter(getApplicationContext(), new ArrayList<Member>());
        event = (Event) getIntent().getSerializableExtra("event");
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventAdapter);
        toolbar.setTitle(event.getDate());

        eventViewModel.members.observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> members) {
                eventViewModel.getAttendees(event.getAttendees());
            }
        });
        eventViewModel.attendees.observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> members) {
                eventAdapter.updateMembers(eventViewModel.attendees.getValue());
            }
        });
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventViewModel.fetchMembers();
            }
        });
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
