package com.krishbarcode.firebase_realtime;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.TimeUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView name, navname, veh, navemail;
    ImageView proimage, navimage;
    StorageReference proimageref, storageReference;
    FirebaseStorage storage;
    FirebaseUser firebaseUser;
    View header;
    DatabaseReference mDatabase;
    FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        header = navigationView.getHeaderView(0);
        //header = navigationView.inflateHeaderView(R.layout.nav_header_user_main);
        //header = LayoutInflater.from(this).inflate(R.layout.nav_header_user_main,navigationView,false);
        //navigationView.addHeaderView(header);


        name = (TextView) findViewById(R.id.name);
        veh = (TextView) findViewById(R.id.veh_no);
        proimage = (ImageView) findViewById(R.id.proimage);
        navimage = (ImageView) header.findViewById(R.id.navproimage);
        navname = (TextView) header.findViewById(R.id.navname);
        navemail = (TextView) header.findViewById(R.id.navemail);

        storage = FirebaseStorage.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = storage.getReference();
        String dwnldurl = "uploads/" + firebaseUser.getUid() + ".jpg";
        Log.v("dwnldurl", dwnldurl);
        proimageref = storageReference.child(dwnldurl);

        Glide.with(this).using(new FirebaseImageLoader()).load(proimageref).into(proimage);







        Glide.with(this).using(new FirebaseImageLoader()).load(proimageref).into(navimage);


//
//        File localfile = null;
//        try {
//            localfile = File.createTempFile(String.valueOf(System.currentTimeMillis()),"jpg");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //final long ONE_MEGABYTE = 1024 * 1024 *2;
//        final File finalLocalfile = localfile;
//        proimageref.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//
//            }
//        });
//
////        proimageref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                Bitmap round = getRoundedShape(bitmap);
//
//                proimage.setImageBitmap(bitmap);
//                navimage.setImageBitmap(bitmap);
//                Log.v("tag", "Image Loaded");
//                //        Toast.makeText(UserMainActivity.this, "Image loaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(UserMainActivity.this, "image not found", Toast.LENGTH_SHORT).show();
//            }
//        });

        firestore = FirebaseFirestore.getInstance();
        firestore.collection(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("data", "=>" + document.getData() + document.get("name") + document.get("veh") + document.get("email"));
                                name.setText(document.get("name").toString());
                                navname.setText(document.get("name").toString());
                                veh.setText(document.get("veh").toString());
                                navemail.setText(document.get("email").toString());




                            }

                        } else {
                            Log.e("data", "error occured user main" + task.getException());
                        }

                    }
                });
        firestore.collection(firebaseUser.getUid()+"contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {


                                Log.d("data", "=>" + document.getData() + document.get("contact1") + document.get("contact2") + document.get("contact3"));


                            }

                        } else {
                            Log.e("data", "error occured user main" + task.getException());
                        }

                    }
                });


//        Log.v("tag","Data retriving started");
//
////        mDatabase = FirebaseDatabase.getInstance().getReference("User/ProfileData/"+firebaseUser.getUid());
////        Log.v("tag","Data reference created");
//        FirebaseDatabase.getInstance().getReference().child("User").child("ProfileData").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.v("tag","ondata changed");
//
//                User us = dataSnapshot.getValue(User.class);
//
//
//                Log.v("tag",us+us.name+us.veh_no+us.name+us.email);
//                name.setText(us.name);
//                veh.setText(us.veh_no);
//                navname.setText(us.name);
//                navemail.setText(us.email);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.v("tag", String.valueOf(databaseError.getCode()));
//                Toast.makeText(UserMainActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.v("tag","ondata changed");
//
//                User us = dataSnapshot.getValue(User.class);
//                    Log.v("tag",us.name+us.veh_no+us.name+us.email);
//                    name.setText(us.name);
//                    veh.setText(us.veh_no);
//                    navname.setText(us.name);
//                    navemail.setText(us.email);
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                Log.v("tag", String.valueOf(databaseError.getCode()));
//                Toast.makeText(UserMainActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
//            }
//        });


    }



    public Bitmap getRoundedShape(Bitmap scaleBitmaoimage) {
        int width = 50;
        int height = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) width - 1) / 2, ((float) height - 1) / 2, (Math.min(((float) width), ((float) height)) / 2), Path.Direction.CW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path);
        }
        Bitmap sourceBitmap = scaleBitmaoimage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, width, height), null);
        return targetBitmap;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_pro) {
            startActivity(new Intent(UserMainActivity.this, Profile.class));

        } else if (id == R.id.log_out) {

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(UserMainActivity.this, MainActivity.class));
        } else if (id == R.id.addcontacts) {
            startActivity(new Intent(UserMainActivity.this, AddContacts.class));
        } else if (id == R.id.changepropic) {
            startActivity(new Intent(UserMainActivity.this, SetProfileImage.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
