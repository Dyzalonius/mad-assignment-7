package com.example.zeusops.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.zeusops.data.models.Member;

import java.util.List;

@Dao
public interface MemberDao {
    @Insert
    void insert(Member member);

    @Delete
    void delete(Member member);

    @Delete
    void delete(List<Member> members);

    @Update
    void update(Member member);

    @Query("DELETE FROM member_table")
    void clear();

    @Query("SELECT * from member_table")
    List<Member> getAllMembers();
}
