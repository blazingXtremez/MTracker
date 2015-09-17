package com.example.riteish.mtracker;

import java.io.Serializable;

/**
 * Created by Riteish on 4/27/2015.
 */
public class Trains implements Serializable{
    String trainName;
    String status;
    String time;
    String date;
    String information;

    // getter functionality
    public String getTrainName(){
        return trainName;
    }

    public String getStatus(){
        return status;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }

    public String getInfo(){return information;}

    @Override
    public String toString(){
        return trainName+" "+status+" "+time+" "+date+" "+information;
    }


}