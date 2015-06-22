package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.lastlocal.adapter.FAQAdapter;
import in.lastlocal.adapter.Holder;
import in.lastlocal.constant.AppConstant;
import in.lastlocal.customview.AnimatedExpandableListView;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 21-Jun-15.
 */
public class FAQFragmentMarathi extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AnimatedExpandableListView listView;
    private FAQAdapter adapter;

    private Context context;
    private Locale mLocale;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignFragmentMarathi newInstance() {
        SignFragmentMarathi fragment = new SignFragmentMarathi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        /** before generating view changing config*/
        mLocale = new Locale(AppConstant.LOCALE_HINDI);
        Locale.setDefault(mLocale);

        Configuration config = new Configuration();
        config.locale = mLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_faq, container, false);
        onSetUP(v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_faq_information, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_language:
                mListener.onFragmentInteraction(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSetUP(View v) {
        List<Holder.GroupItem> items = new ArrayList<Holder.GroupItem>();

        // Populate our list with groups and it's children

        String[] arrGroupItem = getResources().getStringArray(R.array.arr_faq_question);
        String[] arrChildItem = getResources().getStringArray(R.array.arr_faq_ans);

        for (int i = 1; i < arrGroupItem.length; i++) {
            Holder.GroupItem item = new Holder.GroupItem();

            item.title = arrGroupItem[i];

            // for(int j = 0; j < i; j++) {
            Holder.ChildItem child = new Holder.ChildItem();
            child.title = arrChildItem[0];
            item.items.add(child);
            // }

            items.add(item);
        }

        adapter = new FAQAdapter(getActivity());
        adapter.setData(items);

        listView = (AnimatedExpandableListView) v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
    }

    public void onButtonPressed(View v) {
        if (mListener != null) {
            mListener.onFragmentInteraction(v);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
