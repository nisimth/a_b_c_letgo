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
public class MyFavoritesFragment extends Fragment {

    private Context context ;

    public MyFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity() ;
        View rootView = inflater.inflate(R.layout.fragment_my_favorites, container, false);
        return rootView ;
    }

}
