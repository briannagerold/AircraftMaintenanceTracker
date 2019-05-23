package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txtDate, txtDescription, txtNum, txtType;
    LogEntry logEntry;
    public static final String EDIT_LOG = "editLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //get the log entry sent in the bundle
        Bundle extras = getIntent().getExtras();
        final String userEmail = extras.getString(MainActivity.USER_EMAIL_TAG);
        logEntry = (LogEntry) extras.getSerializable(MainActivity.LOG_ENTRY_TAG);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtra(MainActivity.USER_EMAIL_TAG, userEmail);
                intent.putExtra(EDIT_LOG, logEntry);
                startActivity(intent);
            }
        });

        //set up the text views
        txtDate = findViewById(R.id.txtDateEdit);
        txtDescription = findViewById(R.id.txtDescriptionEdit);
        txtNum = findViewById(R.id.txtAircraftNumberEdit);
        txtType = findViewById(R.id.txtPlaneTypeEdit);


        //set the text views to the values of the log entry
        txtDate.setText(logEntry.getDateStr());
        txtNum.setText(logEntry.getShipNum());
        txtDescription.setText(logEntry.getDescription());
        txtType.setText(logEntry.getPlaneType());


    }

}
