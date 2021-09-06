package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomBedBookingActivity extends AppCompatActivity {

    private String Roomid,RoomAvailability,RoomCapacity,HostelId;
    private LinearLayout linearbedslayout;
    private RecyclerView bedslist;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private BedslistAdapter bedslistAdapter;
    private ArrayAdapter<Bedsdetails> arrayAdapter;
    private Button bookbutton;
    private ArrayList<Bedsdetails> bedsdetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_bed_booking);
        Assigning();
        //FunctionforRoomBeds();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bedsdetailsArrayList=new ArrayList<Bedsdetails>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Bedsdetails h=dataSnapshot.getValue(Bedsdetails.class);
                    bedsdetailsArrayList.add(h);
                   // Toast.makeText(RoomBedBookingActivity.this, h.getBedId().toString(), Toast.LENGTH_SHORT).show();
                }
                try {
                    bedslistAdapter = new BedslistAdapter(bedsdetailsArrayList, RoomBedBookingActivity.this);
                    // arrayAdapter=new ArrayAdapter<Bedsdetails>(RoomBedBookingActivity.this,android.R.layout.simple_list_item_multiple_choice,bedsdetailsArrayList);
                    bedslist.setAdapter(bedslistAdapter);
                }catch (Exception e){
                    Toast.makeText(RoomBedBookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        }});


    }







    private void Assigning() {
        //linearbedslayout=(LinearLayout)findViewById(R.id.idlinearlayoutbeds);
        bookbutton=findViewById(R.id.idbookbedbtn);
        bedslist=(RecyclerView) findViewById(R.id.idbedslist);
        bedslist.setLayoutManager(new LinearLayoutManager(RoomBedBookingActivity.this));
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Intent i=getIntent();
        Roomid=i.getStringExtra("roomid");
        RoomAvailability=i.getStringExtra("roomavailability");
        RoomCapacity=i.getStringExtra("roomcapacity");
        HostelId=i.getStringExtra("hostelid");
        String hostelownerid=i.getStringExtra("hostelowneride");
       // Toast.makeText(RoomBedBookingActivity.this, HostelId.toString(), Toast.LENGTH_SHORT).show();
        databaseReference=firebaseDatabase.getReference().child("Rooms").child(hostelownerid).child(HostelId).child("Bedsstate").child(Roomid);
  //      bedslist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
    }