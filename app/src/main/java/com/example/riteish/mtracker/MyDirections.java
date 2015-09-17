package com.example.riteish.mtracker;

import java.io.Serializable;

/**
 * Created by Riteish on 5/19/2015.
 */
public class MyDirections implements Serializable {

    public MyDirections(){}         // empty constructor
    String method;
    String duration;
    String distance;
    String information;
    String startStation;
    String endStation;
    String depatureTime;
    String arrivaltime;
    String trainName;


    // setters and getters
    public void setMethod(String method){
        this.method = method;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public void setInformation(String information){
        this.information = information;
    }

    public void setStartStation(String startStation){
        this.startStation = startStation;
    }

    public void setEndStation(String endStation){
        this.endStation = endStation;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public void setDepatureTime(String depatureTime) {
        this.depatureTime = depatureTime;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    // getter for all the variables
    public String getMethod(){
        return method;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getInformation() {
        return information;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public String getDepatureTime() {
        return depatureTime;
    }

    public String getTrainName() {
        return trainName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

