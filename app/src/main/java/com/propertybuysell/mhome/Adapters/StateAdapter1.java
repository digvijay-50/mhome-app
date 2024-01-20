package com.propertybuysell.mhome.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.propertybuysell.mhome.ModelClasses.GetState_Inner;
import com.propertybuysell.mhome.R;

import java.util.ArrayList;
import java.util.List;


public class StateAdapter1 extends RecyclerView.Adapter<StateAdapter1.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<GetState_Inner> contactList;
    private List<GetState_Inner> contactListFiltered;
    private ContactsAdapterListener listener;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;
 
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);
 
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
 
 
    public StateAdapter1(Context context, List<GetState_Inner> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GetState_Inner contact = contactListFiltered.get(position);
        holder.name.setText(contact.getStateName());
        holder.phone.setVisibility(View.GONE);
        //holder.phone.setText(contact.getPhone());
 

    }
 
    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }
 
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<GetState_Inner> filteredList = new ArrayList<>();
                    for (GetState_Inner row : contactList) {
 
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getStateName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
 
                    contactListFiltered = filteredList;
                }
 
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }
 
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<GetState_Inner>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
 
    public interface ContactsAdapterListener {
        void onContactSelected(GetState_Inner contact);
    }
}