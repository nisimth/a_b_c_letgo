package course.android.letgo_302838271_305212946.fragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MyFavoritesFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MySellingFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MySoldFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.SettingFragment;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment implements CallBackListiner {

    private Context context ;
    private FragmentManager fm;

    private View.OnClickListener onMySellingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MySellingFragment mySellingFragment = new MySellingFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, mySellingFragment);
            t.addToBackStack(null);
            t.commit();

        }
    };

    private View.OnClickListener onMySoldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MySoldFragment mySoldFragment = new MySoldFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, mySoldFragment);
            t.addToBackStack(null);
            t.commit();

        }
    };

    private View.OnClickListener onMyFavoritesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyFavoritesFragment myFavoritesFragment = new MyFavoritesFragment();
            FragmentTransaction t = getChildFragmentManager().beginTransaction();
            t.replace(R.id.content_view, myFavoritesFragment);
            t.addToBackStack(null);
            t.commit();

        }
    };

    private View.OnClickListener onSettingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction t = fm.beginTransaction();
            SettingFragment settingFragment = new SettingFragment();
            t.replace(R.id.root_view,settingFragment);
            t.addToBackStack(null);
            t.commit();

        }
    };


    public MyProfileFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener selectItemListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            AddNewPostDialogFragment fragment = new AddNewPostDialogFragment();
            fragment.setTargetFragment(MyProfileFragment.this,0);
            Activity act = (Activity) context;
            fragment.show(act.getFragmentManager(), " addNewPostDialogFragment");
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        Button mySellingBtn = (Button) rootView.findViewById(R.id.selling_btn);
        mySellingBtn.setOnClickListener(onMySellingClickListener);

        Button mySoldBtn = (Button) rootView.findViewById(R.id.sold_btn);
        mySoldBtn.setOnClickListener(onMySoldClickListener);

        Button myFavoritesBtn = (Button) rootView.findViewById(R.id.favorites_btn);
        myFavoritesBtn.setOnClickListener(onMyFavoritesClickListener);

        ImageButton settingBtn = (ImageButton) rootView.findViewById(R.id.setting_btn);
        settingBtn.setOnClickListener(onSettingClickListener);

        Button addNewPostBtn = (Button) rootView.findViewById(R.id.add_new_post_btn);
        addNewPostBtn.setOnClickListener(new View.OnClickListener(){
            //open camera to shoot a picture and to add it to the data base
            @Override
            public void onClick(View v) {
                AddNewPostDialogFragment fragment = new AddNewPostDialogFragment();
                fragment.setTargetFragment(MyProfileFragment.this,0);
                Activity act = (Activity) context;
                fragment.show(act.getFragmentManager(), " addNewPostDialogFragment");

            }
        });

        MySellingFragment mySellingFragment = new MySellingFragment();
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.content_view, mySellingFragment);
        t.addToBackStack(null);
        t.commit();
        return rootView;
    }



    @Override
    public void saveButtonOnClicked() {

    }
}
