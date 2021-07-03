package com.alma.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.alma.Fragments.ClientInfoFragment;
import com.alma.Fragments.ClientsAllFragment;
import com.alma.Fragments.ClientsAllNarrowFragment;
import com.alma.Fragments.ScheduleAddFragment;
import com.alma.Fragments.ScheduleAllFragment;
import com.alma.R;
import com.google.android.material.tabs.TabLayout;

public class ClientsActivity extends AppCompatActivity {

    private Fragment fragment;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        tabLayout = findViewById(R.id.fclients_tabbedLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });
        changeTab(0);
    }

    @SuppressLint("WrongConstant")
    public void changeTab(int i){
        tabLayout.selectTab(tabLayout.getTabAt(i));
        switch (i){
            case 0:
                fragment=new ClientsAllNarrowFragment();
                break;
            case 1:
                fragment = new ClientsAllFragment();
                break;
            case 2:
                fragment=new ClientInfoFragment();
                break;
        }
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_clients, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}