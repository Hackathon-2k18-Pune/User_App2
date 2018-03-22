package com.krishbarcode.firebase_realtime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText age;
    EditText veh_no;
    EditText con_no;
    EditText adharno;
    String name;
    Button save, verify;
    FirebaseFirestore firestore;

    String frstname,lstname,ae,veh,con,adhar;

    private String USERID = "";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase, mref, userref;
    private FirebaseDatabase firedatabaase;

    String ema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firestore = FirebaseFirestore.getInstance();


        firedatabaase = FirebaseDatabase.getInstance();
        mref = firedatabaase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        USERID = firebaseUser.getUid();


        firebaseAuth = FirebaseAuth.getInstance();
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        age = (EditText) findViewById(R.id.age);

        veh_no = (EditText) findViewById(R.id.veh_no);
        con_no = (EditText) findViewById(R.id.contact_no);
        adharno = (EditText) findViewById(R.id.adhar_no);
        save = (Button) findViewById(R.id.save);

        ema = firebaseUser.getEmail();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("user","user ka save button");
                if (validateForm()) {

                    frstname = fname.getText().toString().trim();
                    lstname = lname.getText().toString().trim();
                    name = frstname +" "+ lstname;
                    ae = age.getText().toString().trim();
                    veh = veh_no.getText().toString().trim();
                    con = con_no.getText().toString().trim();
                    adhar = adharno.getText().toString().trim();
                    Log.v("tag","\n"+name+"\n"+ae+"\n"+veh+"\n"+con+"\n"+adhar+"\n");



                    Map<String,Object> user = new HashMap<>();
                    user.put("name",name);
                    user.put("age",ae);
                    user.put("veh",veh);
                    user.put("con",con);
                    user.put("adhar",adhar);
                    user.put("email",ema);
                    user.put("userid",USERID);

                    firestore.collection(firebaseUser.getUid()).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Log.v("tag","Document is added with id - "+documentReference.getId());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("tag","error occured profile"+e);
                        }
                    });








                    /*

                    User user = new User(ema,name,ae,veh,con,adhar,USERID);


//                    user.setUserid(USERID);
//                    user.setfirstname(frstname);
//                    user.setlastname(lstname);
//                    user.setage(ae);
//                    user.setemail(ema);
//                    user.setvehno(veh);
//                    user.setconno(con);
//                    user.setadhar(adhar);


                    mDatabase = FirebaseDatabase.getInstance().getReference("User/ProfileData");

                    Map<String, User> musers = new HashMap<String, User>();
                    musers.put(firebaseUser.getUid(),user);


                    mDatabase.child(firebaseUser.getUid()).setValue(musers, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Log.v("tag","\n"+frstname+"\n"+lstname+"\n"+ae+"\n"+veh+"\n"+con+"\n"+adhar+"\n");
                                Toast.makeText(Profile.this, "Data is saved successfully",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });
                    */
                }


                startActivity(new Intent(Profile.this,SetProfileImage.class));
            }
        });
    }

    public boolean validateForm() {
        boolean alldone = true;
        String frstname = fname.getText().toString().trim();
        String lstname = lname.getText().toString().trim();
        String ae = age.getText().toString().trim();

        if (TextUtils.isEmpty(frstname)) {
            fname.setError("Enter your first name");
            return false;
        }
        else {
            alldone = true;
            fname.setError(null);
        }
        if (TextUtils.isEmpty(lstname)) {
            lname.setError("Enter your last name");
            return false;
        }
        if (TextUtils.isEmpty(ae)) {
            age.setError("Enter your Age");
            return false;
        } else {
            alldone = true;
            age.setError(null);
        }

        return alldone;
    }

//    public void write(View v)
//    {
//        String value = edit.getText().toString().trim();
//        demoref.child("value").setValue(value);
//        text1.setText("");
//    }
//    public void fetch(View v)
//    {   demoref.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            String value = dataSnapshot.getValue(String.class);
//            text3.setText(value);
//        }
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Toast.makeText(Profile.this, "No data found", Toast.LENGTH_SHORT).show();
//        }
//    });
//    }
//


}


