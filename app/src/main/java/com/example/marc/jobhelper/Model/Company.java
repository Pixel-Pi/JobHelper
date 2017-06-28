package com.example.marc.jobhelper.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.widget.Toast;

import com.example.marc.jobhelper.Controller.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

/**
 * Speichert die Daten zu einer Firma.
 * Erstellt von Marc am 17.06.17.
 */

public class Company {

    private static int NumberCompanies = 0;
    private static boolean ImagesAllowed = true;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;

    private int index;
    private String companyName;
    private String jobTitle;
    private ApplicationStatus status = new ApplicationStatus();
    private android.location.Address address = new Address(Locale.getDefault());
    private String contactPerson;
    private String website;
    private String phone;
    private String imgUri;
    private String thumbnailUri;

    public Company(){
        index = NumberCompanies;
        NumberCompanies++;
    }

    public Company(String _companyName, String _jobTitle, String _status, Date _date, String _address, String _contactPerson, Uri _website, String _phone, Uri _imgUri){
        if(!_companyName.equals("")) companyName = _companyName;
        if(!_jobTitle.equals("")) jobTitle = _jobTitle;
        if(ApplicationStatus.availableStati.contains(_status)) status.changeStatus(_status, _date);
        address.setAddressLine(0, _address);
        if(!_contactPerson.equals("")) contactPerson = _contactPerson;
        website = _website.toString();
        if(!_phone.equals("")) phone = _phone;
        if(_imgUri != null)
        imgUri = _imgUri.toString();
        index = NumberCompanies;
        NumberCompanies++;
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

    public String getWebsite(){ return website;}

    public Uri getWebsiteUri(){ return Uri.parse(website); }

    public String getPhone(){
        return phone;
    }

    public Uri getImgUri(){
        if(imgUri == null) return null;
        return Uri.parse(imgUri); }

    public int getIndex(){ return index;}

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
        this.imgUri = imgUri.toString();
    }

    public static void setImagesAllowed(boolean allowed){
        ImagesAllowed = allowed;
    }

    public Bitmap loadThumbnail() {
        System.out.println("Thumbnail path: " + thumbnailUri);
        System.out.println("Image path: " + imgUri);
        if (ImagesAllowed) {
            if (thumbnailUri != null) {
                try {
                    final InputStream imageStream = MainActivity.getAppContext().getContentResolver().openInputStream(Uri.parse(thumbnailUri));
                    return BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                if (imgUri == null) return null;
                String fileName = MainActivity.getAppContext().getFilesDir() + companyName + "_Logo_thumbnail.png";
                try (FileOutputStream out = new FileOutputStream(fileName)) {
                    Bitmap resized = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imgUri), WIDTH, HEIGHT);
                    resized.compress(Bitmap.CompressFormat.PNG, 90, out);
                    thumbnailUri = fileName;
                    return resized;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }
        else Toast.makeText(MainActivity.getAppContext(), "Kein Zugriff auf SD-Karte für Bilder", Toast.LENGTH_LONG).show();
        return null;
    }
    public Bitmap loadBitmap() {
        if (ImagesAllowed) {
            if (imgUri != null) {
                try {
                    final InputStream imageStream = MainActivity.getAppContext().getContentResolver().openInputStream(Uri.parse(imgUri));
                    return BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        else Toast.makeText(MainActivity.getAppContext(), "Kein Zugriff auf SD-Karte für Bilder", Toast.LENGTH_LONG).show();
        return null;
    }


}
