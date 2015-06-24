package in.lastlocal.dailogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 22-Jun-15.
 */
public class DialogGuidenceFragment extends DialogFragment {

    public static DialogGuidenceFragment create(boolean darkTheme, int accentColor) {
        DialogGuidenceFragment dialog = new DialogGuidenceFragment();
        Bundle args = new Bundle();
        args.putBoolean("dark_theme", darkTheme);
        args.putInt("accent_color", accentColor);
        dialog.setArguments(args);
        return dialog;
    }

    public static DialogGuidenceFragment create(boolean darkTheme, int accentColor, boolean isEng, int position) {
        DialogGuidenceFragment dialog = new DialogGuidenceFragment();
        Bundle args = new Bundle();
        args.putBoolean("dark_theme", darkTheme);
        args.putInt("accent_color", accentColor);

        args.putInt("position", position);
        args.putBoolean("isEng",isEng);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View customView;
        try {
            customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
        } catch (InflateException e) {
            throw new IllegalStateException("This device does not support Web Views.");
        }

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .theme(getArguments().getBoolean("dark_theme") ? Theme.DARK : Theme.LIGHT)
                .title(R.string.title_dialog_guidline)
                .customView(customView, false)
                .positiveText(R.string.btn_close)
                .build();

        final WebView webView = (WebView) customView.findViewById(R.id.webview);
        try {
            // Load from changelog.html in the assets folder
            StringBuilder buf = new StringBuilder();

            String strFileName = "html/lrn_";
            boolean temp = getArguments().getBoolean("isEng");
            int pos = getArguments().getInt("position");

            strFileName =  temp ? strFileName+(pos+1):strFileName+"ma"+(pos+1);

            Toast.makeText(getActivity(),strFileName,Toast.LENGTH_SHORT).show();

            InputStream json = getActivity().getAssets().open(strFileName+".html");
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null)
                buf.append(str);
            in.close();

           // buf.substring()

            // Inject color values for WebView body background and links
            final int accentColor = getArguments().getInt("accent_color");
            webView.loadData(buf.toString()
                    .replace("{style-placeholder}", getArguments().getBoolean("dark_theme") ?
                            "body { background-color: #444444; color: #fff; }" :
                            "body { background-color: #EDEDED; color: #000; }")
                    .replace("{link-color}", colorToHex(shiftColor(accentColor, true)))
                    .replace("{link-color-active}", colorToHex(accentColor))
                    , "text/html", "UTF-8");
        } catch (Throwable e) {
            webView.loadData("<h1>Unable to load</h1><p>" + e.getLocalizedMessage() + "</p>", "text/html", "UTF-8");
        }

        return dialog;
    }

    private String colorToHex(int color) {
        return Integer.toHexString(color).substring(2);
    }

    private int shiftColor(int color, boolean up) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (up ? 1.1f : 0.9f); // value component
        return Color.HSVToColor(hsv);
    }
}