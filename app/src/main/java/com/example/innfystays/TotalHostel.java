  package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TotalHostel extends AppCompatActivity {

    private RecyclerView recyclerView,locationRecyclerView;
    private DatabaseReference reference,reference1;
    private ArrayList<Hostelsdetails> list;
    private ArrayList<Locationdetails> list1;
    private HostelAdapter adapter;
    private LocationAdapter locationAdapter;
    private SearchView searchView;
    private View bi;
    public String address;
    private SearchView locationedittext;
    private TextView yourlocation,noservices;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private Location location;
    private AppLocationService appLocationService;
    private ProgressDialog progressDialog;
    Integer cnt=0;



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_hostel2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        appLocationService = new AppLocationService(
                TotalHostel.this);
        location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        LayoutInflater inflater = getLayoutInflater();

        bi= inflater.inflate(R.layout.locationdialoglayout,null);
        idmanaging();
        progressDialog.show();



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<Hostelsdetails>();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    Hostelsdetails p=dataSnapshot1.getValue(Hostelsdetails.class);
                    list.add(p);

                }

                adapter=new HostelAdapter(TotalHostel.this,list, new HostelAdapter.OnGroupListener() {
                    @Override
                    public void onGroupclick(int position) {
                        String grpname=adapter.hostels.get(position).getHstOwnerId();
                        String hstid=adapter.hostels.get(position).getHstId();
                        Intent i=new Intent(TotalHostel.this,HostelRooms.class);
                        i.putExtra("hstownerid",grpname);
                        i.putExtra("hstid",hstid);
                        startActivity(i);
                        Toast.makeText(TotalHostel.this,grpname,Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public boolean onLongGroupclick(int position) {
                        return false;
                    }
                });
                recyclerView.setAdapter(adapter);



                if (location != null) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new TotalHostel.GeocoderHandler());


                    // progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TotalHostel.this,"Turn On Location",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                selectlocation();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(TotalHostel.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });








        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query=query.toLowerCase();
                alert.dismiss();
                setTitle(query);
                Toast.makeText(TotalHostel.this,query,Toast.LENGTH_SHORT).show();

                adapter.getFilter().filter(query);


                if(adapter.getItemCount()>0){


                    noservices.setVisibility(View.INVISIBLE);
                }else {
                    noservices.setVisibility(View.VISIBLE);
                    //Toast.makeText(TotalHostel.this,"Ikkada",Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                setTitle(newText);
                if(adapter.getItemCount()>0){


                    noservices.setVisibility(View.INVISIBLE);
                }else {
                    noservices.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });



    }



    private void selectlocation() {
        progressDialog.show();



        locationedittext=(SearchView) bi.findViewById(R.id.lumsome);
        yourlocation=(TextView) bi.findViewById(R.id.textView53);
        locationRecyclerView=(RecyclerView)bi.findViewById(R.id.idrecyclerviewlocation);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        yourlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();



                if (location != null) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new TotalHostel.GeocoderHandler());


                        alert.setCancelable(false);
                        alert.dismiss();
                        try{

                    searchView.setQuery(getTitle().toString().toLowerCase().trim(),true);
                      // adapter.getFilter().filter(getTitle().toString().toLowerCase());
                             }catch (Exception e){
                            Toast.makeText(TotalHostel.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                       // progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TotalHostel.this,"Turn On Location",Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationedittext.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list1=new ArrayList<Locationdetails>();
                        for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                            Locationdetails p=dataSnapshot1.getValue(Locationdetails.class);
                            list1.add(p);
                        }

                        locationAdapter=new LocationAdapter(TotalHostel.this,list1, new LocationAdapter.OnGroupListener() {
                            @Override
                            public void onGroupclick(int position) {
                                String grpname=locationAdapter.location.get(position).getLocationName();

                                searchView.setQuery(grpname,true);
                                if(adapter.getItemCount()>0){


                                    noservices.setVisibility(View.INVISIBLE);
                                }else {
                                    noservices.setVisibility(View.VISIBLE);
                                }



                            }

                            @Override
                            public boolean onLongGroupclick(int position) {
                                return false;
                            }
                        });
                        locationRecyclerView.setAdapter(locationAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TotalHostel.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        locationedittext.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query=query.toLowerCase();
                alert.dismiss();
                setTitle(query);
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.getFilter().filter(newText);
                return true;
            }
        });


        builder=new AlertDialog.Builder(TotalHostel.this);
        alert=builder.create();
        alert.setView(bi);
        alert.setIcon(R.drawable.locationicon);
        alert.setTitle("Select Location");
        //alert.setCancelable(false);
        alert.show();

    }

    private void idmanaging() {
        searchView=findViewById(R.id.idsearch);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
//        locationRecyclerView=findViewById(R.id.idrecyclerviewlocation);
  //      locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView=findViewById(R.id.idrecyclerviewhostelss);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference= FirebaseDatabase.getInstance().getReference().child("Hostels");
        reference1= FirebaseDatabase.getInstance().getReference().child("Locations");
        searchView.setQueryHint("Enter Search");

        noservices=(TextView) findViewById(R.id.idnoservices);
        noservices.setVisibility(View.INVISIBLE);

    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }


            //Toast.makeText(TotalHostel.this,locationAddress,Toast.LENGTH_SHORT).show();
            setTitle(locationAddress);
            //searchView.setQuery(locationAddress,true);
            progressDialog.dismiss();
            //address=locationAddress.toString();




        }
    }
}
