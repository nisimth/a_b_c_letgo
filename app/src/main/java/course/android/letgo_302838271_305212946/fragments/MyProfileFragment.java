package course.android.letgo_302838271_305212946.fragments;


import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MyFavoritesFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MySellingFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MySoldFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    private Context context ;

    private View.OnClickListener onMySellingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MySellingFragment mySellingFragment = new MySellingFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, mySellingFragment);
            t.commit();

        }
    };

    private View.OnClickListener onMySoldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MySoldFragment mySoldFragment = new MySoldFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, mySoldFragment);
            t.commit();

        }
    };

    private View.OnClickListener onMyFavoritesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyFavoritesFragment myFavoritesFragment = new MyFavoritesFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, myFavoritesFragment);
            t.commit();

        }
    };

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        Button mySellingBtn= (Button) rootView.findViewById(R.id.selling_btn);
        mySellingBtn.setOnClickListener(onMySellingClickListener);

        Button mySoldBtn= (Button) rootView.findViewById(R.id.sold_btn);
        mySoldBtn.setOnClickListener(onMySoldClickListener);

        Button myFavoritesBtn= (Button) rootView.findViewById(R.id.favorites_btn);
        myFavoritesBtn.setOnClickListener(onMyFavoritesClickListener);

        return rootView;
    }

}
