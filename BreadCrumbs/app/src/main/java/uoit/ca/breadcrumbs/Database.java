package uoit.ca.breadcrumbs;


import java.util.ArrayList;


/**
 * Created by shoma on 2016-12-26.
 */

public class Database {
    private ArrayList<Breadcrumb> aL= new ArrayList<>();

    public void addMessage(String mEdit, int np){
        try {
            Breadcrumb userMessage = new Breadcrumb();
            userMessage.setMessage(mEdit);
            userMessage.setLocation();
            userMessage.setDuration(np);
            userMessage.setExpirationTime();
            //userMessage.sendData();

            aL.add(userMessage);

        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }
    public ArrayList message(){
        return aL;
    }

}
