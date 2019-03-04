package com.rnsit.utopiaupdater.AdapterObjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TechObject implements Serializable{
    String eventName,partName,teamName;
    public TechObject (String eventName,String partName,String teamName){
        this.eventName = eventName;
        this.partName = partName;
        this.teamName = teamName;
    }
    public TechObject(){}

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