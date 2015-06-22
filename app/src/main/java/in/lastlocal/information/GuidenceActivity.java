package in.lastlocal.information;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.ThemeSingleton;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.framework.OnMyListItemClickListener;
import in.lastlocal.information.fragment.FAQFragment;
import in.lastlocal.information.fragment.FAQFragmentMarathi;
import in.lastlocal.information.fragment.GuidelineFragment;
import in.lastlocal.information.fragment.GuidelineFragmentMarathi;
import in.lastlocal.information.fragment.GuidenceDialogFragment;
import in.lastlocal.mumbaitraffic.R;


/**
 * Created by USER on 21-Jun-15.
 */
public class GuidenceActivity extends AppCompatActivity implements OnMyListItemClickListener {
    private int optionSelected = AppConstant.ANIMATE_X;
    private GuidelineFragment mFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_faq);

        if (savedInstanceState == null) {
            mFirstFragment = new GuidelineFragment();
            addFragment(mFirstFragment);
        }
    }

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

            FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, mFirstFragment, new GuidelineFragmentMarathi(), R.id.fl_fragment_cantainer);
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

    public void onCustomThemeFragmentClick(final View view, boolean isEng, int position) {

        int accentColor = ThemeSingleton.get().widgetColor;
        if (accentColor == 0)
            accentColor = getResources().getColor(R.color.material_teal_500);

        GuidenceDialogFragment.create(false, accentColor, isEng, position)
                .show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onListItemClick(View v, int position, boolean isEng) {
        onCustomThemeFragmentClick(v, isEng, position);

    }
}
