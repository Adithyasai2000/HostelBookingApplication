package com.example.innfystays;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class BedslistAdapter extends RecyclerView.Adapter<BedslistAdapter.BedsViewHolder>  {
    ArrayList<Bedsdetails> bedsdetailsArrayList;
    Context context;

    public BedslistAdapter(ArrayList<Bedsdetails> bedsdetailsArrayList, Context context) {
        this.bedsdetailsArrayList = bedsdetailsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BedslistAdapter.BedsViewHolder(LayoutInflater.from(context).inflate(R.layout.bedsrecyclerviewlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BedsViewHolder holder, int position) {
        if(bedsdetailsArrayList.get(position).getBedState().equals("true")){
            holder.imageButton.setBackgroundResource(R.drawable.ic_baseline_bed_242);
            holder.textView.setText(bedsdetailsArrayList.get(position).getBedId());
            holder.imageButton1.setBackgroundResource(R.drawable.ic_baseline_cancel_24);
            holder.textView1.setText("Filled");

        }
        else{
            holder.imageButton.setBackgroundResource(R.drawable.ic_baseline_bed_24);
            holder.textView.setText(bedsdetailsArrayList.get(position).getBedId());
            holder.imageButton1.setBackgroundResource(R.drawable.ic_baseline_check_circle_24);
            holder.textView1.setText("Available");
            final Integer[] cnt = {0};
            holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cnt[0]==0){
                    holder.imageButton1.setBackgroundResource(R.drawable.ic_baseline_check_circle_24);
                    holder.textView1.setText("Available");
                    cnt[0] =1;
                    }else {
                        holder.imageButton1.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                        holder.textView1.setText("Selected");
                        cnt[0]=0;

                    }


                }
            });

        }

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.imageButton.setBackground(ic_base);
                if(holder.imageButton.getBackground().equals(R.drawable.ic_baseline_bed_24)){
                    holder.imageButton.setBackgroundResource(R.drawable.ic_baseline_bed_241);
                }
                else if(holder.imageButton.getBackground().equals(R.drawable.ic_baseline_bed_241)) {

                    holder.imageButton.setBackgroundResource(R.drawable.ic_baseline_bed_24);
                }
                //Toast.makeText(context, "skdlvkds", Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setText(bedsdetailsArrayList.get(position).getBedId());

    }

    @Override
    public int getItemCount() {
        return bedsdetailsArrayList.size();
    }



    public class BedsViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton,imageButton1;
        TextView textView,textView1;
        public BedsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton=(ImageButton)itemView.findViewById(R.id.idbedimage);
            imageButton1=(ImageButton)itemView.findViewById(R.id.idselectedimagebtn);
            textView=itemView.findViewById(R.id.idbedtextid);
            textView1=itemView.findViewById(R.id.idtextimgbtn);
        }
    }
}
