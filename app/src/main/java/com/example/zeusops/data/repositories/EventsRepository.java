package com.example.zeusops.data.repositories;

import android.content.Context;

import com.example.zeusops.data.database.EventDao;
import com.example.zeusops.data.database.ZeusopsRoomDatabase;
import com.example.zeusops.data.models.Event;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventsRepository {

    private ZeusopsRoomDatabase database;
    private EventDao dao;
    private List<Event> events;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public EventsRepository(Context context) {
        database = ZeusopsRoomDatabase.getDatabase(context);
        dao = database.eventDao();
        fetchMembers();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void insertList(final List<Event> events) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Event event : events) {
                    dao.insert(event);
                }
            }
        });
    }

    public void clear() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.clear();
            }
        });
    }

    public void update(final Event event) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(event);
            }
        });
    }

    public void fetchMembers() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                events = dao.getAllEvents();
            }
        });
    }
}
