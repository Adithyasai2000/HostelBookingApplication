package com.example.innfystays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    Context context;
    ArrayList<Roomdetails> rooms;
    ArrayList<SliderData> listroomimages;
    String hostelid,hstownerid;
    private OnRoomListener onRoomListener;
    private ImageSliderAdapter imageSliderAdapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    public RoomAdapter(Context context, ArrayList<Roomdetails> rooms, String hostelid,String hstownerid, OnRoomListener onRoomListener) {
        this.context = context;
        this.rooms = rooms;
        this.hostelid=hostelid;
        this.hstownerid=hstownerid;
        // this.roomsimages=roomsimages;

        this.onRoomListener=onRoomListener;
    }
    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomViewHolder(LayoutInflater.from(context).inflate(R.layout.roomrecyclerviewlayout,parent,false),onRoomListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        final RoomAdapter.RoomViewHolder holder1;
        holder1=holder;

        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference=firebaseDatabase.getReference().child("Rooms").child(hstownerid);
        databaseReference.child(hostelid).child("Roomsimages").child(rooms.get(position).getRoomId().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                listroomimages = new ArrayList<SliderData>();
                //li=new ArrayList<SliderObject>();
                for(DataSnapshot dataSnapshot:snapshot1.getChildren()){
                    SliderData p=dataSnapshot.getValue(SliderData.class);
                    // Toast.makeText(getContext(),p.getArrayList().size(),Toast.LENGTH_SHORT).show();
                    listroomimages.add(p);
                    // li.add(p);

                }
                imageSliderAdapter=new ImageSliderAdapter(context,listroomimages);
                holder1.imageslider.setSliderAdapter(imageSliderAdapter);
                holder1.imageslider.setAutoCycleDirection(View.LAYOUT_DIRECTION_LTR);
                holder1.imageslider.setScrollTimeInSec(3);
                holder1.imageslider.setAutoCycle(true);
                holder1.imageslider.startAutoCycle();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        //imageSliderAdapter=new ImageSliderAdapter(this.context,this.roomsimages.get(position).getArrayList());
        holder.roomid.setText(rooms.get(position).getRoomId());
        holder.roomavailability.setText(rooms.get(position).getRoomAvailabilty());


    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public  interface OnRoomListener{
        void onRoomclick(int position);
        boolean onRoomLongclick(int position);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        OnRoomListener onRoomListener;
        TextView roomid,roomnumber,roomavailability,roomcapacity,roomimage;
        SliderView imageslider;
        public RoomViewHolder(@NonNull View itemView, OnRoomListener onRoomListener) {
            super(itemView);
            roomid=itemView.findViewById(R.id.idtextviewroomid);
            roomavailability=itemView.findViewById(R.id.idtextViewavailability);
            imageslider=itemView.findViewById(R.id.slider);
            this.onRoomListener=onRoomListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRoomListener.onRoomclick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onRoomListener.onRoomLongclick(getAdapterPosition());
            return true;
        }
    }
}
