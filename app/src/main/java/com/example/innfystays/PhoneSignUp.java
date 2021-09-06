package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class PhoneSignUp extends AppCompatActivity {

    private FloatingActionButton flobtn;
    private Button rsdbtn,sgnupbtn;
    private TextView otpheader;
    private EditText sgnnumber,otpnumber;
    private FirebaseAuth firebaseAuth;

    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private ProgressDialog progressDialog;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String number,codebysystem;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign_up);
        firebaseAuth=FirebaseAuth.getInstance();
        Assigning();
        flobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendotptophoneAuthentication();

            }
        });


        sgnupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otpnumber.getText().toString();
                if (!code.isEmpty()) {
                    Toast.makeText(PhoneSignUp.this, "here", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhoneSignUp.this,HomePage.class));

                } else {
                    Toast.makeText(PhoneSignUp.this, "empty feild", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void sendotptophoneAuthentication() {
        number=sgnnumber.getText().toString().trim();
        if(number.isEmpty()){
            Toast.makeText(PhoneSignUp.this,"Empty Field",Toast.LENGTH_SHORT).show();
            return;

        }
        rsdbtn.setVisibility(View.VISIBLE);
        sgnupbtn.setVisibility(View.VISIBLE);
        otpheader.setVisibility(View.VISIBLE);
        otpnumber.setVisibility(View.VISIBLE);
        sgnnumber.setVisibility(View.INVISIBLE);
        Toast.makeText(PhoneSignUp.this, number, Toast.LENGTH_SHORT).show();
        sendVerificationCode(number);





    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,                 //phoneNo that is given by user
                60,                             //Timeout Duration
                TimeUnit.SECONDS,                   //Unit of Timeout
                (Activity) TaskExecutors.MAIN_THREAD,          //Work done on main Thread
                mCallbacks);                       // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    //Getting the code sent by SMS
                    String code = phoneAuthCredential.getSmsCode();

                    //sometime the code is not detected automatically
                    //in this case the code will be null
                    //so user has to manually enter the code
                    if (code != null) {
                        otpnumber.setText(code);
                        //verifying the code
                        verifyVerificationCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(PhoneSignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    //Log.e("TAG",e.getMessage() );
                }

                //when the code is generated then this method will receive the code.
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);

                    //storing the verification id that is sent to the user
                    mVerificationId = s;
                }
            };
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneSignUp.this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //verification successful we will start the profile activity
                                    //Intent intent = new Intent(VerifyPhoneActivity.this, ProfileActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   // startActivity(intent);

                                } else {

                                    //verification unsuccessful.. display an error message

                                    String message = "Somthing is wrong, we will fix it soon...";

                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        message = "Invalid code entered...";
                                    }
                                    Toast.makeText(PhoneSignUp.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }





    void Assigning(){
        flobtn=findViewById(R.id.idfloatingActionButton);
        sgnnumber=findViewById(R.id.idinputfield);
        rsdbtn=findViewById(R.id.idresendotpbtn);
        sgnupbtn=findViewById(R.id.idokbtn);
        otpheader=findViewById(R.id.idotpheader);
        otpnumber=findViewById(R.id.idotpedittext);
        rsdbtn.setVisibility(View.INVISIBLE);
        sgnupbtn.setVisibility(View.INVISIBLE);
        otpheader.setVisibility(View.INVISIBLE);
        otpnumber.setVisibility(View.INVISIBLE);
        progressDialog=new ProgressDialog(PhoneSignUp.this);

    }

}