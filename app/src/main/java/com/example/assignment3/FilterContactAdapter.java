package com.example.assignment3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FilterContactAdapter extends ArrayAdapter<Contact> {


    public FilterContactAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.single_contact_design, parent, false
            );
        }


        TextView tvName, tvPhone;
        ImageView ivCall;
        tvName = v.findViewById(R.id.tvName);
        tvPhone = v.findViewById(R.id.tvPhone);
        ivCall = v.findViewById(R.id.ivCall);

        Contact contact = getItem(position);

        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
