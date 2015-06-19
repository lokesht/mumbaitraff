package in.lastlocal.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import in.lastlocal.map.WebViewNearByPolice;
import in.lastlocal.mumbaitraffic.R;

public class MainInformation extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_information);
        initialise();
    }

    private void initialise() {
        mToolbar = (Toolbar) findViewById(R.id.inc_tool_bar);
        setSupportActionBar(mToolbar);

        Button btn = (Button)findViewById(R.id.btnWebNearByPoliceStation);
        btn.setOnClickListener(this);

        Button btFAq = (Button)findViewById(R.id.btnFAQ);
        btFAq.setOnClickListener(this);

        Button btn_emergency = (Button)findViewById(R.id.btn_emergency_contact);
        btn_emergency.setOnClickListener(this);

        Button bt_offence = (Button)findViewById(R.id.btn_offence_penalties);
        bt_offence.setOnClickListener(this);

        Button bt_sign = (Button)findViewById(R.id.btn_sign);
        bt_sign.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent in;

        switch (v.getId()) {
            case R.id.btnWebNearByPoliceStation:
                 in = new Intent(this, WebViewNearByPolice.class);
                startActivity(in);
                break;

            case R.id.btnFAQ:
                 in = new Intent(this, FAQActivity.class);
                startActivity(in);
                break;

            case R.id.btn_emergency_contact:
                //in = new Intent(this, EmergencyContactActivity.class);
                //startActivity(in);
                break;

            case R.id.btn_offence_penalties:
                in = new Intent(this, FAQActivity.class);
                startActivity(in);
                break;

            case R.id.btn_sign:
                in = new Intent(this, FAQActivity.class);
                startActivity(in);
                break;

            case R.id.btn_licencing:
                in = new Intent(this, FAQActivity.class);
                startActivity(in);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
