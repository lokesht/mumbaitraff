package in.lastlocal.mumbaitraffic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

import in.lastlocal.DailogFragment.DialogEmergencyName;
import in.lastlocal.constant.AppConstant;
import in.lastlocal.widget_sos.SendSmsActivity;


public class EmergencySMS extends AppCompatActivity implements DialogEmergencyName.NoticeDialogListener {

    // Declare
    static final int PICK_CONTACT = 1;

    private String ownName = "Own Name";
    private String contactName = "Name";
    private String contact = "Contact";
    Button btnClicked;

    /** */
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_sms);
        sharedpreferences = getSharedPreferences(AppConstant.CONTACT_PREFERENCE, Context.MODE_PRIVATE);

        initialise();
    }

    private void initialise() {
        Button btn = (Button) findViewById(R.id.btn_con1);
        Button btn2 = (Button) findViewById(R.id.btn_con2);
        Button btn3 = (Button) findViewById(R.id.btn_con3);
        Button btnName = (Button)findViewById(R.id.btn_request_name_entry);

        int i = 0;
        Map<String, ?> keys = sharedpreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            String strKey = entry.getKey();

            if (strKey.equalsIgnoreCase("0")) {

            } else if (strKey.equalsIgnoreCase("1")) {
                String name = entry.getValue().toString();
                btnName.setText(name);
            } else {
                int buttonId = Integer.parseInt(strKey);
                String print = entry.getValue().toString().replace("#", " ");

                switch (buttonId) {
                    case 2:
                        btn.setText(print);
                        break;
                    case 3:
                        btn2.setText(print);
                        break;
                    case 4:
                        btn3.setText(print);
                        break;
                }
            }

        }
    }

    public void onContactSelect1(View v) {
        btnClicked = (Button) v;
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        //Toast.makeText(this, "has phone " + hasPhone, Toast.LENGTH_SHORT).show();
                        if (hasPhone != null && hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            contact = phones.getString(phones.getColumnIndex("data1"));

                            if (phones != null && !phones.isClosed())
                                phones.close();
                        }
                    }

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    int id = 2;
                    switch (btnClicked.getId()) {
                        case R.id.btn_con1:
                            id = 2;
                            break;
                        case R.id.btn_con2:
                            id = 3;
                            break;
                        case R.id.btn_con3:
                            id = 4;
                            break;
                    }

                    editor.putString(id + "", contact + "#" + contactName);
                    editor.apply();

                    btnClicked.setText(contactName + " " + contact);
                    //Toast.makeText(this, contactName + contact, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onCreateShortCut(View v) {
        //Adding shortcut for MainActivity on Home screen

        Intent shortcutIntent = new Intent(getApplicationContext(), SendSmsActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Send SMS");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.home_logo));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

    public void onRequestName(View v)
    {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DialogEmergencyName();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");

    }

    public void onReset(View v) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        Button btn = (Button) findViewById(R.id.btn_con1);
        btn.setText(R.string.btn_emergency_contact1);

        Button btn2 = (Button) findViewById(R.id.btn_con2);
        btn2.setText(R.string.btn_emergency_contact2);

        Button btn3 = (Button) findViewById(R.id.btn_con3);
        btn3.setText(R.string.btn_emergency_contact3);

        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
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

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(String s) {
        if (s != null && s.length() > 0) {
            SharedPreferences.Editor edit = sharedpreferences.edit();
            edit.putString("1", s);
            edit.apply();

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

            Button btn = (Button)findViewById(R.id.btn_request_name_entry);
            btn.setText(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
