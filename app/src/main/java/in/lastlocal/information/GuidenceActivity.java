package in.lastlocal.information;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.afollestad.materialdialogs.ThemeSingleton;

import in.lastlocal.information.fragment.GuidenceDialogFragment;
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

        int accentColor = ThemeSingleton.get().widgetColor;
        if (accentColor == 0)
            accentColor = getResources().getColor(R.color.material_teal_500);

        GuidenceDialogFragment.create(false, accentColor)
                .show(getSupportFragmentManager(), "Dialog");

    }
}
