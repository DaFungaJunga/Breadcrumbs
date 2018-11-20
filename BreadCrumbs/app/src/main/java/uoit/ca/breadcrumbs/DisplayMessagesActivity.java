package uoit.ca.breadcrumbs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayMessagesActivity extends AppCompatActivity {
    private String userIp;
    Button nextPage;
    Button refresh;
    TextView text;
    TextView timePosted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_messages);

        nextPage = (Button) findViewById(R.id.button2);
        refresh = (Button) findViewById(R.id.button3);
        IpChecker ip = new IpChecker();
        IpTemp IpT = new IpTemp();
        ip.execute();
        userIp = IpT.getIp();
        getMessages();
        nextPage.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick (View v){
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                clear();
                getMessages();
            }
        });}
    /**
     * clear the table of messages
     */
    public void clear(){
        TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);
        ll.removeAllViews();
    }
    /**
     * Check if the device is currently connected to wifi or if it is enabled
     * @return true or false if connected
     */
    private boolean notConnectedToWifi(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return !(activeNetwork != null && activeNetwork.isConnected() && activeNetwork.getType() != ConnectivityManager.TYPE_WIFI);
    }
    /**
     * Get all relevant messages from the database to display
     */
    public void getMessages() {
        try
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Breadcrumb");
            //  myRef.child(userIp.replace(".", "")).orderByValue();
            myRef.child(userIp.replace(".", "")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);
                    int counter = 0;
                    for (DataSnapshot dateLayer : (dataSnapshot.getChildren())) {
                        String message = (String) dateLayer.child("message").getValue();
                        String expirationTime = (String) dateLayer.child("stringExpirationTime").getValue();
                        String properDate = (String) dateLayer.child("properDate").getValue();
                        Log.i(MainActivity.class.getSimpleName(), "message: " + message + " time: " + expirationTime+"proper Date: "+properDate);
                        try {
                            Date currentDate = new Date();
                            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
                            String date = df.format(currentDate);
                            double newCurr = Double.parseDouble(date.trim());
                            double dateMessage =Double.parseDouble(expirationTime);

                            //Date newCurr = df.parse("-"+ date);
                            // Date dateMessage = df.parse("-"+expirationTime);

                            if (newCurr<-dateMessage) {
                                TableRow row = new TableRow(DisplayMessagesActivity.this);
                                if ((counter % 2)==0) {
                                    row.setBackgroundResource(android.R.color.darker_gray);
                                } else {
                                    row.setBackgroundResource(android.R.color.white);
                                }
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);
                                text = new TextView(DisplayMessagesActivity.this);
                                timePosted = new TextView(DisplayMessagesActivity.this);
                                text.setText(message);
                                text.setTextColor(Color.BLACK);
                                timePosted.setText(properDate);
                                timePosted.setTextColor(Color.BLACK);
                                timePosted.setGravity(Gravity.CENTER|Gravity.BOTTOM);
                                row.addView(timePosted);
                                row.addView(text);
                                ll.addView(row, counter);
                                Log.i(MainActivity.class.getSimpleName(), "New Post");
                                counter++;
                            }
                            else{
                                Log.i(MainActivity.class.getSimpleName(), "Old Post");
                                //return;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.i(MainActivity.class.getSimpleName(), "Failed to read value.", error.toException());
                }
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

