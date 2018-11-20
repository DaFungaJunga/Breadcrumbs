package uoit.ca.breadcrumbs;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Breadcrumb {
    public String message, location,properDate;
    private int duration;
    private Date expirationTime;

    Breadcrumb(){
        this.message="";
        this.location= "";
        this.duration=0;
        this.expirationTime= new Date();
    }

    /**
     * Set the message of this object
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * set the duration of the message
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get the users public IP address and set it as location
     *
     */
    public void setLocation(){
        IpChecker ip = new IpChecker();
        IpTemp IpT = new IpTemp();
        try {
            ip.execute();
            Log.i(MainActivity.class.getSimpleName(),this.location);
            this.location = IpT.getIp();
        }
        catch(Exception except){
            except.printStackTrace();
        }
    }

    /**
     * Set the Expiration Time to the current date
     */
    public void setExpirationTime(){
        this.expirationTime = new Date(System.currentTimeMillis()+duration*60*1000);
    }

    /**
     * Return location (IP)
     * @return ip
     */
    public String getLocation(){
        return location;
    }

    /**
     * Convert Expiration Time to a String
     * @return
     */
    public String getStringExpirationTime(){
        try {
            DateFormat df = new SimpleDateFormat("-yyyyMMddHHmmss", Locale.ENGLISH);
            String date = df.format(getExpirationTime());
            return date;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Format the current date
     * @return the current date
     */
    public String getProperDate(){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            String date = df.format(getExpirationTime());
            return date;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get the Expiration Date of the message
     * @return Expiration Date
     */
    public Date getExpirationTime(){
        return expirationTime;
    }

}