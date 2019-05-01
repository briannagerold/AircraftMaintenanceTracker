package edu.css.aircraftmaintenancetracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogEntry implements Serializable {

    private String key;
    private String dateStr;
    private String aircraftNum;
    private String description;

    private final String dateError = "Enter as MM/DD/YYYY";
    private final String numError = "Enter a valid number";

    public LogEntry(){
        dateStr = "";
        aircraftNum = "";
        description = "";
    }

    public LogEntry(String dateStr, String aircraftNum, String description){
        setDateStr(dateStr);
        setAircraftNum(aircraftNum);
        setDescription(description);
    }

    public LogEntry(String key, String dateStr, String aircraftNum, String description){
        setKey(key);
        setDateStr(dateStr);
        setAircraftNum(aircraftNum);
        setDescription(description);
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setDateStr(String dateStr){
        this.dateStr = dateError;
        if(dateStr.length() == 10){
            char firstSlash = dateStr.charAt(2);
            char secondSlash = dateStr.charAt(5);

            if(firstSlash == '/' && secondSlash == '/'){
                this.dateStr = dateStr;
            }
        }
    }


    public void setAircraftNum(String aircraftNum){
        if(!aircraftNum.equals("")) {
            Integer num = Integer.parseInt(aircraftNum);
            if (num < 10000 && num > 99) {
                this.aircraftNum = aircraftNum;
            }
        }
        else {
            this.aircraftNum = numError;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey(){
        return key;
    }

    public String getDateStr(){
        return dateStr;
    }

    public String getAircraftNum(){
        return aircraftNum;
    }

//    public int getAircreaftNumInt(){
//        if(!aircraftNum.equals(numError)){
//            return Integer.parseInt(aircraftNum);
//        }
//            return 0;
//    }

    public String getDescription (){
        return description;
    }

    public boolean valid(){
        return !(getAircraftNum().equals(numError) || getDateStr().equals(dateError));

    }
}
