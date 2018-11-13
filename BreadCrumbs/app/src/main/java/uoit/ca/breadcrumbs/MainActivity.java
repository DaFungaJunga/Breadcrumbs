package uoit.ca.breadcrumbs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText mEdit;
    Button nextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IpChecker ip = new IpChecker();
        ip.execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NumberPicker np = (NumberPicker) findViewById(R.id.numberPickerDuration);
        np.setMinValue(1);
        np.setMaxValue(60);
        np.setWrapSelectorWheel(true);

        button = (Button) findViewById(R.id.button);
        mEdit=(EditText)findViewById(R.id.editText);
        mEdit.setTextColor(Color.BLACK);
        nextPage=(Button) findViewById(R.id.goToDisplay);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createMessage();
                goToDisplayMessages();
            }
            public void createMessage(){
                try {
                    FirebaseApp.initializeApp(getApplicationContext());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Breadcrumb");

                    Breadcrumb userMessage = new Breadcrumb();
                    userMessage.setMessage(mEdit.getText().toString());
                    userMessage.setLocation();
                    userMessage.setDuration(np.getValue());
                    userMessage.setExpirationTime();


                    myRef.child(userMessage.getLocation().replace(".","")).child(userMessage.getStringExpirationTime()).setValue(userMessage);
                }
                catch(Exception exception){
                    exception.printStackTrace();
                }
            }
            private void goToDisplayMessages() {
                Intent intent = new Intent(getApplicationContext(), DisplayMessagesActivity.class);
                startActivity(intent);
            }
            private boolean notConnectedToWifi(){
                final ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                return !(activeNetwork != null && activeNetwork.isConnected() && activeNetwork.getType() != ConnectivityManager.TYPE_WIFI);
            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplayMessagesActivity.class);
                startActivity(intent);
            }
        });
    }
}
