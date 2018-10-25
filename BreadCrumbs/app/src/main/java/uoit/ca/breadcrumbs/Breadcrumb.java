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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

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
    public void setExpirationTime(){
        this.expirationTime = new Date(System.currentTimeMillis()+duration*60*1000);
    }
    public String getLocation(){
        return location;
    }
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
    public Date getExpirationTime(){
        return expirationTime;
    }

}