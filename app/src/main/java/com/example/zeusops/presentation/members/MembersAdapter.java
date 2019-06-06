package com.example.zeusops.presentation.members;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Member;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    private Context context;
    public List<Member> members;

    public MembersAdapter(Context context, List<Member> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final Member member = members.get(position);
        holder.text.setText(String.format("%s %s", member.getRank(), member.getName()));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void updateMembers(List<Member> members) {
        this.members.clear();
        this.members.addAll(members);
        notifyDataSetChanged();
    }
}