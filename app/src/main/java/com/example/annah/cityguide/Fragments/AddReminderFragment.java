package com.example.annah.cityguide.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.annah.cityguide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddReminderFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    Context context;


    View view;
    SupportMapFragment supportMapFragment;

    public AddReminderFragment(){

    }
    @Override
    public void onCreate(Bundle bundle) {super.onCreate(bundle);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_reminder, container, false);
        if (supportMapFragment != null){
            supportMapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);

        }
       return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng UCA = new LatLng(-34,  151);
        googleMap.addMarker(new MarkerOptions().position(UCA).title("YOUR TITLE")).showInfoWindow();
        CameraPosition dhaka = CameraPosition.builder().target(UCA).zoom(16).bearing(0).tilt(45).build();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
