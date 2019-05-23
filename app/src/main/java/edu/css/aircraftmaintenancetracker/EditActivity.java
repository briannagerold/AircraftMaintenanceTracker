package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class EditActivity extends AppCompatActivity {

    LogEntry logEntry;
    TextView txtDate, txtShipNumber, txtPlaneType, txtDescription;
    FirebaseData mFirebaseData; //firebase class
    DatabaseReference mDbRef;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String userEmail = extras.getString(MainActivity.USER_EMAIL_TAG);
        logEntry = (LogEntry) extras.getSerializable(DetailActivity.EDIT_LOG);

        txtDate = findViewById(R.id.txtDateEdit);
        txtShipNumber = findViewById(R.id.txtAircraftNumberEdit);
        txtPlaneType = findViewById(R.id.txtPlaneTypeEdit);
        txtDescription = findViewById(R.id.txtDescriptionEdit);

        txtDate.setText(logEntry.getDateStr());
        txtShipNumber.setText(logEntry.getShipNum());
        txtPlaneType.setText(logEntry.getPlaneType());
        txtDescription.setText(logEntry.getDescription());

        mFirebaseData = new FirebaseData(this); //new firebase with context
        mDbRef = mFirebaseData.open(userEmail);

        btnSave = findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogEntry newLogEntry = new LogEntry(logEntry.getKey(), txtDate.getText().toString(), txtShipNumber.getText().toString(), txtPlaneType.getText().toString(), txtDescription.getText().toString());

                mFirebaseData.delete(logEntry);
                mFirebaseData.createLogEntry(newLogEntry);
                Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }


    /**
     * Create the menu - default code
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    /**
     * set up the actions for the menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the item clicked
        int id = item.getItemId();

        //if logout (only option) then sign out
        if (id == R.id.action_delete) {
            mFirebaseData.delete(logEntry);
            Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(mainIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
