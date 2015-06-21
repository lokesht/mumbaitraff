package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import in.lastlocal.adapter.ExampleAdapter;
import in.lastlocal.adapter.Holder.ChildItem;
import in.lastlocal.adapter.Holder.GroupItem;
import in.lastlocal.constant.AppConstant;
import in.lastlocal.customview.AnimatedExpandableListView;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.mumbaitraffic.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    private Context context;
    private Locale mLocale;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQFragment newInstance() {
        FAQFragment fragment = new FAQFragment();
        return fragment;
    }

    public FAQFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Required empty public constructor
//        mLocale = new Locale(AppConstant.LOCALE_HINDI);
//        Locale.setDefault(mLocale);

        mLocale = new Locale(AppConstant.LOCALE_ENGLISH);
        Locale.setDefault(mLocale);

        context = getActivity();

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

//        context = getActivity();
//
//        //mLocale = new Locale(AppConstant.LOCALE_ENGLISH);
//        //Locale.setDefault(mLocale);
//
//        Configuration config = new Configuration();
//        config.locale = mLocale;
//        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
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

    private void setLocale() {

//        if (AppConstant.LOCALE_HINDI.equals(mLocale.toString())) {
            mLocale = new Locale(AppConstant.LOCALE_ENGLISH);
            Locale.setDefault(mLocale);
            Configuration config = new Configuration();
            config.locale = mLocale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            //setContentView(R.layout.activity_main);
        getActivity().setContentView(R.layout.fragment_faq);
            Log.d("LocaleTest", "if block");
//        }//if
//        else if (AppConstant.LOCALE_ENGLISH.equals(mLocale.toString())) {
//            mLocale = new Locale(AppConstant.LOCALE_HINDI);
//            Locale.setDefault(mLocale);
//            Configuration config = new Configuration();
//            config.locale = mLocale;
//            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//            // MainActivity.this.setContentView(R.layout.activity_main);
//            Log.d("LocaleTest", "else if block");
     //   }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();

        FragmentManager.BackStackEntry latestEntry = (FragmentManager.BackStackEntry) fragmentManager.getBackStackEntryAt(count - 1);
        String str = latestEntry.getName();
        Fragment fragment = fragmentManager.findFragmentByTag(str);
        if (fragment == null) {
            return;
        }

        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.detach(fragment);
        fragTransaction.attach(fragment);
        fragTransaction.commit();

        super.onConfigurationChanged(newConfig);
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

        // Populate our list with groups and it's children

        String[] arrGroupItem = getResources().getStringArray(R.array.arr_faq_question);
        String[] arrChildItem = getResources().getStringArray(R.array.arr_faq_ans);

        for (int i = 1; i < arrGroupItem.length; i++) {
            GroupItem item = new GroupItem();

            item.title = arrGroupItem[i];

            // for(int j = 0; j < i; j++) {
            ChildItem child = new ChildItem();
            child.title = arrChildItem[0];
            child.hint = "Too awesome";

            item.items.add(child);
            // }

            items.add(item);
        }

        adapter = new ExampleAdapter(getActivity());
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
