package com.example.zeusops.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "event_table")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String date;
    private String attendees;

    public Event(Long id, String date, String attendees) {
        this.id = id;
        this.date = date;
        this.attendees = attendees;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAttendees() {
        return attendees;
    }
}
