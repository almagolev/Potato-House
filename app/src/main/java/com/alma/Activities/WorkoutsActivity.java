package com.alma.Activities;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alma.Fragments.ClientInfoFragment;
import com.alma.Fragments.ClientsAllFragment;
import com.alma.Fragments.WorkoutsAddFragment;
import com.alma.Fragments.WorkoutsAllFragment;
import com.alma.R;
import com.google.android.material.tabs.TabLayout;

public class WorkoutsActivity extends AppCompatActivity {

    private Fragment fragment;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        tabLayout = findViewById(R.id.fw_tabbedLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment=null;
                changeTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
    @SuppressLint("WrongConstant")
    public void changeTab(int i){
        tabLayout.selectTab(tabLayout.getTabAt(i));
        switch (i){
            case 0:
                fragment = new WorkoutsAllFragment();
                break;
            case 1:
                fragment=new WorkoutsAddFragment();
                break;
        }
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_workouts, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}