package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEntryActivity extends AppCompatActivity {

    EditText txtDate, txtAircraftNumber, txtDescription;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDate = findViewById(R.id.txtDate);
        txtAircraftNumber = findViewById(R.id.txtAircraftNumber);
        txtDescription = findViewById(R.id.txtDescription);

        btnSave = findViewById(R.id.btnSave);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = formatter.format(Calendar.getInstance().getTime());

        txtDate.setText(dateStr);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogEntry logEntry = new LogEntry(txtDate.getText().toString(), txtAircraftNumber.getText().toString(), txtDescription.getText().toString());

                if(logEntry.getAircreaftNumInt() != 0 ) {
                    //ADD CODE TO ADD TO THE LIST AND THE DATABASE

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    txtAircraftNumber.setText(logEntry.getAircraftNum());
                }
            }
        });
    }

}
