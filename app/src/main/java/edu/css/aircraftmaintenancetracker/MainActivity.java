package edu.css.aircraftmaintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
import java.util.Collection;
import java.util.Collections;
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

    /**
     * Runs when the app is first started. It sets up
     * all the buttons, the spinner and the list view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSearch = findViewById(R.id.txtSearch);

        //setUpSpinner(); //set up the spinner
        setUpButtons(); //set up all the buttons
        setUpListView(); //set up the list view
    }

    /**
     *Check the if the user is logged in
     * sets up firebase and populates the list view
     * with data for the user
     */
    @Override
    public void onStart(){
        super.onStart();
        checkLogin();
        setupFirebaseDataChange();
    }

    /**
     * Set up the firebase database
     * also sets up the listener for data changes
     */
    private void setupFirebaseDataChange() {
        mFirebaseData = new FirebaseData(this); //new firebase with context
        mDbRef = mFirebaseData.open(userEmail); //opens the database connection under the user
        mDbRef.addValueEventListener(new ValueEventListener() {
            /**
             * When something is added to the list then it will re populate the list view
             * with the new list of entries
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logEntryList = mFirebaseData.getList(dataSnapshot); //get the list from firebase
                //update the list view
                Collections.sort(logEntryList);
                updateList(logEntryList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_logout) {
            mFirebaseData.signOut(); //sign out
            checkLogin(); //call the login screen
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the login active returns
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_LOGIN) {
            //if it returns from the login activity check the login and get the user
            checkLogin();
        }
    }

    /**
     * Check to see if someone is logged in
     * if yes get the user email
     */
    public void checkLogin(){
        //see if someone is logged in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) { //if someone is logged in
            //get the user's email
            userEmail = account.getEmail();
            //clean the email so it doesn't include illegal characters
            cleanUserEmail();
        }
        else{
            //if not logged in - go to the login in screen
            Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(loginIntent, RC_LOGIN);
        }
    }

    /**
     * replace illegal characters with _
     */
    public void cleanUserEmail(){
        userEmail = userEmail.replace('.', '_');
        userEmail = userEmail.replace('#', '_');
        userEmail = userEmail.replace('$', '_');
        userEmail = userEmail.replace('[', '_');
        userEmail = userEmail.replace(']', '_');
    }

    /**
     * Create a new LogEntryAdapter with a list that is passed
     * @param logList
     */
    public void updateList(List<LogEntry> logList){
        //create a new adapter
        logEntryAdapter = new LogEntryAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, logList);
        // Apply the adapter to the list
        listViewLogs.setAdapter(logEntryAdapter);
    }

    /**
     * Use the search field and search term to search the list for matches
     */
    public void search(){
        String searchTerm = txtSearch.getText().toString();
        searchLogEntryList = new ArrayList<>();

        for(int i = 0; i < logEntryList.size(); i++){ //loop- looking for matches
            if(logEntryList.get(i).getDateStr().contains(searchTerm)){ //if the date of the contains the search term
                searchLogEntryList.add(logEntryList.get(i)); //add it to the list
            }
            else if(logEntryList.get(i).getShipNum().contains(searchTerm)){
                searchLogEntryList.add(logEntryList.get(i));
            }
            else if(logEntryList.get(i).getPlaneType().contains(searchTerm)){
                searchLogEntryList.add(logEntryList.get(i));
            }
            else if(logEntryList.get(i).getDescription().contains(searchTerm)){
                searchLogEntryList.add(logEntryList.get(i));
            }
        }

        updateList(searchLogEntryList); //update the list view with the search results
    }

//
//    /**
//     * set up the spinner to get the text of what was selected
//     */
//    public void setUpSpinner(){
//        spnSearch = findViewById(R.id.spnSearchType);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SearchTypes, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spnSearch.setAdapter(adapter);
//
//
//        spnSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //set the search field to what was selected
//                searchField = parent.getItemAtPosition(position).toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //Another interface callback
//            }
//        });
//    }

    /**
     * set up the fab, all and search buttons
     */
    public void setUpButtons(){
        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList(logEntryList); //update the list to show all the entries
            }
        });

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(); //search the list
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the NewEntryActivity and pass the user email
                Intent intent = new Intent(view.getContext(), NewEntryActivity.class);
                intent.putExtra(USER_EMAIL_TAG, userEmail);
                startActivity(intent);
            }
        });
    }

    /**
     * set up the list view
     */
    public void setUpListView(){

        listViewLogs = findViewById(R.id.listLogs);

        listViewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the item that was selected
                LogEntry logEntry = (LogEntry) parent.getItemAtPosition(position);

                //start the detail screen and pass the selected log entry
                Intent detailActIntent = new Intent(parent.getContext(), DetailActivity.class);
                detailActIntent.putExtra(USER_EMAIL_TAG, userEmail);
                detailActIntent.putExtra(LOG_ENTRY_TAG, logEntry);
                startActivity(detailActIntent);
            }
        });
    }

}
