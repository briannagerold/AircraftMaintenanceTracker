package edu.css.aircraftmaintenancetracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogEntry implements Serializable {

    private String key;
    private String dateStr;
    private String shipNum;
    private String planeType;
    private String description;

    private final String dateError = "Enter as MM/DD/YYYY";
    private final String numError = "Enter a valid number";

    public LogEntry(){
        dateStr = "";
        shipNum = "";
        planeType = "";
        description = "";
    }

    public LogEntry(String dateStr, String shipNum, String planeType, String description){
        setDateStr(dateStr);
        setShipNum(shipNum);
        setPlaneType(planeType);
        setDescription(description);
    }

    public LogEntry(String key, String dateStr, String shipNum, String planeType, String description){
        setKey(key);
        setDateStr(dateStr);
        setShipNum(shipNum);
        setPlaneType(planeType);
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


    public void setShipNum(String shipNum){
        this.shipNum = numError;
        if(!shipNum.equals("")) {
            Integer num = Integer.parseInt(shipNum);
            if (num < 10000 && num > 99) {
                this.shipNum = shipNum;
            }
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlaneType(String planeType){
        this.planeType = planeType;
    }

    public String getKey(){
        return key;
    }

    public String getDateStr(){
        return dateStr;
    }

    public String getShipNum(){
        return shipNum;
    }


    public String getDescription (){
        return description;
    }

    public String getPlaneType() {
        return planeType;
    }

    public boolean valid(){
        return !(getShipNum().equals(numError) || getDateStr().equals(dateError));
    }
}
