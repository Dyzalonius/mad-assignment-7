package com.example.zeusops.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.zeusops.data.models.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Delete
    void delete(List<Event> events);

    @Update
    void update(Event event);

    @Query("DELETE FROM event_table")
    void clear();

    @Query("SELECT * from event_table")
    List<Event> getAllEvents();
}
