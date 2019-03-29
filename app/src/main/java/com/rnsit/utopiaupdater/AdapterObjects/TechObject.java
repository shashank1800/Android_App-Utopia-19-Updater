package com.rnsit.utopiaupdater.AdapterObjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TechObject implements Serializable{
    String eventName,partName,teamName;
    long timeStamp;
    public TechObject (long timeStamp,String eventName,String partName,String teamName){
        this.timeStamp = timeStamp;
        this.eventName = eventName;
        this.partName = partName;
        this.teamName = teamName;
    }
    public TechObject(){}

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}