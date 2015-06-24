package in.lastlocal.information.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.lastlocal.adapter.SignListViewAdapter;
import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.model.ItemSign;
import in.lastlocal.mumbaitraffic.R;

/**
 * Created by USER on 21-Jun-15.
 */
public class SignFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context context;
    private String[] signs_english;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignFragment newInstance() {
        SignFragment fragment = new SignFragment();
        return fragment;
    }

    public SignFragment() {
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

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign, container, false);
        onSetUP(v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
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

        List<ItemSign> itemSigns = new ArrayList<ItemSign>();
        signs_english = getResources().getStringArray(R.array.signs_english);
        for(int i=1;i<signs_english.length+1;i++) {
            ItemSign item = new ItemSign("image"+i+".png", signs_english[i-1]);
            itemSigns.add(item);
        }
        GridView lv =(GridView)v.findViewById(R.id.listView);
        SignListViewAdapter adapter = new SignListViewAdapter(getActivity(),itemSigns);
        lv.setAdapter(adapter);
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