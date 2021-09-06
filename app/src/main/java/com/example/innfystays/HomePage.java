package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private Button hstbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Toolbar Toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Button signout;
    private TextView mailtextview,nametextview;
    private ImageView imageView,imageView1,imageView2,imageView3;
    private DatabaseReference databaseReference;
    private String name,mail,uid;
    private View vh;




    AppLocationService appLocationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);





        Assinging();
        GettingUserDetails();

       // Intent i=getIntent();
       // String mail=i.getStringExtra("mail");
      //  String name=i.getStringExtra("name");
       // Toast.makeText(this,mail+name,Toast.LENGTH_LONG);






        hstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomePage.this,TotalHostel.class));
            }
        });
        setSupportActionBar(Toolbar);

       // firebaseAuth=FirebaseAuth.getInstance();

        imageView=(ImageView) findViewById(R.id.hydimg);
        imageView1=findViewById(R.id.chnimg);
        imageView2=findViewById(R.id.bngimg);
        imageView3=findViewById(R.id.delimg);

        imageView.setImageBitmap(function(R.drawable.hyd));
        imageView1.setImageBitmap(function(R.drawable.chnn));
        imageView2.setImageBitmap(function(R.drawable.bng));
        imageView3.setImageBitmap(function(R.drawable.delhi));


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        toggle = new ActionBarDrawerToggle(HomePage.this, drawer,Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
            }
        });


    }

    private void GettingUserDetails() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                     name=snapshot.child("fullname").getValue().toString();
                    mail=snapshot.child("mailid").getValue().toString();
                    mailtextview.setText(mail);
                    //Toast.makeText(HomePage.this,mail+name,Toast.LENGTH_SHORT).show();
                    nametextview.setText(name);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Assinging() {
        signout=findViewById(R.id.idsignout);
        drawer=(DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        vh=navigationView.getHeaderView(0);
        Toolbar=findViewById(R.id.toolbar);
        hstbtn=findViewById(R.id.hstbtn);



        appLocationService = new AppLocationService(
                HomePage.this);



        mailtextview= (TextView) vh.findViewById(R.id.idnavbarheadermailtextview);
        nametextview=(TextView) vh.findViewById(R.id.idnavbarheadername);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
         uid=firebaseAuth.getCurrentUser().getUid();

        databaseReference=firebaseDatabase.getReference().child("Users").child(uid);



    }


    Bitmap function(int ii){
        Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(ii)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);
        return imageRounded;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_body,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}