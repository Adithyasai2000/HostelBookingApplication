package com.example.innfystays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 126;
    private EditText email,password,confirmpassword;
    private TextView forgetpassword,phonesignup,signuptext,logintext;
    private Button login,signup,signupnew;
    private FirebaseAuth firebaseAuth;
    String stremail,strpassword,strpasswordconfirm;
    private ProgressDialog progressDialog;
    private ImageView phoneauth,googleauth,facebookauth;
    private FirebaseDatabase firebaseDatabase;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
               progressDialog.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            finish();
                            progressDialog.dismiss();
                            Intent i=new Intent(MainActivity.this,SignupProfileData.class);
                            i.putExtra("mail",task.getResult().getUser().getEmail());
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "GOOGLE SIGNIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Assigning();

        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        if(user!=null){

            startActivity(new Intent(MainActivity.this,HomePage.class));
            finish();


        }
        facebookauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"This is in under constuction",Toast.LENGTH_LONG).show();
            }
        });
        googleauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                progressDialog.show();

                  Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                   startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });


        forgetpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mail=email.getText().toString().trim();
                if(!mail.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Reset Password email has sent to your\nREGISTERED MAIL", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this, "Reset Password email has not sent \nVERIFY REGISTERED MAIL", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Empty Field enter mail", Toast.LENGTH_SHORT).show();

                }
            }
        });
        phoneauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this,PhoneSignUp.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmpassword.setVisibility(View.VISIBLE);
                signupnew.setVisibility(View.VISIBLE);
                signuptext.setVisibility(View.VISIBLE);
                phoneauth.setVisibility(View.INVISIBLE);
                googleauth.setVisibility(View.INVISIBLE);
                facebookauth.setVisibility(View.INVISIBLE);
                logintext.setVisibility(View.INVISIBLE);
                phonesignup.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                forgetpassword.setVisibility(View.INVISIBLE);
            }
        });

        signupnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                stremail=email.getText().toString().trim();
                strpasswordconfirm=confirmpassword.getText().toString().trim();
                strpassword=password.getText().toString().trim();
                if (strpassword.isEmpty()||strpasswordconfirm.isEmpty()||stremail.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Empty Feild",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (strpassword.equals(strpasswordconfirm)) {
                        firebaseAuth.createUserWithEmailAndPassword(stremail, strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Verification mail has been sent\nPlease verify that", Toast.LENGTH_LONG).show();
                                                firebaseAuth.signOut();
                                                confirmpassword.setVisibility(View.INVISIBLE);
                                                signupnew.setVisibility(View.INVISIBLE);
                                                phonesignup.setVisibility(View.VISIBLE);

                                                signup.setVisibility(View.VISIBLE);
                                                login.setVisibility(View.VISIBLE);

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "not Registered,verification mail hasn't been sent", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    //startActivity(new Intent(MainActivity.this,SignupProfileData.class));
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Signedup UnSuccessfull\nRetry Later or Check your Connection", Toast.LENGTH_SHORT).show();


                                }


                            }
                        });
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                stremail=email.getText().toString().trim();
                strpassword=password.getText().toString().trim();
                if(strpassword.isEmpty() || stremail.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Empty Field",Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(stremail,strpassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                mailverification();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Login unsucessful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }

    private void mailverification() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        Boolean b=user.isEmailVerified();
        if(b){
            progressDialog.dismiss();
            finish();
            Intent i=new Intent(MainActivity.this,SignupProfileData.class);
            i.putExtra("mail",stremail);
            startActivity(i);

        }else{
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,"Email Verification is required",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }

    }
    private void Assigning() {
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextTextPassword3);
        confirmpassword=findViewById(R.id.editTextTextPassword4);
        forgetpassword=findViewById(R.id.textView);
        login=findViewById(R.id.button);
        signup=findViewById(R.id.button4);
        signupnew=findViewById(R.id.button5);
        phonesignup=findViewById(R.id.button2);
        signuptext=findViewById(R.id.textView3);
        logintext=findViewById(R.id.textView4);
        phoneauth=findViewById(R.id.imageView);
        googleauth=findViewById(R.id.imageView3);
        facebookauth=findViewById(R.id.imageView4);
        phoneauth.setVisibility(View.VISIBLE);
        googleauth.setVisibility(View.VISIBLE);
        facebookauth.setVisibility(View.VISIBLE);
        signuptext.setVisibility(View.INVISIBLE);
        phonesignup.setVisibility(View.VISIBLE);
        confirmpassword.setVisibility(View.INVISIBLE);
        signupnew.setVisibility(View.INVISIBLE);
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");



    }



}