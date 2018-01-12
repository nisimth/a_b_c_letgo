package course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private Context context ;
    private ImageButton a ; ///

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        return rootView;
    }



}
