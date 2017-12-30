package course.android.letgo_302838271_305212946.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import java.util.List;


import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.adapters.HomeRecyclerViewAdapter;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CallBackListiner{

    private RecyclerView homeRecyclerView;
    private Context context;

    private ImageButton carsBtn , techBtn , homeBtn , leisureBtn ,
            motorsBtn , fashionBtn , childBtn , entertaimentBtn , otherBtn ;

    public HomeFragment() {

    }


    private View.OnClickListener selectItemListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            AddPostInfoFragment fragment = new AddPostInfoFragment();
            fragment.setTargetFragment(HomeFragment.this,0);
            Activity act = (Activity) context;
            fragment.show(act.getFragmentManager(), " edtpostinfodialog");
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);

        Button addNewPostBtn = (Button) rootView.findViewById(R.id.add_new_post_btn);
        addNewPostBtn.setOnClickListener(new View.OnClickListener(){
            //open camera to shoot a picture and to add it to the data base
            @Override
            public void onClick(View v) {
                AddPostInfoFragment fragment = new AddPostInfoFragment();
                fragment.setTargetFragment(HomeFragment.this,0);
                Activity act = (Activity) context;
                fragment.show(act.getFragmentManager(), " edtpostinfodialog");
                initData();
            }
        });
//////////////////////////////////////////////////////////////////////////
      /*  carsBtn = (ImageButton) rootView.findViewById(R.id.car_tag_btn);
        carsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag();
            }
        });*/
///////////////////////////////////////////////////////////////////////////
        initData();
        return rootView;
    }
/////////////////////////////////////////////////////////////////////////////////////
 /*   private void initDataByTag(){
        List<PostInfo> posts = MyInfoManager.getInstance().getPostsByTag( carsBtn.toString() );
        if( posts != null && posts.size() > 0 ){
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
            LinearLayoutManager ll = new LinearLayoutManager(context);
            ll.setOrientation(LinearLayoutManager.VERTICAL);

            GridLayoutManager gl = new GridLayoutManager(context,2);

            homeRecyclerView.setLayoutManager(gl);
            homeRecyclerView.setAdapter(adapter);
        }
    }*/
 ////////////////////////////////////////////////////////////////////////////////////////

    private void initData() {
        List<PostInfo> posts = MyInfoManager.getInstance().getAllPosts();
       if( posts != null && posts.size() > 0 ){
           HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
           LinearLayoutManager ll = new LinearLayoutManager(context);
           ll.setOrientation(LinearLayoutManager.VERTICAL);

           GridLayoutManager gl = new GridLayoutManager(context,2);

           homeRecyclerView.setLayoutManager(gl);
           homeRecyclerView.setAdapter(adapter);
       }
    }
        @Override
        public void saveButtonOnClicked () {
            initData();
        }
    }

//
