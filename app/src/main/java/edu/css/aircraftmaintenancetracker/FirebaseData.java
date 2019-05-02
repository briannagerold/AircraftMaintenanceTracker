package edu.css.aircraftmaintenancetracker;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseData {

    DatabaseReference dbRef;
    public String dataTag = "AircraftMaintenanceTracker/";

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseDatabase database;

    public FirebaseData(Context context){

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    public DatabaseReference open(String user){
        database = FirebaseDatabase.getInstance();
        dataTag = dataTag + user;
        dbRef = database.getReference(dataTag);
        return dbRef;

    }

    public void close(){

    }

    public LogEntry createLogEntry(LogEntry logEntry){
        //get a new key
        //String key = dbRef.child(dataTag).push().getKey();
        String key = dbRef.push().getKey();

        //put user first
        dbRef.child(key).setValue(logEntry);

        return logEntry;
    }

    public List<LogEntry> getList(DataSnapshot dataSnapshot){
        List<LogEntry> logList = new ArrayList<LogEntry>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            LogEntry logEntry = data.getValue(LogEntry.class);
            logList.add(0,logEntry);
        }
        return logList;
    }


    public void signOut(){
        mGoogleSignInClient.signOut();
    }



}
