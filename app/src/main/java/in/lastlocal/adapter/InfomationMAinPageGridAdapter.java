package in.lastlocal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import in.lastlocal.information.EmergencyContactActivity;
import in.lastlocal.information.FAQActivity;
import in.lastlocal.information.GuidenceActivity;
import in.lastlocal.information.MainInformation;
import in.lastlocal.information.OffencesActivity;
import in.lastlocal.information.SignActivity;
import in.lastlocal.information.UserManulActivity;
import in.lastlocal.map.WebViewNearByPolice;
import in.lastlocal.model.ItemGridInformation;
import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 20-Jun-15.
 */
public class InfomationMAinPageGridAdapter extends ArrayAdapter<ItemGridInformation> {
    Context context;
    int layoutResourceId;
    ArrayList<ItemGridInformation> data = new ArrayList<ItemGridInformation>();

    public InfomationMAinPageGridAdapter(Context context, int layoutResourceId,
                                         ArrayList<ItemGridInformation> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, null);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.tv_info_text);
            holder.imageItem = (CircleButton) row.findViewById(R.id.cb_icon_background);

            int h = context.getResources().getDisplayMetrics().heightPixels;
            //int w = context.getResources().getDisplayMetrics().widthPixels;
            //context.getResources().getDisplayMetrics().

            // holder.image.setLayoutParams(new LayoutParams(h-50,h-50));
            // ((Activity) context).getWindow();

            //int width = .getMeasuredWidth() - window.getPaddingLeft() - window.getPaddingRight();
            //int height = window.getMeasuredHeight() - window.getPaddingTop() - window.getPaddingBottom();

           // WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
           // Display display = wm.getDefaultDisplay();

            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // metrics.heightPixels;
            //  metrics.widthPixels;
            int status = 0;
            int margin = 0;
            int resId = context.getResources().getIdentifier("status_bar_height",
                    "dimen",
                    "android");
            if (resId > 0) {
                status = context.getResources().getDimensionPixelSize(resId);
            }
            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }

            margin = status + actionBarHeight;
            // int margin = context.getResources().getInteger(R.integer.margin);

            //Toast.makeText(context, margin + " " + status + " " + actionBarHeight, Toast.LENGTH_SHORT).show();

            row.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (metrics.heightPixels - margin) / 3));
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        ItemGridInformation item = data.get(position);
        holder.txtTitle.setText(item.getMenuItemName());
        holder.imageItem.setImageResource(item.getMenuIcon());

        if (position >= 3 && position <= 5) {
            holder.imageItem.setColor(context.getResources().getColor(R.color.yellow));
            //setBackgroundColor();
        } else if (position == 6) {

            holder.imageItem.setColor(context.getResources().getColor(R.color.green));
        }
        else if(position ==7 || position ==8)
        {
            holder.imageItem.setColor(context.getResources().getColor(R.color.white));
            holder.txtTitle.setText("");
        }

        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context,v.getTag()+" ABC ", Toast.LENGTH_SHORT).show()
                int id = Integer.parseInt(v.getTag().toString());
                Intent in;
                switch (id) {
                    case 0:
                        in = new Intent(context, EmergencyContactActivity.class);
                        context.startActivity(in);
                        break;
                    case 1:
                        in = new Intent(context, WebViewNearByPolice.class);
                        context.startActivity(in);
                        break;
                    case 2:
                        in = new Intent(context, UserManulActivity.class);
                        context.startActivity(in);
                        break;
                    case 3:
                        in = new Intent(context, OffencesActivity.class);
                        context.startActivity(in);
                        break;
                    case 4:
                        in = new Intent(context, GuidenceActivity.class);
                        context.startActivity(in);
                        break;
                    case 5:
                        in = new Intent(context, SignActivity.class);
                        context.startActivity(in);
                        break;
                    case 6:
                        in = new Intent(context, FAQActivity.class);
                        context.startActivity(in);
                        break;
                }
            }
        });
        holder.imageItem.setTag(position + "");
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        CircleButton imageItem;
    }
}