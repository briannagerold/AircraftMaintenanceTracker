package edu.css.aircraftmaintenancetracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogEntry implements Serializable {

    private String key; //key for firebase
    private String dateStr; //date of work
    private String shipNum; //ship number of the aircraft
    private String planeType; // type of aircraft
    private String description; //description of work done on the aircraft

    private final String dateError = "Enter as MM/DD/YYYY"; //if date is not in correct format
    private final String numError = "Enter a valid number"; //if ship number is not valid

    /**
     * Empty constructor
     */
    public LogEntry(){

    }

    /**
     * constructor with out key
     * @param dateStr
     * @param shipNum
     * @param planeType
     * @param description
     */
    public LogEntry(String dateStr, String shipNum, String planeType, String description){
        setDateStr(dateStr);
        setShipNum(shipNum);
        setPlaneType(planeType);
        setDescription(description);
    }

    /**
     * constructor with key
     * @param key
     * @param dateStr
     * @param shipNum
     * @param planeType
     * @param description
     */
    public LogEntry(String key, String dateStr, String shipNum, String planeType, String description){
        setKey(key);
        setDateStr(dateStr);
        setShipNum(shipNum);
        setPlaneType(planeType);
        setDescription(description);
    }

    /**
     * set the key
     * @param key
     */
    public void setKey(String key){
        this.key = key;
    }

    /**
     * set the data if it is in the MM/DD/YYYY format
     * @param dateStr
     */
    public void setDateStr(String dateStr){
        this.dateStr = dateError; //will have an error message if the date string is not in the correct format
        if(dateStr.length() == 10){ //is it the correct length
            char firstSlash = dateStr.charAt(2); //the 3rd character
            char secondSlash = dateStr.charAt(5); //the 6th character

            if(firstSlash == '/' && secondSlash == '/'){ //are the characters slashes
                this.dateStr = dateStr; //if yes set the date string
            }
        }
    }

    /**
     * set the ship number if it is valid
     * @param shipNum
     */
    public void setShipNum(String shipNum){
        this.shipNum = numError; //will be an error message if it doesn't pass
        if(!shipNum.equals("")) { //not empty
            Integer num = Integer.parseInt(shipNum); //as an int
            if (num < 10000 && num > 99) { //3 or 4 digits
                this.shipNum = shipNum; //set the ship number
            }
        }
    }

    /**
     * set the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set the plane type
     * @param planeType
     */
    public void setPlaneType(String planeType){
        this.planeType = planeType;
    }

    /**
     * get the key
     * @return
     */
    public String getKey(){
        return key;
    }

    /**
     * get the date
     * @return
     */
    public String getDateStr(){
        return dateStr;
    }

    /**
     * get the ship number
     * @return
     */
    public String getShipNum(){
        return shipNum;
    }

    /**
     * get the description of the work that was done
     * @return
     */
    public String getDescription (){
        return description;
    }

    /**
     * get the plane type
     * @return
     */
    public String getPlaneType() {
        return planeType;
    }

    /**
     * check to see if there was an error in the date or the ship number
     * @return
     */
    public boolean valid(){
        return !(getShipNum().equals(numError) || getDateStr().equals(dateError));
    }
}
