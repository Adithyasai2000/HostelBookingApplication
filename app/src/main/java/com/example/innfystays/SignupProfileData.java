package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Math.log;

public class SignupProfileData extends AppCompatActivity {
    private String mail,phoneno;
    Intent i;
    EditText personname,personmail,personnumber,personaddress,personcollege;
    FloatingActionButton updatedatafab;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String name,mailid,phone,address,college;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_profile_data);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        String user=firebaseUser.getUid();

        progressDialog.show();
        if(!user.isEmpty()){
            DatabaseReference rt=firebaseDatabase.getReference().child("Users").child(user);
            rt.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try {
                        String g=snapshot.child("uid").getValue().toString();
                        String name=snapshot.child("fullname").getValue().toString();
                        String mail=snapshot.child("mailid").getValue().toString();
                        progressDialog.dismiss();
                        finish();
                        Intent i=new Intent(SignupProfileData.this, HomePage.class);
                       // i.putExtra("mail",mail);
                       // i.putExtra("name",name);
                        startActivity(i);
                    }
                    catch (Exception e){
                        progressDialog.dismiss();
                        Toast.makeText(SignupProfileData.this,"Fill Details",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(SignupProfileData.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }
        Assigning();
        i=getIntent();
        mail=i.getStringExtra("mail");
        if(!mail.isEmpty()){
            personmail.setText(mail);
        }
        updatedatafab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gettingdatails();
                if(phone.isEmpty()||name.isEmpty()||mailid.isEmpty()||address.isEmpty()||college.isEmpty()){
                    Toast.makeText(SignupProfileData.this,"ENTER ALL DETAILS",Toast.LENGTH_SHORT).show();
                }
                else{
                    senddatatoserver();
                    finish();
                    startActivity(new Intent(SignupProfileData.this,HomePage.class));
                }
            }
        });

    }

    private void senddatatoserver() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dref=firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        Profile p=new Profile(name,mailid,phone,college,address,firebaseUser.getUid());
        dref.setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignupProfileData.this,"Profile Updated",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupProfileData.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Gettingdatails() {
        name=personname.getText().toString().trim();
        mailid=personmail.getText().toString().trim();
        phone=personnumber.getText().toString().trim();
        college=personcollege.getText().toString().trim();
        address=personaddress.getText().toString().trim();


    }

    private void Assigning() {
        personname=findViewById(R.id.editTextTextPersonName);
        personmail=findViewById(R.id.editTextTextEmailAddress);
        personnumber=findViewById(R.id.editTextPhone);
        personcollege=findViewById(R.id.editTextTextPersonName2);
        personaddress=findViewById(R.id.editTextTextPostalAddress);
        updatedatafab=findViewById(R.id.floatingActionButton);
    }
}