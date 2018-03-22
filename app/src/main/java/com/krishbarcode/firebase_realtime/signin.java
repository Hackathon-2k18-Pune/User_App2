package com.krishbarcode.firebase_realtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button but;
    private TextView text1;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pd = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();



        email = (EditText) findViewById(R.id.email1);
        pass = (EditText) findViewById(R.id.pass1);
        but = (Button) findViewById(R.id.but);
        text1 = (TextView) findViewById(R.id.textView);
    }

    public void users(View view)
    {
        String emailtext,passtext;
        emailtext = email.getText().toString().trim();
        passtext = pass.getText().toString().trim();


        if(TextUtils.isEmpty(emailtext))
        {
            //email is emptt
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(passtext   ))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();

            return;

        }

    }
    public void signup(View v)
    {

    }
}
