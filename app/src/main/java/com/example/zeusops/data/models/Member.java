package com.example.zeusops.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "member_table")
public class Member implements Serializable {

    @PrimaryKey()
    private int id;

    private String name;
    private String rank;
    private String country;
    private double attendance;
//    private int memberYears;

    public Member(int id, String name, String rank, String country, double attendance) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.country = country;
        this.attendance = attendance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getCountry() {
        return country;
    }

    public double getAttendance() {
        return attendance;
    }
}
