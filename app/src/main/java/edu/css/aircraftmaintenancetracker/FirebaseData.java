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

    /**
     * create with Google sign in options
     * @param context
     */
    public FirebaseData(Context context){

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    /**
     * Get a reference to the tree under the user
     * @param user
     * @return
     */
    public DatabaseReference open(String user){
        //get an instance of the database
        database = FirebaseDatabase.getInstance();
        //set the dataTat to include the username
        dataTag = dataTag + user;
        //get a reference to that tree of the database
        dbRef = database.getReference(dataTag);
        //return the reference
        return dbRef;

    }

    /**
     * Add a log entry to the database
     * @param logEntry
     * @return
     */
    public LogEntry createLogEntry(LogEntry logEntry){
        //get a new key
        String key = dbRef.push().getKey();

        //add the new log entry to the database under the user and the key
        dbRef.child(key).setValue(logEntry);

        //return what was added
        return logEntry;
    }

    /**
     * get all the entries under the user
     * @param dataSnapshot
     * @return
     */
    public List<LogEntry> getList(DataSnapshot dataSnapshot){
        List<LogEntry> logList = new ArrayList<LogEntry>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            LogEntry logEntry = data.getValue(LogEntry.class);
            logList.add(0,logEntry);
        }
        return logList;
    }

    /**
     * sign out of the app
     */
    public void signOut(){
        mGoogleSignInClient.signOut();
    }



}
