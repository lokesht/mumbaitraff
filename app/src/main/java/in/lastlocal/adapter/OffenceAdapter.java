package in.lastlocal.adapter;

/**
 * Created by USER on 17-Jun-15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.lastlocal.adapter.Holder.ChildHolder;
import in.lastlocal.adapter.Holder.GroupHolder;
import in.lastlocal.adapter.Holder.GroupItem;
import in.lastlocal.customview.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import in.lastlocal.mumbaitraffic.R;

/**
 * Adapter for our list of {@link GroupItem}s.
 */
public class OffenceAdapter extends AnimatedExpandableListAdapter {

    private LayoutInflater inflater;
    private List<GroupItem> items;

    public OffenceAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<GroupItem> items) {
        this.items = items;
    }

    @Override
    public Holder.ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        Holder.ChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_list_offence, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            holder.tvNo1 = (TextView) convertView.findViewById(R.id.tv_no1);
            holder.tvNo2 = (TextView) convertView.findViewById(R.id.tv_no2);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.title.setText(item.title);
        holder.tvNo1.setText(item.lebel1);
        holder.tvNo2.setText(item.lebel2);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        GroupItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.item_group_offences, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        holder.title.setText(item.title);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}


