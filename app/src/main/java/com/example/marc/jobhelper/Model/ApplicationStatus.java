package com.example.marc.jobhelper.Model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Speichert den Status der Bewerbung
 */

public class ApplicationStatus {

    private String status;
    public static final String PLANNED = "planned";
    public static final String SENT = "sent";
    public static final String INT_PLANNED = "interviewPlanned";
    public static final String INT_HELD = "interviewHeld";
    public static final String DENIED = "denied";
    public static final String ACCEPTED = "accepted";
    protected static final String[] availStatiArr= new String[] {PLANNED, SENT, INT_PLANNED, INT_HELD, DENIED, ACCEPTED};
    public static final List<String> availableStati = Arrays.asList(availStatiArr);
    private Date plannedDate;
    private Date sentDate;
    private Date interviewDate;
    private Date denyDate;
    private Date acceptedDate;

    public ApplicationStatus(){
        status = PLANNED;
        plannedDate = new Date();
    }

    public ApplicationStatus(String _status){
        setStatus(_status);
    }

    public ApplicationStatus(String _status, Date _date){
        changeStatus(_status, _date);
    }

    public boolean setStatus (String _status){
        for(String s : availableStati) {
            if (s.equals(_status)) {
                status = _status;
                return true;
            }
        }
        status = PLANNED;
        return false;
    }

    public void changeStatus(String _status, Date _date) {
        if(_date != null) _date = new Date();
        if(setStatus(_status)){
                setDate(_date);
        }
    }

    public void setDate(Date _date){
        switch(status) {
            case PLANNED:
                plannedDate = _date;
                return;
            case SENT:
                sentDate = _date;
                return;
            case INT_PLANNED:
                interviewDate = _date;
                return;
            case INT_HELD:
                return;
            case DENIED:
                denyDate = _date;
                return;
            case ACCEPTED:
                acceptedDate = _date;
        }
    }

    public Date getDate() {
        switch(status) {
            case PLANNED:
                return plannedDate;
            case SENT:
                return sentDate;
            case INT_PLANNED:
                return interviewDate;
            case INT_HELD:
                return interviewDate;
            case DENIED:
                return denyDate;
            case ACCEPTED:
                return acceptedDate;
        }
        return new Date(); //sollte nie passieren, IDE mault aber ohne.
    }

    public String getStatus() {
        return status;
    }
}
