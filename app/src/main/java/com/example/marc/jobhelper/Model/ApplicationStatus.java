package com.example.marc.jobhelper.Model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Speichert den Status der Bewerbung.
 * Dabei kann ein Status "Geplant", "Gesendet" usw. eingestellt werden.
 * Passend dazu werden verschiedene Daten gespeichert, die entsprechend abgerufen werden.
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

    /**
     * Stellt standartmäßig den Status auf PLANNED und das Datum auf das aktuelle Datum.
     */
    ApplicationStatus(){
        status = PLANNED;
        plannedDate = new Date();
    }

    /**
     * Stellt den Status auf den angegebenen Status ein.
     * @param _status einzustellender Status
     */
    protected ApplicationStatus(String _status){
        setStatus(_status);
    }

    /**
     * Erstellt den ApplicationStatus mit den angegebenen Werten.
     * @param _status
     * @param _date
     */
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

    /**
     * Ändert den Status auf die angegebenen Werte.
     * @param _status Status, auf den geändert werden soll.
     * @param _date Datum, auf das geändert werden soll.
     */
    void changeStatus(String _status, Date _date) {
        if(_date != null) _date = new Date();
        if(setStatus(_status)){
                setDate(_date);
        }
    }

    /**
     * Stellt das Datum entsprechend zum vorher eingestellten Status ein.
     * @param _date Datum, auf das gestellt wird.
     */
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

    /**
     * Gibt das Datum zurück, das dem aktuell eingestellten Status entspricht.
     * INT_HELD hat kein Datum, bzw. das gleiche wie INT_PLANNED.
     * @return Datum, passend zum Status
     */
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

    /**
     * Gibt den aktuellen Status zurück.
     * @return Aktueller Status.
     */
    String getStatus() {
        return status;
    }
}
