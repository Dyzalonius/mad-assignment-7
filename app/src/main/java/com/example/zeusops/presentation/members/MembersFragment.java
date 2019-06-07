package com.example.zeusops.presentation.members;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Member;
import com.example.zeusops.presentation.member.MemberActivity;

import java.util.ArrayList;
import java.util.List;

public class MembersFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    private MembersViewModel membersViewModel;
    private MembersAdapter membersAdapter;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public static MembersFragment newInstance() {
        MembersFragment fragment = new MembersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        membersViewModel = new MembersViewModel(getContext());
        membersViewModel.members = new MutableLiveData<>();
        membersAdapter = new MembersAdapter(getContext(), new ArrayList<Member>());
        membersViewModel.members.observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> members) {
                membersAdapter.updateMembers(members);
            }
        });

        membersViewModel.getMembers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_members, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        membersAdapter = new MembersAdapter(getContext(), membersAdapter.members);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(membersAdapter);
        recyclerView.addOnItemTouchListener(this);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    membersViewModel.fetchMembers();
                    Member member = membersAdapter.members.get(adapterPosition);
                    Intent intent = new Intent(getContext(), MemberActivity.class);
                    intent.putExtra("member", member);
                    startActivityForResult(intent, 0);
                }
                return true;
            }
        });

        return root;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}