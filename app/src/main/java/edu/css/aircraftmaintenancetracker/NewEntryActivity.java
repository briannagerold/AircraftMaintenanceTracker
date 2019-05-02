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


        Bundle extras = getIntent().getExtras();
        user = extras.getString(MainActivity.USER_EMAIL_TAG);

        fbData = new FirebaseData(this);
        fbData.open(user);

        txtDate = findViewById(R.id.txtDateDetail);
        txtAircraftNumber = findViewById(R.id.txtAircraftNumberDetail);
        txtDescription = findViewById(R.id.txtDescriptionDetail);
        txtType = findViewById(R.id.txtPlaneTypeDetail);

        btnSave = findViewById(R.id.btnSave);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = formatter.format(Calendar.getInstance().getTime());

        txtDate.setText(dateStr);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = txtDate.getText().toString();
                String newShipNum = txtAircraftNumber.getText().toString();
                String newPlaneType = txtType.getText().toString();
                String newDescription = txtDescription.getText().toString();


                LogEntry newLogEntry = new LogEntry(newDate, newShipNum, newPlaneType, newDescription);

                if(newLogEntry.valid()) {
                    fbData.createLogEntry(newLogEntry);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    txtAircraftNumber.setText(newLogEntry.getShipNum());
                    txtDate.setText(newLogEntry.getDateStr());
                }
            }
        });
    }

}