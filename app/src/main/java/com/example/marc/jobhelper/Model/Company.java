package com.example.marc.jobhelper.Model;

import android.location.Address;

import java.util.Date;
import java.util.Locale;

/**
 * Speichert die Daten zu einer Firma.
 * Erstellt von Marc am 17.06.17.
 */

public class Company {

    private String companyName = "Neue Firma";
    private String jobTitle  = "Neuer Job";
    private ApplicationStatus status = new ApplicationStatus();
    private android.location.Address address = new Address(Locale.getDefault());
    private String contactPerson = "Ansprechpartner";
    private android.location.Address website = new Address(Locale.getDefault());
    private String phone = "0873 376461";

    public Company(String _companyName, String _jobTitle, String _status, Date _date, String _address, String _contactPerson, String _website, String _phone){
        if(!_companyName.equals("")) companyName = _companyName;
        if(!_jobTitle.equals("")) jobTitle = _jobTitle;
        if(ApplicationStatus.availableStati.contains(_status)) status.changeStatus(_status, _date);
        address.setAddressLine(0, _address);
        if(!_contactPerson.equals("")) contactPerson = _contactPerson;
        website.setUrl(_website);
        if(!_phone.equals("")) phone = _phone;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getJobTitle(){
        return jobTitle;
    }

    public String getStatus(){
        return status.getStatus();
    }

    public Date getDate(){
        return status.getDate();
    }

    public Address getAddress(){
        return address;
    }

    public String getContactPerson(){
        return contactPerson;
    }

    public Address getWebsite(){
        return website;
    }

    public String getPhone(){
        return phone;
    }
}
