package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseData mFirebaseData;
    DatabaseReference mDbRef;
    List<LogEntry> logEntryList;
    ArrayAdapter<LogEntry> logEntryAdapter;
    ListView listViewLogs;
    public static final String LOG_ENTRY_TAG = "LogEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CIS 3334", "FAB CLICKED" );
                Intent intent = new Intent(view.getContext(), NewEntryActivity.class);
                startActivity(intent);
            }
        });

        listViewLogs = findViewById(R.id.listLogs);
        setupFirebaseDataChange();

        listViewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogEntry logEntry = (LogEntry) parent.getItemAtPosition(position);
                Intent detailActIntent = new Intent(parent.getContext(), DetailActivity.class);
                detailActIntent.putExtra(LOG_ENTRY_TAG, logEntry);
                startActivity(detailActIntent);
            }
        });

//        listViewLogs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LogEntry logEntry = logEntryList.get(position);
//
//                Intent intent = new Intent(view.getContext(), DetailActivity.class);
//                intent.putExtra(LOG_ENTRY_TAG, logEntry);
//                startActivity(intent);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //Another interface callback
//            }
//        });


    }

    private void setupFirebaseDataChange() {
        mFirebaseData = new FirebaseData();
        mDbRef = mFirebaseData.open();
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("CIS3334", "Starting onDataChange()");        // debugging log
                logEntryList = mFirebaseData.getList(dataSnapshot);
                // Instantiate a custom adapter for displaying each fish
                logEntryAdapter = new LogEntryAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, logEntryList);
                // Apply the adapter to the list
                listViewLogs.setAdapter(logEntryAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("CIS3334", "onCancelled: ");
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Log.d("CIS 3334", "Logout clicked");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
