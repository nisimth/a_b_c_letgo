package course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import course.android.letgo_302838271_305212946.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySoldFragment extends Fragment {
    private Context context ;

    public MySoldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity() ;
        View rootView = inflater.inflate(R.layout.fragment_my_sold, container, false);
        return rootView ;
    }

}
