package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import in.lastlocal.adapter.FAQAdapter;
import in.lastlocal.adapter.GuidelineListViewAdapter;
import in.lastlocal.adapter.Holder;
import in.lastlocal.constant.AppConstant;
import in.lastlocal.customview.AnimatedExpandableListView;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.framework.OnMyListItemClickListener;
import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 21-Jun-15.
 */
public class GuidelineFragmentMarathi extends Fragment {

    private OnMyListItemClickListener mListener;
    private Context context;
    private Locale mLocale;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuidelineFragmentMarathi newInstance() {
        GuidelineFragmentMarathi fragment = new GuidelineFragmentMarathi();
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
        View v = inflater.inflate(R.layout.fragment_guidline, container, false);
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

    private void onSetUP(View v) {
        ListView lv = (ListView) v.findViewById(R.id.listView);
        String[] arrGroupItem = getResources().getStringArray(R.array.arr_guidence_head);

        List<String> list = new ArrayList<String>();
        list = Arrays.asList(arrGroupItem);
        GuidelineListViewAdapter adapter = new GuidelineListViewAdapter(getActivity(), list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mListener.onListItemClick(view, position, false);

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
            mListener = (OnMyListItemClickListener) activity;
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