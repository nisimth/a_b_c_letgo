package course.android.letgo_302838271_305212946.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import course.android.letgo_302838271_305212946.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificatiosFragment extends Fragment {
    private Context context;

    public NotificatiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_notificatios, container, false);
        ImageButton goBackBtn = (ImageButton)rootView.findViewById(R.id.exist_setting_btn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                HomeFragment home = new HomeFragment();
                t.replace(R.id.root_view,home);
                t.addToBackStack(null);
                t.commit();
            }
        });

        return rootView;
    }

}
