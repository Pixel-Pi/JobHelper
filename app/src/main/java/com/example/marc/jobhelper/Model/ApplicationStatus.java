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
    private static final String[] availStatiArr= new String[] {PLANNED, SENT, INT_PLANNED, INT_HELD, DENIED, ACCEPTED};
    public static final List<String> availableStati = Arrays.asList(availStatiArr);
    private Date plannedDate;
    private Date sentDate;
    private Date interviewDate;
    private Date denyDate;
    private Date acceptedDate;

    ApplicationStatus(){
        status = PLANNED;
        plannedDate = new Date();
    }

    protected ApplicationStatus(String _status){
        setStatus(_status);
    }

    protected ApplicationStatus(String _status, Date _date){
        changeStatus(_status, _date);
    }

    boolean setStatus (String _status){
        for(String s : availableStati) {
            if (s.equals(_status)) {
                status = _status;
                return true;
            }
        }
        status = PLANNED;
        return false;
    }

    void changeStatus(String _status, Date _date) {
        if(_date != null) _date = new Date();
        if(setStatus(_status)){
                setDate(_date);
        }
    }

    void setDate(Date _date){
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

    Date getDate() {
        Date returnDate = null;
        switch(status) {
            case PLANNED:
                returnDate = plannedDate;
                break;
            case SENT:
                returnDate = sentDate;
                break;
            case INT_PLANNED:
                returnDate = interviewDate;
                break;
            case INT_HELD:
                returnDate = interviewDate;
                break;
            case DENIED:
                returnDate = denyDate;
                break;
            case ACCEPTED:
                returnDate = acceptedDate;
                break;
        }
        if(returnDate == null) returnDate = new Date();
        return returnDate;
    }

    String getStatus() {
        return status;
    }
}
