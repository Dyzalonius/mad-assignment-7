package com.example.zeusops.presentation.member;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.zeusops.R;
import com.example.zeusops.data.models.Member;

import java.text.DecimalFormat;

public class MemberActivity extends AppCompatActivity {

    private TextView attendance, country;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
        setContentView(R.layout.activity_member);
        setSupportActionBar((Toolbar)(findViewById(R.id.toolbar)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar = findViewById(R.id.toolbar);
        attendance = findViewById(R.id.textViewAttendance);
        country = findViewById(R.id.textViewCountry);

        Member member = (Member) getIntent().getSerializableExtra("member");
        toolbar.setTitle(member.getRank() + " " + member.getName());
        DecimalFormat df = new DecimalFormat("##.00");
        attendance.setText(String.format("%s%%", df.format(member.getAttendance() * 100.0)));
        country.setText(member.getCountry());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right);
    }
}