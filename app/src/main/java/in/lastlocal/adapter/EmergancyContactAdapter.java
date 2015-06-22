package in.lastlocal.adapter;

/**
 * Created by USER on 17-Jun-15.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.lastlocal.adapter.Holder.ChildHolder;
import in.lastlocal.adapter.Holder.GroupHolder;
import in.lastlocal.adapter.Holder.GroupItem;
import in.lastlocal.customview.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import in.lastlocal.mumbaitraffic.R;

/**
 * Adapter for our list of {@link GroupItem}s.
 */
public class EmergancyContactAdapter extends AnimatedExpandableListAdapter {

    private LayoutInflater inflater;
    private List<GroupItem> items;
    Context context;

    public EmergancyContactAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
        final ChildHolder holder;
        final Holder.ChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_list_contact_emergency, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);

            holder.tvNo1 = (TextView) convertView.findViewById(R.id.tv_no1);
            holder.tvNo2 = (TextView) convertView.findViewById(R.id.tv_no2);
            holder.tvNo3 = (TextView) convertView.findViewById(R.id.tv_no3);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.title.setText(item.title);


        String[] array = item.phones.split(",");
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                holder.tvNo1.setText(array[i]);
                holder.tvNo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + holder.tvNo1.getText().toString().trim()));
                        context.startActivity(call);
                    }
                });
                holder.tvNo2.setText("");
                holder.tvNo3.setText("");
                holder.tvNo2.setClickable(false);
                holder.tvNo3.setClickable(false);
            }

            if (i == 1) {
                holder.tvNo2.setText(array[i]);
                holder.tvNo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + holder.tvNo2.getText().toString().trim()));
                        context.startActivity(call);
                    }
                });
                holder.tvNo3.setText("");
                holder.tvNo3.setClickable(false);
            }

            if (i == 2) {
                holder.tvNo3.setText(array[i]);
                holder.tvNo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent call = new Intent(Intent.ACTION_DIAL);
                        call.setData(Uri.parse("tel:" + holder.tvNo3.getText().toString().trim()));
                        context.startActivity(call);
                    }
                });
            }
        }
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
            convertView = inflater.inflate(R.layout.item_group_faq, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }


//        // initialize ImageView
//        ImageView imgExpandCollapse = (ImageView) convertView.findViewById(R.id.imgExpandCollapse);
//
//        // check if GroupView is expanded and set imageview for expand/collapse-action
//        if(isExpanded){
//            imgExpandCollapse.setImageResource(R.drawable.ic_action_collapse);
//        }
//        else{
//            imgExpandCollapse.setImageResource(R.drawable.ic_action_expand);
//        }


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


