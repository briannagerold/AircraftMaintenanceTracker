package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseData mFirebaseData; //firebase class
    DatabaseReference mDbRef; //reference to firebase
    List<LogEntry> logEntryList; //list of all the entries
    List<LogEntry> searchLogEntryList; //list of the entries that match the search
    ArrayAdapter<LogEntry> logEntryAdapter; //array adapter for the list
    private String userEmail; //The email for the user

    public static final String LOG_ENTRY_TAG = "LogEntry"; //tag for bundle information
    public static final String USER_EMAIL_TAG = "UserEmail"; //tag for bundle information

    private final int RC_LOGIN = 1111; //tag for startActivityWithResult

    TextView txtSearch; //text field for search
    Button btnSearch, btnAll; //buttons
    Spinner spnSearch; //spinner for search field
    String searchField = "Date"; //default search field - stores the string of field
    ListView listViewLogs; //The list view that displays all the entries


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAll = findViewById(R.id.btnAll);
        spnSearch = findViewById(R.id.spnSearchType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SearchTypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnSearch.setAdapter(adapter);


        spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchField = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logEntryAdapter = new LogEntryAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, logEntryList);
                // Apply the adapter to the list
                listViewLogs.setAdapter(logEntryAdapter);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewEntryActivity.class);
            intent.putExtra(USER_EMAIL_TAG, userEmail);
            startActivity(intent);
            }
        });

        listViewLogs = findViewById(R.id.listLogs);

        listViewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogEntry logEntry = (LogEntry) parent.getItemAtPosition(position);

            Intent detailActIntent = new Intent(parent.getContext(), DetailActivity.class);
            detailActIntent.putExtra(LOG_ENTRY_TAG, logEntry);
            startActivity(detailActIntent);
            }
        });

    }

    private void setupFirebaseDataChange() {
        mFirebaseData = new FirebaseData(this);
        mDbRef = mFirebaseData.open(userEmail);
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logEntryList = mFirebaseData.getList(dataSnapshot);
                // Instantiate a custom adapter for displaying each fish
                updateList(logEntryList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
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
            mFirebaseData.signOut();
            logEntryList.clear();
            updateList(logEntryList);
            checkLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        Log.d("CIS 3334", "IN ON START");
        super.onStart();
        checkLogin();
        setupFirebaseDataChange();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_LOGIN) {
            checkLogin();
        }
    }

    public void checkLogin(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            userEmail = account.getEmail();
            cleanUserEmail();
        }
        else{
            Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(loginIntent, RC_LOGIN);
        }
    }

    public void cleanUserEmail(){
        userEmail = userEmail.replace('.', '_');
        userEmail = userEmail.replace('#', '_');
        userEmail = userEmail.replace('$', '_');
        userEmail = userEmail.replace('[', '_');
        userEmail = userEmail.replace(']', '_');
    }

    public void updateList(List<LogEntry> logList){
        logEntryAdapter = new LogEntryAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, logList);
        // Apply the adapter to the list
        listViewLogs.setAdapter(logEntryAdapter);
    }

    public void search(){
        String searchTerm = txtSearch.getText().toString();
        if(searchField.equals("Date")){
            searchLogEntryList = searchByDate(searchTerm);
        }
        else if (searchField.equals("Ship #")){
            searchLogEntryList = searchByShipNum(searchTerm);
        }
        else if(searchField.equals("Plane Type")){
            searchLogEntryList = searchByType(searchTerm);
        }
        else if(searchField.equals("Description")){
            searchLogEntryList = searchByDescription(searchTerm);
        }

        updateList(searchLogEntryList);
    }

    public List<LogEntry> searchByDate(String term){
        List<LogEntry> temp = new ArrayList<>();

        for(int i = 0; i < logEntryList.size(); i++){
            if(logEntryList.get(i).getDateStr().equals(term)){
                temp.add(logEntryList.get(i));
            }
        }

        return temp;
    }


    public List<LogEntry> searchByShipNum(String term){
        List<LogEntry> temp = new ArrayList<>();

        for(int i = 0; i < logEntryList.size(); i++){
            if(logEntryList.get(i).getShipNum().equals(term)){
                temp.add(logEntryList.get(i));
            }
        }

        return temp;
    }


    public List<LogEntry> searchByType(String term){
        List<LogEntry> temp = new ArrayList<>();

        for(int i = 0; i < logEntryList.size(); i++){
            if(logEntryList.get(i).getPlaneType().contains(term)){
                temp.add(logEntryList.get(i));
            }
        }

        return temp;
    }


    public List<LogEntry> searchByDescription(String term){
        List<LogEntry> temp = new ArrayList<>();

        for(int i = 0; i < logEntryList.size(); i++){
            if(logEntryList.get(i).getDescription().contains(term)){
                temp.add(logEntryList.get(i));
            }
        }

        return temp;
    }


}
