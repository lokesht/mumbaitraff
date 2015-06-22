package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.lastlocal.adapter.EmergancyContactAdapter;
import in.lastlocal.adapter.ExampleAdapter;
import in.lastlocal.constant.AppConstant;
import in.lastlocal.customview.AnimatedExpandableListView;
import in.lastlocal.framework.OnFragmentInteractionListener;


import in.lastlocal.adapter.Holder.*;
import in.lastlocal.mumbaitraffic.DBHelper;
import in.lastlocal.mumbaitraffic.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyContactFragmentMarathi extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AnimatedExpandableListView listView;
    private EmergancyContactAdapter adapter;

    private Context context;
    private Locale mLocale;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyContactFragmentMarathi newInstance() {
        EmergencyContactFragmentMarathi fragment = new EmergencyContactFragmentMarathi();
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
        List<GroupItem> items = new ArrayList<GroupItem>();

        String selGroup = "SELECT Branch_Id, Branch FROM EmergencyNoMA GROUP BY Branch_Id";
        String selChild = "SELECT Name, Numbers FROM EmergencyNoMA where Branch_Id = ";

        DBHelper db = new DBHelper(context);
        Cursor c=  db.executeStatement(selGroup);
        if(c!=null && c.getCount()>0)
        {
            c.moveToFirst();
            do{
                GroupItem item = new GroupItem();
                item.title = c.getString(1);


                String selC = selChild+c.getString(0);

                Cursor curChild =  db.executeStatement(selC);
                if(curChild!=null && curChild.getCount()>0)
                {
                    curChild.moveToNext();
                    do{
                        ChildItem child = new ChildItem();
                        child.title = curChild.getString(0).trim();//+" "+curChild.getString(1);
                        child.phones = curChild.getString(1).trim();

                        item.items.add(child);
                    }while (curChild.moveToNext());
                }

                items.add(item);
            }while(c.moveToNext());
            if(!c.isClosed())
                c.close();

            db.close();
        }else
        {

        }
        adapter = new EmergancyContactAdapter(getActivity());
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
