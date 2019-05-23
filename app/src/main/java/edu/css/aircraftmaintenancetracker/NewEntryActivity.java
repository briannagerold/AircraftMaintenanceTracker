package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEntryActivity extends AppCompatActivity {

    EditText txtDate, txtAircraftNumber, txtDescription, txtType;
    Button btnSave;
    FirebaseData fbData;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_new_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the user from the bundle that was passed
        Bundle extras = getIntent().getExtras();
        user = extras.getString(MainActivity.USER_EMAIL_TAG);

        //set up firebase and open the connection
        fbData = new FirebaseData(this);
        fbData.open(user);

        //set up the text views
        txtDate = findViewById(R.id.txtDateEdit);
        txtAircraftNumber = findViewById(R.id.txtAircraftNumberEdit);
        txtDescription = findViewById(R.id.txtDescriptionEdit);
        txtType = findViewById(R.id.txtPlaneTypeEdit);

        //get the current date
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = formatter.format(Calendar.getInstance().getTime());
        //pre-fill the current date
        txtDate.setText(dateStr);


        //set up the button
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = txtDate.getText().toString(); //get the date
                String newShipNum = txtAircraftNumber.getText().toString(); //get the ship number
                String newPlaneType = txtType.getText().toString(); //get the plane type
                String newDescription = txtDescription.getText().toString(); //get the description

                //create a new log entry
                LogEntry newLogEntry = new LogEntry(newDate, newShipNum, newPlaneType, newDescription);

                //check if it is a valid log entry
                if(newLogEntry.valid()) {
                    //add to the database
                    fbData.createLogEntry(newLogEntry);

                    //return to the main activity
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    //if not valid show error messages to the user
                    txtAircraftNumber.setText(newLogEntry.getShipNum());
                    txtDate.setText(newLogEntry.getDateStr());
                }
            }
        });
    }

}