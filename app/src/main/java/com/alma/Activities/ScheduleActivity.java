package com.alma.Activities;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.alma.Fragments.ScheduleAddFragment;
import com.alma.Fragments.ScheduleAllFragment;
import com.alma.R;
import com.google.android.material.tabs.TabLayout;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, CalendarView.OnDateChangeListener, View.OnClickListener {

    private Fragment fragment;
    private TabLayout tabLayout;
    private Calendar calendar;
    private CalendarView calendarView;
    private TextView schedule_dateTitle;
    private String chosenDate;
    private long today;
    private int lastI,lastI1,lastI2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        findViewById(R.id.schedule_floatingActionButton).setOnClickListener(this);
        tabLayout = findViewById(R.id.schedule_tabbedLayout);
        tabLayout.addOnTabSelectedListener(this);
        schedule_dateTitle=findViewById(R.id.schedule_dateTitle);
        calendarView = findViewById(R.id.schedule_calendar);
        calendarView.setOnDateChangeListener(this);
        calendar = Calendar.getInstance();
        today=calendarView.getDate();

        lastI=calendar.get(Calendar.YEAR);
        lastI1=calendar.get(Calendar.MONTH);
        lastI2=calendar.get(Calendar.DAY_OF_MONTH);
        chosenDate = String.format("%d-%02d-%02d",lastI,(lastI1+1),lastI2);
        schedule_dateTitle.setText(chosenDate);
        changeTab(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_floatingActionButton:
                lastI=calendar.get(Calendar.YEAR);
                lastI1=calendar.get(Calendar.MONTH);
                lastI2=calendar.get(Calendar.DAY_OF_MONTH);
                calendarView.setDate(today);
                chosenDate = String.format("%d-%02d-%02d",lastI,(lastI1+1),lastI2);
                schedule_dateTitle.setText(chosenDate);
                changeTab(0);
                break;
         }
    }

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
        lastI=i;
        lastI1=i1;
        lastI2=i2;
        chosenDate = String.format("%d-%02d-%02d",lastI,(lastI1+1),lastI2);
        schedule_dateTitle.setText(chosenDate);
        changeTab(0);
    }

    @SuppressLint("WrongConstant")
    public void changeTab(int i){
        tabLayout.selectTab(tabLayout.getTabAt(i));
        switch (i){
            case 0:
                fragment = new ScheduleAllFragment();
                break;
            case 1:
                fragment=new ScheduleAddFragment();
                break;
        }
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_schedule, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public String getDate(){
        return chosenDate;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        fragment = null;
        changeTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }
}