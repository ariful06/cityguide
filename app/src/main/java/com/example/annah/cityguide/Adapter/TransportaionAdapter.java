package com.example.annah.cityguide.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annah.cityguide.R;
import com.example.annah.cityguide.TransportationItem;

import java.util.ArrayList;

/**
 * Created by Anik on 4/9/2018.
 */

public class TransportaionAdapter extends ArrayAdapter<TransportationItem> {


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    public TransportaionAdapter(Context context, ArrayList<TransportationItem> transportationList){
        super(context,0,transportationList);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_rows,parent,false);
        }
        ImageView imageViewTransportation = convertView.findViewById(R.id.image_view_type);
        TextView textViewNameOfTheTransportation = convertView.findViewById(R.id.tv_bus);
        TransportationItem transportationItem = getItem(position);

      if (transportationItem != null){
          imageViewTransportation.setImageResource(transportationItem.getmImageOfTheTransportation());
          textViewNameOfTheTransportation.setText(transportationItem.getNameOfTheTransportation());

      }

        return convertView;
    }
}
