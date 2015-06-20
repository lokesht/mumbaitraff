package in.lastlocal.widget_sos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.mumbaitraffic.EmergencySMS;

public class SendSmsActivity extends Activity {

     /** */
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /** */
        sharedpreferences = getSharedPreferences(AppConstant.CONTACT_PREFERENCE, Context.MODE_PRIVATE);
        onSendMessage(null);

        intentToLoc();
    }



    public void onSendMessage(View v) {

        String con1, conName1, con2, conName2, con3, conName3;
        String[] t1;
        con1 = sharedpreferences.getString("2", "");
        con2 = sharedpreferences.getString("3", "");
        con3 = sharedpreferences.getString("4", "");

        if (con1.length() == 0 && con2.length() == 0 && con3.length() == 0) {
            Toast.makeText(this, "No contact saved", Toast.LENGTH_SHORT).show();
            return;
        }

        if (con1.length() > 10) {
            t1 = con1.split("#");
            con1 = t1[0];
            conName1 = t1[1];

            if (con1.length() > 9) {
                send(con1, conName1 + " message ");
            }
        }

        if (con2.length() > 10) {
            t1 = con2.split("#");
            con2 = t1[0];
            conName2 = t1[1];
            if (con2.length() > 9) {
                send(con2, conName2 + " message ");
            }
        }

        if (con3.length() > 10) {
            t1 = con3.split("#");
            con3 = t1[0];
            conName3 = t1[1];
            if (con3.length() > 9) {
                send(con3, conName3 + " message ");
            }
        }
    }

    private void send(String contact, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contact, null, msg, null, null);

        Toast.makeText(this, msg + " messgae Send", Toast.LENGTH_SHORT).show();
    }

    public void intentToLoc()
    {
        Intent in = new Intent(this, EmergencySMS.class);
        startActivity(in);
    }
}
