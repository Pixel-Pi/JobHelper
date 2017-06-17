package com.example.marc.jobhelper;

import android.location.Address;

import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marc on 17.06.17.
 */

public class Company {
    //TODO benötigte Daten Variablen

    private String companyName;
    private String jobTitle;
    private ApplicationStatus status;
    private android.location.Address address;
    private String contactPerson;
    private android.location.Address website;
    private String phone;
    public Company(){
        companyName = "Neue Firma";
        jobTitle = "Neuer Job";
        status = new ApplicationStatus();
        address = new Address(Locale.getDefault());
        address.setAddressLine(0, "");
        contactPerson = "Ansprechpartner";
        website = new Address(Locale.getDefault());
        website.setUrl("example.com");
        phone = "0873 376461";
    }

    public Company(String _companyName, String _jobTitle, String _status, Date _date, String _address, String _contactPerson, String _website, String _phone){

    }
}
