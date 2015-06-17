package in.lastlocal.information.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.lastlocal.framework.OnFragmentInteractionListener;
import in.lastlocal.mumbaitraffic.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainInformationFragment extends Fragment implements View.OnClickListener{

    View v;
    private OnFragmentInteractionListener mListener;

    public MainInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_main_information, container, false);

        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        MenuInflater inflater = new MenuInflater(getActivity().getApplicationContext());
        menu.clear();

        inflater.inflate(R.menu.menu_faq_information, menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnNearPolice  = (Button)v.findViewById(R.id.btnWebNearByPoliceStation);
        btnNearPolice.setOnClickListener(this);

        Button btnFAQ  = (Button)v.findViewById(R.id.btnFAQ);
        btnFAQ.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (activity instanceof OnFragmentInteractionListener)
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

    @Override
    public void onClick(View v) {
        mListener.onFragmentInteraction(v);
    }
}
