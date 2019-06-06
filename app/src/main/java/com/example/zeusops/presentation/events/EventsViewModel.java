package com.example.zeusops.presentation.events;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.zeusops.data.models.Event;
import com.example.zeusops.data.repositories.EventsRepository;
import com.example.zeusops.data.datasource.AsyncResult;
import com.example.zeusops.data.datasource.DownloadWebpageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsViewModel extends ViewModel {

    public MutableLiveData<List<Event>> events;
    private EventsRepository eventsRepository;

    public EventsViewModel(Context context) {
        eventsRepository = new EventsRepository(context);
    }

    public void getEvents() {
        // Fetch ROOM data
        List<Event> events = eventsRepository.getEvents();

        // If ROOM is empty, fetch from Google Sheets
        if (events != null && events.size() > 0) {
            this.events.postValue(events);
        } else {
            fetchJson();
        }
    }

    private void fetchJson() {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1ubRqLvX4l2G1TiwIrnuWtjLvWOqfpTqs3ElwvqEBcHM");
    }

    private void processJson(JSONObject object) {
        eventsRepository.clear();

        try {
            List<Event> events = new ArrayList<>();
            JSONArray columns = object.getJSONArray("cols");
            JSONArray rows = object.getJSONArray("rows");
            JSONArray labels = rows.getJSONObject(0).getJSONArray("c");

            for (int c = 19; c < columns.length(); c++) {
                String date = labels.getJSONObject(c).getString("v");
                String attendees = "";

                for(int i = 1; i<rows.length(); i++) {
                    JSONArray row = rows.getJSONObject(i).getJSONArray("c");
                    if (row.get(c) instanceof JSONObject) {
                        switch (row.getJSONObject(c).getString("v")) {
                            case "SIGNED OFF": { }
                            case "ABSENT": { }
                            default: { attendees += i + " "; }
                        }
                    }
                }

                Event newEvent = new Event(null, date, attendees);

                events.add(newEvent);
            }
            eventsRepository.insertList(events);
            this.events.postValue(events);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}