package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txtDate, txtDescription, txtNum, txtType;
    LogEntry logEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up the text views
        txtDate = findViewById(R.id.txtDateDetail);
        txtDescription = findViewById(R.id.txtDescriptionDetail);
        txtNum = findViewById(R.id.txtAircraftNumberDetail);
        txtType = findViewById(R.id.txtPlaneTypeDetail);

        //get the log entry sent in the bundle
        Bundle extras = getIntent().getExtras();
        logEntry = (LogEntry) extras.getSerializable(MainActivity.LOG_ENTRY_TAG);

        //set the text views to the values of the log entry
        txtDate.setText(logEntry.getDateStr());
        txtNum.setText(logEntry.getShipNum());
        txtDescription.setText(logEntry.getDescription());
        txtType.setText(logEntry.getPlaneType());


    }

}
