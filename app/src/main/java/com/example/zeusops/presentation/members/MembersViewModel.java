package com.example.zeusops.presentation.members;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.zeusops.data.datasource.AsyncResult;
import com.example.zeusops.data.datasource.DownloadWebpageTask;
import com.example.zeusops.data.models.Member;
import com.example.zeusops.data.repositories.MembersRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MembersViewModel extends ViewModel {

    public MutableLiveData<List<Member>> members;
    private MembersRepository membersRepository;

    public MembersViewModel(Context context) {
        membersRepository = new MembersRepository(context);
        membersRepository.fetchMembers();
    }

    public void getMembers() {
        // Fetch ROOM data
        List<Member> members = membersRepository.getMembers();

        // If ROOM is empty, fetch from Google Sheets
        if (members != null && members.size() > 0) {
            this.members.postValue(members);
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
        membersRepository.clear();

        try {
            JSONArray rows = object.getJSONArray("rows");
            List<Member> members = new ArrayList<>();

            for (int r = 1; r < rows.length(); r++) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String rank = columns.getJSONObject(0).getString("v");
                String name = columns.getJSONObject(1).getString("v");
//                String membershipStart = columns.getJSONObject(2).getString("v");
//                String membershipLength = columns.getJSONObject(3).getString("v");
                String country = columns.getJSONObject(4).getString("v");
                double attendance = Double.parseDouble(columns.getJSONObject(7).getString("v"));
                Member newMember = new Member(r, name, rank, country, attendance);
                members.add(newMember);
                membersRepository.insert(newMember);
            }
            this.members.postValue(members);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchMembers() {
        membersRepository.fetchMembers();
    }
}