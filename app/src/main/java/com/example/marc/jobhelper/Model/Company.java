package com.example.marc.jobhelper.Model;

import android.location.Address;
import android.net.Uri;

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
    private Uri imgUri;

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

    public String getAddress(){ return address.getAddressLine(0); }

    public String getContactPerson(){
        return contactPerson;
    }

    public String getWebsite(){
        return website.getUrl();
    }

    public String getPhone(){
        return phone;
    }

    public Uri getImgUri(){ return imgUri; }

    public void setCompanyName(String _companyName){ companyName = _companyName; }

    public void setStatus(ApplicationStatus status) { this.status = status;  }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setAddress(String address) {
        this.address.setAddressLine(0, address);
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setWebsite(String website) {
        this.website.setUrl(website);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }
}
