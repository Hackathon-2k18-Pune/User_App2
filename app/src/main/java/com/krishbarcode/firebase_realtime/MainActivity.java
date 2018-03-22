package com.krishbarcode.firebase_realtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    String email, pass;

    EditText ed1, ed2;
    ProgressDialog probar;
    private FirebaseAuth firebaseauth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseauth = FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),UserMainActivity.class));
        }
        probar = new ProgressDialog(this);

        ed1 = (EditText) findViewById(R.id.email1);
        ed2 = (EditText) findViewById(R.id.pass1);

    }
    public void submit(View v) {


        Log.v("user","creating user id");
        email = ed1.getText().toString().trim();
        pass = ed2.getText().toString().trim();

        verification();


        probar.setMessage("Registering user......");
        probar.show();

        firebaseauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                probar.dismiss();
                if (task.isSuccessful()) {


                    Log.v("user","user id ban gaya");
                    Toast.makeText(MainActivity.this, "Creating Login ID...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Profile.class);
                    i.putExtra("EMAIL",email);
                    startActivity(i);

               } else {
                    Toast.makeText(MainActivity.this, "Could not registered.....  Please try again  ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void signin(View v) {



        Log.v("user","getting logged in with id and pass");
        email = ed1.getText().toString().trim();
        pass = ed2.getText().toString().trim();

        verification();

        probar.setMessage("Getting you logged in...");
        probar.show();
        firebaseauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                probar.dismiss();
                if(task.isSuccessful())                {
                    finish();
                    firebaseUser = firebaseauth.getCurrentUser();
                    if(firebaseUser.isEmailVerified()==false){
                        Toast.makeText(MainActivity.this, "Email is not Verified", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,EmailVerification.class));

                    }
                    else {

                        Log.v("user", "sign in successfully");
                    Intent i = new Intent(getApplicationContext(), UserMainActivity.class);
                    i.putExtra("EMAIL",email);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Getting you Logged in....", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                    Toast.makeText(MainActivity.this,"Not able to login ..Retry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void verification()
    {

        Log.v("user","verification of id and pass");
        if (TextUtils.isEmpty(email)) {
            ed1.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            ed2.setError("Enter pass");
            return;
        }
        if(pass.length()<6)
        {
            ed2.setError("Password should be at least 6 characters");

        }

    }


}
