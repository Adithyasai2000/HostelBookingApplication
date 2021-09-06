package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class total_hostel extends AppCompatActivity {

    private RecyclerView recyclerview;
    private DatabaseReference useref;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_hostel);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerview=findViewById(R.id.idrecyclerviewhostels);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Hostels");
        useref= FirebaseDatabase.getInstance().getReference().child("Hostels");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Hostelsdetails> options;
        options=new FirebaseRecyclerOptions.Builder<Hostelsdetails>().setQuery(useref,Hostelsdetails.class).build();

                FirebaseRecyclerAdapter<Hostelsdetails,Findhostelviewholder> adapter=new FirebaseRecyclerAdapter<Hostelsdetails, Findhostelviewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Findhostelviewholder holder, int position, @NonNull Hostelsdetails model) {
                        holder.hstName.setText(model.getHstName());
                        holder.hstAddress.setText(model.getHstAddress());
                        holder.hstDetails.setText(model.getHstDetails());
                        holder.hstRating.setNumStars(Integer.parseInt(model.getHstRating()));
                        holder.hstAvailability.setText(model.getHstAvailability());
                        //holder.hstImage.setImageURI(Uri.parse(model.getHstImage()));
                        Toast.makeText(total_hostel.this,"Here",Toast.LENGTH_LONG).show();
                    }

                    @NonNull
                    @Override
                    public Findhostelviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        Toast.makeText(total_hostel.this,"H",Toast.LENGTH_LONG).show();
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hostleslayoutcard,parent,false);
                        Findhostelviewholder viewHolder=new Findhostelviewholder(view);
                        return viewHolder;
                    }
                };
        recyclerview.setAdapter(adapter);
        adapter.startListening();

    }
    public static class Findhostelviewholder extends RecyclerView.ViewHolder{

        TextView hstName,hstAddress,hstAvailability,hstDetails;
        RatingBar hstRating;
        ImageView hstImage;

        public Findhostelviewholder(@NonNull View itemView) {
            super(itemView);
            hstName=itemView.findViewById(R.id.idhstname);
            hstAddress=itemView.findViewById(R.id.idhstaddress);
            hstDetails=itemView.findViewById(R.id.idhstdts);
            hstAvailability=itemView.findViewById(R.id.idhstavailability);
            hstRating=itemView.findViewById(R.id.idhstratingBar);
            hstImage=itemView.findViewById(R.id.idhstimg);


        }
    }
}