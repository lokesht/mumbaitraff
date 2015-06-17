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
        public String hint;
    }

    public static class ChildHolder {
        public TextView title;
        public TextView hint;
    }

    public  static class GroupHolder {
        public TextView title;
    }

}
