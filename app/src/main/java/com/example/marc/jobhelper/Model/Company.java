package com.example.marc.jobhelper.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.marc.jobhelper.Controller.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

/**
 * Speichert die Daten zu einer Firma.
 * Es wird alles soweit möglichst als Elementarer Datentyp gespeichert, um später beim Transformieren in ein
 * Json keine Probleme zu bekommen.
 * Bilder werden nicht direkt im Objekt gespeichert sondern nur deren Referenz.
 * Erstellt von Marc am 17.06.17.
 */

public class Company {

    private static int NumberCompanies = 0;
    private static boolean ImagesAllowed = false;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;

    private int index;
    private String companyName = "";
    private String jobTitle = "";
    private ApplicationStatus status = new ApplicationStatus();
    private android.location.Address address = new Address(Locale.getDefault());
    private String contactPerson = "";
    private String website = "";
    private String phone = "";
    private String imgUri = "";
    private String thumbnailUri = "";
    private int contrastColor = Color.WHITE;

    /**
     * Erstellt eine Company mit Standartwerten.
     */
    public Company(){
        index = NumberCompanies;
        NumberCompanies++;
    }

    /**
     * Erstellt eine Company mit den angegebenen parametern. Der Index wird automatisch berechnet.
     * @param _companyName
     * @param _jobTitle
     * @param _status
     * @param _date
     * @param _address
     * @param _contactPerson
     * @param _website
     * @param _phone
     * @param _imgUri
     */
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

    public String getPhone(){
        return phone;
    }

    public Uri getImgUri(){
        if(imgUri == null) return null;
        return Uri.parse(imgUri); }

    public int getIndex(){ return index; }

    public int getContrastColor(){ return contrastColor; }

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
        if(!this.imgUri.equals("")) this.deleteThumbnail();
        this.imgUri = imgUri.toString();
    }

    public void setContrastColor(int contrastColor) { this.contrastColor = contrastColor; }

    public static void setImagesAllowed(boolean allowed){
        ImagesAllowed = allowed;
    }

    /**
     * Erstellt das Thumbnail für die Listenansichten.
     * @return Thumbnail des Bildes, das unter imgUri zu finden ist.
     */
    private Bitmap thumbnailCreator(){
        if (imgUri.equals("")) return null;
        System.out.println("Loading original Image and making Thumbnail");
        String hiddenDirectory = ".JobHelperThumbnails/";
        String fileName = hiddenDirectory + index + companyName + "_Logo_thumbnail.png";
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = new File(path, fileName);
        try{
            //.nomedia file anlegen
            if(imageFile.getParentFile().mkdirs()) new File(path, hiddenDirectory + ".nomedia").createNewFile();
            imageFile.createNewFile();

            //Thumbnail erstellen und auf der SD-Karte speichern
            FileOutputStream out = new FileOutputStream(imageFile, false);
            final InputStream imageStream = MainActivity.getAppContext().getContentResolver().openInputStream(Uri.parse(imgUri));
            Bitmap resized = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(imageStream), WIDTH, HEIGHT);
            resized.compress(Bitmap.CompressFormat.PNG, 90, out);

            //Link zu Thumbnail im Objekt speichern
            thumbnailUri = imageFile.getAbsolutePath();
            System.out.println("ThumbnailPath: " + thumbnailUri);

            //Company in der Datenbank speichern.
            DatabaseConnection.getInstance(MainActivity.getAppContext()).addCompany(this);
            out.close();
            return resized;
        } catch (Exception e) {
            e.printStackTrace();
            return this.loadBitmap();
        }

    }

    /**
     * Löscht das Thumbnail, wenn es existent ist.
     * @return True, wenn das Löschen geklappt hat.
     */
    private boolean deleteThumbnail(){
        if (!thumbnailUri.equals("")) {
            File thumb = new File(thumbnailUri);
            try {
                return thumb.delete();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
        return true;
    }

    /**
     * Überprüft ob ein Thumbnail vorhanden ist und lädt es. Falls es nicht vorhanden ist, wird es erstellt.
     * @return Thumbnail oder falls es keines gibt null.
     */

    public Bitmap loadThumbnail() {
        if (ImagesAllowed) {
            if (!thumbnailUri.equals("")) {
                System.out.println("Loading Thumbnail");
                try(FileInputStream fis = new FileInputStream(thumbnailUri)){
                    return BitmapFactory.decodeStream(fis);
                } catch (Exception e) { //Loading thumbnail failed? Create a new one
                    e.printStackTrace();
                    return thumbnailCreator();
                }
            } else {
                return thumbnailCreator();
            }
        }
        else Toast.makeText(MainActivity.getAppContext(), "Kein Zugriff auf SD-Karte für Bilder", Toast.LENGTH_LONG).show();
        return null;
    }

    /**
     * Lädt die komplette Bitmap, die in imgUri zu finden ist.
     * @return Die geladene Bitmap, falls es eine gibt. Sonst null.
     */
    public Bitmap loadBitmap() {
        if (ImagesAllowed) {
            if (!imgUri.equals("")) {
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
