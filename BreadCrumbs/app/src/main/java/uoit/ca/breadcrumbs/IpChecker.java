package uoit.ca.breadcrumbs;

/**
 * Created by shoma on 2016-12-20.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpChecker extends AsyncTask<String,Void,String> {
    static String ip=null;
    @Override
     public String doInBackground(String... params)  {

        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            HttpURLConnection urlConnection = (HttpURLConnection) whatismyip.openConnection();
            urlConnection.setReadTimeout(200000);
            urlConnection.setConnectTimeout(200000);
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            ip = in.readLine();
            return ip;
        }
        catch(Exception except){
           except.printStackTrace();
            Log.i(MainActivity.class.getSimpleName(),"ip is null");
            return null;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   public void onPostExecute(String result){
       IpTemp TIp= new IpTemp();
       TIp.setIp(result);

   }
    public String getIp (){
        return ip;
    }

}