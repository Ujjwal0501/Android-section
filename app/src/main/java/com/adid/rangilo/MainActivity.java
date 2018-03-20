package com.adid.rangilo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button ch;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FusedLocationProviderClient mFusedLocationClient;
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    String str = "", email;
    TextView tvbal, tvexp, tvspent, tvcredit;
    LinearLayout llcredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        email = getIntent().getExtras().getString("mail");
        String[] parts = email.split("@");
        email = parts[0];
        tvbal = (TextView) findViewById(R.id.tvbal);
        tvexp = (TextView) findViewById(R.id.tvexp);
        tvspent = (TextView) findViewById(R.id.tvspent);
        tvcredit = (TextView) findViewById(R.id.tvcredit);
        llcredit = (LinearLayout) findViewById(R.id.llcredit);

        llcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCredit();
            }
        });

        tvbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvbal.setText(getBalance());
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        updateContent();
        createLocationRequest();

    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5500);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // update content of main page
    private void updateContent() {
        //TODO
        getBalance();
        getCredit();
        tvspent.setText("0 rs");
    }

    // get balance; manual update only; auto-update on create
    String wallet = "";
    private String getBalance() {
        /*CollectionReference usersref = db.collection("users");
        Query query = usersref.whereEqualTo("username", ""+email);
        query.get();
        return ""+query;*/
        DocumentReference docRef = db.collection("users").document(""+email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d("", "DocumentSnapshot data: " + document.getData());
                        tvbal.setText(document.getString("wallet")+"\t Rs");
                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
        return ""+wallet;
    }

    public void getCredit() {
        DocumentReference docRef = db.collection("users").document(""+email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d("", "DocumentSnapshot data: " + document.getData());
                        tvcredit.setText(document.getString("credits"));
                    } else {
                        Log.d("", "No such document");
                        tvcredit.setText("NA");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                    tvcredit.setText("Unsuccessful");
                }
            }
        });
        //return ""+wallet;
    }

    // get location update; returns coordinates
    private void getLoc() {
        // TODO
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                // showExplanation("Permission Needed", "Rationale", android.Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            str =  "Could not Retrieve location";
                            if (location != null) {
                                // Logic to handle location object
                                str = ""+ (location.getLatitude()) + "," +
                                        "  gh" + location.getLongitude();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getLoc();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    // check current spot and spots in DB
    // takes return value of getLoc, returns TRUE/FALSE
    private static void checkSpot() {
        // TODO
    }

    // explored spots; only called when return of checkSpot is true
    private static void expStat() {
        // TODO
    }

    public void showexp(View v) {
        startActivity(new Intent(MainActivity.this, Explore.class));
    }

    public void contest1(View v) {
        Intent mIntent = new Intent(MainActivity.this, ContestDisplay.class);
        startActivity(mIntent);
    }

    public void contest2(View v) {
        Intent mIntent = new Intent(MainActivity.this, ContestDisplay.class);
        startActivity(mIntent);
    }
}
