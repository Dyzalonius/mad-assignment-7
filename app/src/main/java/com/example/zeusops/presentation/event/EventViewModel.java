package com.example.zeusops.presentation.event;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.zeusops.data.models.Member;
import com.example.zeusops.data.repositories.MembersRepository;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {

    public MutableLiveData<List<Member>> attendees;
    public MutableLiveData<List<Member>> members;
    private MembersRepository membersRepository;

    public EventViewModel(Context context) {
        membersRepository = new MembersRepository(context);
        members = new MutableLiveData<>();
        attendees = new MutableLiveData<>();
    }

    public void fetchMembers() {
        members.postValue(membersRepository.fetchMembersSync());
    }

    public void getAttendees(String attendeesString) {
        List<Member> attendees = new ArrayList<>();

        for (String attendee: attendeesString.split(" ")) {
            Member member = membersRepository.findMember(Integer.parseInt(attendee));
            if (member != null) {
                attendees.add(member);
            }
        }
        this.attendees.postValue(attendees);
    }
}
