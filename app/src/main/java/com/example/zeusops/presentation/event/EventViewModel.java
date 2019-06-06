package com.example.zeusops.presentation.event;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.zeusops.data.models.Member;
import com.example.zeusops.data.repositories.MembersRepository;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {

    public MutableLiveData<List<Member>> members;
    private MembersRepository membersRepository;

    public EventViewModel(Context context) {
        membersRepository = new MembersRepository(context);
    }

    public void getAttendees(String attendeesString) {
        List<Integer> attendeeIds = new ArrayList<>();
        for (String attendee: attendeesString.split(" ")) {
            attendeeIds.add(Integer.parseInt(attendee));
        }
        System.out.println("attendees ids: " + attendeeIds.size());
        List<Member> attendees = new ArrayList<>();
        for (int id : attendeeIds) {
            Member member = membersRepository.findMember(id);
            System.out.println(member);
            if (member != null) {
                attendees.add(member);
            }
        }
        this.members.postValue(attendees);
    }
}
