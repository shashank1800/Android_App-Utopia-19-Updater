package com.rnsit.utopiaupdater.AdapterObjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SportsObject implements Serializable{
    String eventName,subHead,teamName1,teamName2,winnerName;
    public SportsObject (String eventName,String subHead,String teamName1,String teamName2,String winnerName){
        this.eventName = eventName;
        this.subHead = subHead;
        this.teamName1=teamName1;
        this.teamName2 = teamName2;
        this.winnerName = winnerName;
    }
    public SportsObject(){}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public void setTeamName2(String teamName2) {
        this.teamName2 = teamName2;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}