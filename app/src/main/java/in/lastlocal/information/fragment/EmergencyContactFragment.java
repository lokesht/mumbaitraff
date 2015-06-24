package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;

import android.util.DisplayMetrics;
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

import in.lastlocal.adapter.EmergancyContactAdapter;
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
public class EmergencyContactFragment extends Fragment {

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
    public static EmergencyContactFragment newInstance() {
        EmergencyContactFragment fragment = new EmergencyContactFragment();
        return fragment;
    }

    public EmergencyContactFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {

        }
    }



//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        mExpandableListView.setIndicatorBounds(mExpandableListView.getRight()- 40, mExpandableListView.getWidth());
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // getActivity().getActionBar().setHomeButtonEnabled(true);

        context = getActivity();

        // Required empty public constructor
        mLocale = new Locale(AppConstant.LOCALE_ENGLISH);
        Locale.setDefault(mLocale);

        //mLocale = new Locale(AppConstant.LOCALE_ENGLISH);
        //Locale.setDefault(mLocale);

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
            case R.id.homeAsUp:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = new MenuInflater(getActivity().getApplicationContext());
//        menu.clear();
//
//        //super.onPrepareOptionsMenu(menu);
//        inflater.inflate(R.menu.menu_faq_information, menu);
//    }

    private void onSetUP(View v) {
        List<GroupItem> items = new ArrayList<GroupItem>();

        String selGroup = "SELECT Branch_Id, Branch FROM EmergencyNoEN GROUP BY Branch_Id";
        String selChild = "SELECT Name, Numbers FROM EmergencyNoEN where Branch_Id = "+"";

        DBHelper db = new DBHelper(context);
        Cursor c=  db.executeStatement(selGroup);
        if(c!=null && c.getCount()>0)
        {
            c.moveToFirst();
            do{
                GroupItem item = new GroupItem();
                item.title = c.getString(1).trim();

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
        listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            listView.setIndicatorBounds(metrics.widthPixels- 50, listView.getWidth());
        } else {
            listView.setIndicatorBoundsRelative(metrics.widthPixels- 50, listView.getWidth());
        }

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
