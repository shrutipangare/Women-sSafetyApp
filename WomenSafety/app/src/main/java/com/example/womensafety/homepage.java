package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homepage extends AppCompatActivity {

    private Gyroscope gyroscope;
    VideoView v;
    MediaController mediaController;
    ImageButton ibsetting;
    WomenSafety w;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference dbWomenSafety;
    String ph1, ph2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        v = (VideoView) findViewById(R.id.videoView2);
        ibsetting = (ImageButton) findViewById(R.id.settingb);

        w = new WomenSafety();

        mauth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dbWomenSafety = FirebaseDatabase.getInstance().getReference(mauth.getUid()).child("setting");
        dbWomenSafety.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ph1 = dataSnapshot.child("phno1").getValue().toString();
                    ph2 = dataSnapshot.child("phno2").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mediaController = new MediaController(this);
        ibsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }


        });

        gyroscope = new Gyroscope(this);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (rz > 5.0f) {
                    int permissioncheck = ContextCompat.checkSelfPermission(homepage.this, Manifest.permission.SEND_SMS );
                    if (permissioncheck==PackageManager.PERMISSION_GRANTED) {
                        doSms(ph2);
                    }
                    else{
                        ActivityCompat.requestPermissions(homepage.this,new String[]{Manifest.permission.SEND_SMS},0);

                    }
                    doCall(ph1);
                   //getNearbyhelp();
                }

            }
        });

    }

    public void openSetting() {
        Intent isetting = new Intent(homepage.this, Setting.class);
        startActivity(isetting);
    }

    protected void onResume() {
        super.onResume();
        gyroscope.register();

    }

    protected void onPause() {
        super.onPause();
        gyroscope.unregister();
    }
    void getNearbyhelp(){
        getWindow().getDecorView().setBackgroundColor(Color.RED);
        String path = "android.resource://com.example.womensafety/" + R.raw.siren;
        Uri uri = Uri.parse(path);
        v.setVideoURI(uri);
        v.setMediaController(mediaController);
        mediaController.setAnchorView(v);
        v.start();
    }
    void doCall(String ph1){
        Intent icall=new Intent(Intent.ACTION_CALL);
        icall.setData(Uri.parse("tel:"+ph1));
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
        Toast.makeText(homepage.this,"please grant permission",Toast.LENGTH_LONG).show();
        requestPermission();
         }
        else{
        startActivity(icall);
         }
        getNearbyhelp();
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(homepage.this,new String[]{Manifest.permission.CALL_PHONE},0);
    }
    void doSms(String ph2){
        String msg="I am in trouble please help me";
        SmsManager sm=SmsManager.getDefault();
        sm.sendTextMessage(ph2,null,msg,null,null);
        Toast.makeText(this,"msg delivered",Toast.LENGTH_LONG).show();

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0 :if(grantResults.length>=0&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                doSms(ph2);
            }
            else{
                Toast.makeText(this,"no permission granted",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                mauth.signOut();
                finish();
                startActivity(new Intent(homepage.this,MainActivity.class));

            }
        }
        return super.onOptionsItemSelected(item);
    }

}