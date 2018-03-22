package com.krishbarcode.firebase_realtime;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageException;

import java.util.HashMap;
import java.util.Map;

public class AddContacts extends AppCompatActivity {

    private static final int CONTACT1 = 1;
    private static final int CONTACT2 = 2;
    private static final int CONTACT3 = 3;
    TextView contactNumber1, ContactName1;
    TextView contactNumber2, ContactName2;
    TextView contactNumber3, ContactName3;
    Button button1, button2, button3;

    private String USERID = "";
    String con1="",con2="",con3="",name1,name2,name3,number1,number2,number3;
    FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        firestore = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        USERID = firebaseUser.getUid();

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        ContactName1 = (TextView) findViewById(R.id.contactname1);
        ContactName2 = (TextView) findViewById(R.id.contactname2);
        ContactName3 = (TextView) findViewById(R.id.contactname3);

        contactNumber1 = (TextView) findViewById(R.id.contactnumber1);
        contactNumber2 = (TextView) findViewById(R.id.contactnumber2);
        contactNumber3 = (TextView) findViewById(R.id.contactnumber3);

        button1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });
        button2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 2);
            }
        });
        button3.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 3);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONTACT1) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();


                 name1 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                 number1 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                button1.setVisibility(View.GONE);
                ContactName1.setText(name1);
                contactNumber1.setText(number1);

            }
        }
        if (requestCode == CONTACT2) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();


                 name2 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number2 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                button2.setVisibility(View.GONE);
                ContactName2.setText(name2);
                contactNumber2.setText(number2);

            }
        }
        if (requestCode == CONTACT3) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();


                 name3 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number3 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                button3.setVisibility(View.GONE);
                ContactName3.setText(name3);
                contactNumber3.setText(number3);

            }
        }
    }
    public void done(View view)
    {
         con1 = name1+number1;
         con2 = name2+number2;
         con3 = name3+number3;
        Map<String,Object> contact = new HashMap<>();
        contact.put("contact1",con1);
        contact.put("contact2",con2);
        contact.put("contact3",con3);

        firestore.collection(firebaseUser.getUid()+"contacts").add(contact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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



    }

}

