package com.example.zeusops.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.zeusops.data.models.Event;
import com.example.zeusops.data.models.Member;

@Database(entities = {Member.class, Event.class}, version = 5, exportSchema = false)
public abstract class ZeusopsRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "zeusops_database";
    public abstract MemberDao memberDao();
    public abstract EventDao eventDao();
    private static volatile ZeusopsRoomDatabase INSTANCE;

    public static ZeusopsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ZeusopsRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ZeusopsRoomDatabase.class, NAME_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
