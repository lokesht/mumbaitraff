package in.lastlocal.information;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 21-Jun-15.
 */
public class GuidenceActivity extends FragmentActivity {

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidlines);
    }

    public void onCustomThemeFragmentClick(final View view) throws Exception {
     //   final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this);
//                .setNotices(R.raw.notices)
//                .setShowFullLicenseText(false)
//                .setIncludeOwnLicense(true)
//                .setThemeResourceId(R.style.custom_theme)
//                .setDividerColorRes(R.color.custom_divider_color)
//                .build();

   //     fragment.show(getSupportFragmentManager(), null);
    }
}
