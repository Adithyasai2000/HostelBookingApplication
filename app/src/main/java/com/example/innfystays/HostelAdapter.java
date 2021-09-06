package com.example.innfystays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> implements Filterable {

    Context context;
    ArrayList<Hostelsdetails> hostels;
    ArrayList<Hostelsdetails> filteredhostels;
    private OnGroupListener mOnGroupListener;


    public HostelAdapter(Context c, ArrayList<Hostelsdetails> p, OnGroupListener onGroupListener){
        context=c;
        filteredhostels=p;
        hostels=p;
        this.mOnGroupListener=onGroupListener;
    }

    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HostelViewHolder(LayoutInflater.from(context).inflate(R.layout.hostleslayoutcard,parent,false),mOnGroupListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelAdapter.HostelViewHolder holder, int position) {

        holder.hstName.setText(hostels.get(position).getHstName());
        holder.hstAddress.setText(hostels.get(position).getHstAddress());
        holder.hstDetails.setText(hostels.get(position).getHstDetails());
        holder.hstRating.setRating(Float.parseFloat(hostels.get(position).getHstRating()));
        holder.hstAvailability.setText(hostels.get(position).getHstAvailability());
        //holder.hstImage.setImageURI(Uri.parse(model.getHstImage()));
//        Toast.makeText(context,position,Toast.LENGTH_LONG).show();


    }

    @Override
    public int getItemCount() {
            return hostels.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()){
                   hostels=filteredhostels;

                }
                else {
                    ArrayList<Hostelsdetails> filteredList = new ArrayList<>();
                    for (Hostelsdetails androidVersion : filteredhostels) {

                        if (androidVersion.getHstAddress().toLowerCase().contains(charString) || androidVersion.getHstName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }
                    hostels = filteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =hostels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hostels = (ArrayList<Hostelsdetails>) filterResults.values;

                notifyDataSetChanged();

            }
        };
    }

    public interface OnGroupListener{
        void onGroupclick(int position);
        boolean onLongGroupclick(int position);
    }


    public class HostelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView hstName,hstAddress,hstAvailability,hstDetails;
        RatingBar hstRating;
        ImageView hstImage;
        OnGroupListener onGroupListener;
        public HostelViewHolder(@NonNull View itemView,OnGroupListener onGroupListener) {
            super(itemView);
            hstName=itemView.findViewById(R.id.idhstname);
            hstAddress=itemView.findViewById(R.id.idhstaddress);
            hstDetails=itemView.findViewById(R.id.idhstdts);
            hstAvailability=itemView.findViewById(R.id.idhstavailability);
            hstRating=itemView.findViewById(R.id.idhstratingBar);
            hstImage=itemView.findViewById(R.id.idhstimg);
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
}
