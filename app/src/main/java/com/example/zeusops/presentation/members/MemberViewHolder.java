package com.example.zeusops.presentation.members;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zeusops.R;

public class MemberViewHolder extends RecyclerView.ViewHolder {

    TextView text;
    View view;

    public MemberViewHolder(@NonNull View memberView) {
        super(memberView);

        text = memberView.findViewById(R.id.textViewName);

        view = memberView;
    }
}