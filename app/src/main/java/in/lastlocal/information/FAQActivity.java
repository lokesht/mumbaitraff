package in.lastlocal.information;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.information.fragment.FAQFragment;
import in.lastlocal.information.fragment.FAQFragmentMarathi;
import in.lastlocal.mumbaitraffic.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FAQActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    Toolbar mToolbar;
    private int optionSelected =  AppConstant.ANIMATE_X;;

    private FAQFragment mFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_faq);

        // initialise();
        if (savedInstanceState == null) {
            mFirstFragment = new FAQFragment();
            addFragment(mFirstFragment);
        }
    }

//    private void initialise() {
//        mToolbar = (Toolbar) findViewById(R.id.inc_tool_bar);
//        setSupportActionBar(mToolbar);

    //setAc
//    }

    public void addFragment(Fragment mFirstFragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment_cantainer, mFirstFragment);
        fragmentTransaction.commit();
    }

    /* Overloaded Method*/
    public void replaceFragment(Fragment fragment, Bundle savedInstancState) {
        replaceFragment(fragment, savedInstancState, true);
    }

    //To replace fragments
    public void replaceFragment(Fragment fragment, Bundle savedInstancState, boolean isAdd) {
        FragmentManager fragmentManager = getFragmentManager();
        if (savedInstancState != null)
            fragment.setArguments(savedInstancState);

        if (savedInstancState == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (isAdd)
                transaction.addToBackStack("TAG");

            transaction.replace(R.id.fl_fragment_cantainer, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(View v) {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, mFirstFragment, new FAQFragmentMarathi(), R.id.fl_fragment_cantainer);
            fragmentTransactionExtended.addTransition(optionSelected);
            fragmentTransactionExtended.commit();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        }
        super.onBackPressed();
    }
}
