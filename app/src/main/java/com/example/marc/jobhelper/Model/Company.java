package com.example.marc.jobhelper.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;

import com.example.marc.jobhelper.Controller.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private String website = "http://google.com";
    private String phone = "0873 376461";
    private Uri imgUri = null;

    public Company(){}
    public Company(String _companyName, String _jobTitle, String _status, Date _date, String _address, String _contactPerson, Uri _website, String _phone, Uri _imgUri){
        if(!_companyName.equals("")) companyName = _companyName;
        if(!_jobTitle.equals("")) jobTitle = _jobTitle;
        if(ApplicationStatus.availableStati.contains(_status)) status.changeStatus(_status, _date);
        address.setAddressLine(0, _address);
        if(!_contactPerson.equals("")) contactPerson = _contactPerson;
        website = _website.toString();
        if(!_phone.equals("")) phone = _phone;
        imgUri = _imgUri;
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

    public Uri getWebsite(){ return Uri.parse(website); }

    public String getPhone(){
        return phone;
    }

    public Uri getImgUri(){ return imgUri; }

    public void setCompanyName(String _companyName){ companyName = _companyName; }

    public void setStatus(String status) { this.status.setStatus(status);  }

    public void setDate(Date date){ this.status.setDate(date);}

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setAddress(String address) {
        this.address.setAddressLine(0, address);
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setWebsite(Uri website) {
        this.website = website.toString();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public Bitmap loadBitmap(){
        try {
            final InputStream imageStream = MainActivity.getAppContext().getContentResolver().openInputStream(imgUri);
            return BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
