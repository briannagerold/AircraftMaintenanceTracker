package edu.css.aircraftmaintenancetracker;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseData {

    DatabaseReference dbRef;
    public static final String DataTagAll = "AircraftMaintenanceTracker";
    public static final String DataTagType = "AircraftMaintenanceTracker/Type";

    public DatabaseReference open(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(DataTagAll);
        return dbRef;
    }

    public void close(){

    }

    public LogEntry createLogEntry(LogEntry logEntry){
        //get a new key
        String key = dbRef.child(DataTagAll).push().getKey();

        //get the aircraft type (first 2 digits of num)
        int type = logEntry.getAircreaftNumInt() / 100;
        String typeStr = type + "";


        dbRef.child(key).setValue(logEntry);

        dbRef.child("Aircraft").child(typeStr).child(logEntry.getAircraftNum()).child(key).setValue(logEntry);

        return logEntry;
    }

    public List<LogEntry> getList(DataSnapshot dataSnapshot){
        List<LogEntry> logList = new ArrayList<LogEntry>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            LogEntry logEntry = data.getValue(LogEntry.class);
            logList.add(logEntry);
        }
        return logList;
    }







}
