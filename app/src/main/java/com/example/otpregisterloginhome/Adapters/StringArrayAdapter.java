package com.example.otpregisterloginhome.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.otpregisterloginhome.R;


import java.util.List;

public class StringArrayAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<String> categoryListModelArrayList;

    private final int mResource;


    public StringArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List categoryListModelArrayList) {
        super(context, resource, categoryListModelArrayList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.categoryListModelArrayList=categoryListModelArrayList;


    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(R.layout.single_spinnerlist, parent, false);

        TextView offTypeTv = (TextView) view.findViewById(R.id.tv_catname);


        offTypeTv.setText(categoryListModelArrayList.get(position));
        return view;
    }
}