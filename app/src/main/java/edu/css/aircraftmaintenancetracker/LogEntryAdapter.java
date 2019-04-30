package edu.css.aircraftmaintenancetracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LogEntryAdapter extends ArrayAdapter<LogEntry> {

    private List<LogEntry> logEntryList;
    private Context context;

    public LogEntryAdapter(Context context, int resource, List<LogEntry> objects) {
        //super(context, R.layout.country_list_item, R.id.txtViewCountryName, objects);
        super(context, resource, objects);
        this.context = context;
        this.logEntryList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the planet we are displaying
        LogEntry logEntry = logEntryList.get(position);
        View view;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.entry_row, null);

        TextView txtDate = view.findViewById(R.id.lblDate);
        TextView txtNum = view.findViewById(R.id.lblERAircraftNumber);
        TextView txtDescription = view.findViewById(R.id.lblDescription);

        txtDate.setText(logEntry.getDateStr());
        txtNum.setText(logEntry.getAircraftNum());
        txtDescription.setText(logEntry.getDescription());

        return(view);
    }
}
