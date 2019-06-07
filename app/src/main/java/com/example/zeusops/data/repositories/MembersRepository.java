package com.example.zeusops.data.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.zeusops.data.database.MemberDao;
import com.example.zeusops.data.database.ZeusopsRoomDatabase;
import com.example.zeusops.data.models.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MembersRepository {

    private ZeusopsRoomDatabase database;
    private MemberDao dao;
    private List<Member> members;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public MembersRepository(Context context) {
        database = ZeusopsRoomDatabase.getDatabase(context);
        dao = database.memberDao();
        members = new ArrayList<>();
    }

    public List<Member> getMembers() {
        return members;
    }

    public void insert(final Member member) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(member);
            }
        });
    }

    public void insertList(final List<Member> members) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Member member : members) {
                    dao.insert(member);
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

    public void update(final Member member) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(member);
            }
        });
    }

    public void fetchMembers() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                members = dao.getAllMembers();
            }
        });
    }

    public List<Member> fetchMembersSync() {
        List<Member> members = dao.getAllMembers();
        this.members = members;
        return members;
    }

    public Member findMember(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }
}
