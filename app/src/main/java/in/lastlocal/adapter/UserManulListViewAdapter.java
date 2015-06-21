package in.lastlocal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.lastlocal.model.ItemSign;
import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 21-Jun-15.
 */
public class UserManulListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;

    List<ItemSign> lsAllElement;

    public UserManulListViewAdapter(Context context, List<ItemSign> lsAllElement) {
        this.lsAllElement = lsAllElement;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lsAllElement.size();
    }

    @Override
    public Object getItem(int position) {
        return lsAllElement.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_fragment_user_manul, null);
            vh.iv = (ImageView) convertView.findViewById(R.id.iv_image);
            vh.tv = (TextView) convertView.findViewById(R.id.tv_row_text);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(lsAllElement.get(position).getMsg());
        vh.iv.setImageResource(lsAllElement.get(position).getId());

        return convertView;
    }

    public static class ViewHolder {
        TextView tv;
        ImageView iv;
    }
}
