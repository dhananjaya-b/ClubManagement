package com.example.clubmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_events);
    }
    EventFragment eventFragment=new EventFragment();
    NotificationFragment notificationFragment =new NotificationFragment();
    MembersFragment membersFragment =new MembersFragment();
    ProfileFragment profileFragment=new ProfileFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, profileFragment)
                        .commit();
                return true;


            case R.id.action_events:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, eventFragment)
                        .commit();
                return true;
            case R.id.action_notification:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, notificationFragment)
                        .commit();
                return true;
        }
        return false;
    }
}