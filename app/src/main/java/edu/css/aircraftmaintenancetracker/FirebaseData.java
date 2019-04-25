package edu.css.aircraftmaintenancetracker;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData {

    DatabaseReference dbRef;
    public static final String DataTag = "AircraftMaintenanceTracker";

    public DatabaseReference open(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(DataTag);
        return dbRef;
    }

    public void close(){

    }

    public LogEntry createLogEntry(String date, String num, String description){
        //get a new key
        String key = dbRef.child(DataTag).push().getKey();
        //create a new log entry
        LogEntry logEntry = new LogEntry(date, num, description);

        //get the aircraft type (first 2 digits of num)
        int type = (Integer.parseInt(num)) / 100;


        return logEntry;
    }


}
