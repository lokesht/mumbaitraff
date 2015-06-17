package in.lastlocal.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.information.fragment.FAQFragment;
import in.lastlocal.information.fragment.MainInformationFragment;
import in.lastlocal.map.WebViewNearByPolice;
import in.lastlocal.mumbaitraffic.R;

public class MainInformation extends AppCompatActivity  implements OnFragmentInteractionListener {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information);

        initialise();
        if(savedInstanceState ==null)
        {
            replaceFragment(new MainInformationFragment(),savedInstanceState);
        }
    }

    private void initialise()
    {
        mToolbar = (Toolbar)findViewById(R.id.inc_tool_bar);
        setSupportActionBar(mToolbar);
    }
    @Override
    public void onFragmentInteraction(View v) {
        switch (v.getId())
        {
            case R.id.btnWebNearByPoliceStation:
                Intent in = new Intent(this, WebViewNearByPolice.class);
                startActivity(in);
                break;

            case R.id.btnFAQ:
                replaceFragment(FAQFragment.newInstance(),null);
                break;
        }
    }

    //To replace fragments
    public void replaceFragment(Fragment fragment, Bundle savedInstancState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstancState != null)
            fragment.setArguments(savedInstancState);

        if (savedInstancState == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        }
        super.onBackPressed();
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
