package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class HostelRooms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String hostelid,hstownerid;
    private ArrayList<Roomdetails> listroom;
    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_rooms);
        Assinging();
        function();
    }
    private void Assinging() {
        recyclerView=findViewById(R.id.idhostelrooms);




        firebaseAuth= FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(HostelRooms.this));
        firebaseDatabase= FirebaseDatabase.getInstance();
        Intent i=getIntent();
        hostelid=i.getStringExtra("hstid");
        hstownerid=i.getStringExtra("hstownerid");
        databaseReference=firebaseDatabase.getReference().child("Rooms").child(hstownerid);



    }

    void function(){


        if(hostelid!=null) {




            databaseReference.child(hostelid).child("Roomsdetails").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    listroom = new ArrayList<Roomdetails>();



                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Roomdetails p = dataSnapshot1.getValue(Roomdetails.class);
                        //Toast.makeText(getContext(), p.getRoomImagesList().get(0).getImageId(), Toast.LENGTH_LONG).show();
                        listroom.add(p);
                    }

                    roomAdapter = new RoomAdapter(HostelRooms.this, listroom,hostelid,hstownerid, new RoomAdapter.OnRoomListener() {
                        @Override
                        public void onRoomclick(int position) {
                            String grpname = roomAdapter.rooms.get(position).getRoomId();
                            String availability=roomAdapter.rooms.get(position).getRoomAvailabilty();
                            String capacity=roomAdapter.rooms.get(position).getRoomCapacity();
                            //String hostelide=roomAdapter.rooms.get(position).getHostelId();
                            //Toast.makeText(getContext(), grpname, Toast.LENGTH_SHORT).show();
                           Intent l=new Intent(HostelRooms.this,RoomBedBookingActivity.class);
                            l.putExtra("roomid",grpname);
                            l.putExtra("roomavailability",availability);
                            l.putExtra("roomcapacity",capacity);
                            l.putExtra("hostelowneride",hstownerid);
                            l.putExtra("hostelid",hostelid);
                            startActivity(l);


                        }

                        @Override
                        public boolean onRoomLongclick(int position) {
                            return false;
                        }
                    });
                    recyclerView.setAdapter(roomAdapter);
                    //progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}