package edu.css.aircraftmaintenancetracker;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseData {

    DatabaseReference dbRef;
    public static final String DATA_TAG = "AircraftMaintenanceTracker";

    private FirebaseDatabase database;

    public DatabaseReference open(){
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(DATA_TAG);
        return dbRef;
    }

    public void close(){

    }

    public LogEntry createLogEntry(LogEntry logEntry){
        //get a new key
        String key = dbRef.child(DATA_TAG).push().getKey();

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







}
