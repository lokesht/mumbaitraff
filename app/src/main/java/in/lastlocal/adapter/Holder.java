package in.lastlocal.adapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 17-Jun-15.
 */
public class Holder {
    public static class GroupItem {
        public String title;
        public List<ChildItem> items = new ArrayList<ChildItem>();
    }

    public  static class ChildItem {
        public String title;
        public String phones;
        public String lebel1;
        public String lebel2;
    }

    public static class ChildHolder {
        public TextView title;
        public TextView tvNo1;
        public TextView tvNo2;
        public TextView tvNo3;
    }

    public  static class GroupHolder {
        public TextView title;
    }

}
