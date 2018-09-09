package com.example.annah.cityguide.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.annah.cityguide.Adapter.TransportaionAdapter;
import com.example.annah.cityguide.R;
import com.example.annah.cityguide.TransportationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView nav_view;

    private ArrayList<TransportationItem> mTransportationList;
    private TransportaionAdapter mTransportationAdapter;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.home_page));
        initList();

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.drawer_open,R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view = findViewById(R.id.nav_bar);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.profile:
                        Toast.makeText(MainActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.circles:
                        Toast.makeText(MainActivity.this, "Circles clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.reminder:
                        Intent intent = new Intent(MainActivity.this,ReminderActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });

        Spinner spinnerTransportation = findViewById(R.id.spinner);
        mTransportationAdapter = new TransportaionAdapter(this,mTransportationList);
        spinnerTransportation.setAdapter(mTransportationAdapter);


        spinnerTransportation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                TransportationItem clickItem = (TransportationItem) adapterView.getItemAtPosition(position);
                String clickedTransportationName = clickItem.getNameOfTheTransportation();
                Toast.makeText(MainActivity.this, clickedTransportationName+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initList(){

        mTransportationList = new ArrayList<>();
        mTransportationList.add(new TransportationItem("Bus",R.drawable.bus_png));
        mTransportationList.add(new TransportationItem("Leguna",R.drawable.leguna));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
