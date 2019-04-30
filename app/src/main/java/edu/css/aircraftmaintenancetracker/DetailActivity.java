package edu.css.aircraftmaintenancetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txtDate, txtDescription, txtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtDate = findViewById(R.id.txtDateDetail);
        txtDescription = findViewById(R.id.txtDescriptionDetail);
        txtNum = findViewById(R.id.txtAircraftNumberDetail);

        Bundle extras = getIntent().getExtras();
        LogEntry logEntry = (LogEntry) extras.getSerializable(MainActivity.LOG_ENTRY_TAG);

        txtDate.setText(logEntry.getDateStr());
        txtNum.setText(logEntry.getAircraftNum());
        txtDescription.setText(logEntry.getDescription());


    }

}
