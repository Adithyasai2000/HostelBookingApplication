package com.example.innfystays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {

    Context context;
    ArrayList<Locationdetails> location;
    ArrayList<Locationdetails> filteredlocation;
    private LocationAdapter.OnGroupListener mOnGroupListener;

    public LocationAdapter(Context context, ArrayList<Locationdetails> location, OnGroupListener mOnGroupListener) {
        this.context = context;
        this.location = location;
        this.filteredlocation = location;
        this.mOnGroupListener = mOnGroupListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    location=filteredlocation;

                }
                else {
                    ArrayList<Locationdetails> filteredList = new ArrayList<>();
                    for (Locationdetails androidVersion : filteredlocation) {

                        if (androidVersion.getLocationName().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }
                    location = filteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =location;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                location = (ArrayList<Locationdetails>) filterResults.values;

                notifyDataSetChanged();

            }
        };
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationAdapter.LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.locationlayoutcard,parent,false), mOnGroupListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        holder.locationName.setText(location.get(position).getLocationName());

    }

    @Override
    public int getItemCount() {
        return location.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView locationName;
        LocationAdapter.OnGroupListener onGroupListener;
        public LocationViewHolder(@NonNull View itemView, LocationAdapter.OnGroupListener onGroupListener) {
            super(itemView);
            locationName=itemView.findViewById(R.id.idlocationname);
            this.onGroupListener=onGroupListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGroupListener.onGroupclick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            onGroupListener.onLongGroupclick(getAdapterPosition());
            return true;
        }
    }
    public interface OnGroupListener{
        void onGroupclick(int position);
        boolean onLongGroupclick(int position);
    }
}
