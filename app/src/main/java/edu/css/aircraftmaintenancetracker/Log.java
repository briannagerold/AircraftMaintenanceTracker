package edu.css.aircraftmaintenancetracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Log {

    private String key;
    private String dateStr;
    private String aircraftNum;
    private String description;

    public Log(){

    }

    public Log(String dateStr, String aircraftNum, String description){
        setDateStr(dateStr);
        setAircraftNum(aircraftNum);
        setDescription(description);
    }

    public Log(String key, String dateStr, String aircraftNum, String description){
        setKey(key);
        setDateStr(dateStr);
        setAircraftNum(aircraftNum);
        setDescription(description);
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setDateStr(String dateStr){
        this.dateStr = dateStr;
    }

    public void setCurrentDate(){
        SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
        dateStr = formater.format(Calendar.getInstance().getTime());
    }

    public void setAircraftNum(String aircraftNum){
        Integer num = Integer.parseInt(aircraftNum);
        if (num < 10000 && num > 99){
            this.aircraftNum = aircraftNum;
        }
        else {
            this.aircraftNum = "Error";
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

    public int getAircreaftNumInt(){
        if(!aircraftNum.equals("Error")){
            return Integer.parseInt(aircraftNum);
        }
            return 0;
    }

    public String getDescription (){
        return description;
    }
}
