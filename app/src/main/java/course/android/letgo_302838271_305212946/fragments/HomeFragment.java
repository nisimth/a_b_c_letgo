package course.android.letgo_302838271_305212946.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import org.json.JSONObject;

import java.util.List;


import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.adapters.HomeRecyclerViewAdapter;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;
import course.android.letgo_302838271_305212946.network.utils.NetworkConnector;
import course.android.letgo_302838271_305212946.network.utils.NetworkResListener;
import course.android.letgo_302838271_305212946.network.utils.ResStatus;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CallBackListiner, NetworkResListener{

    private RecyclerView homeRecyclerView;
    private Context context;
    private ProgressBar progressBar;


    private ImageButton carsBtn , techBtn , homeBtn , leisureBtn ,
            motorsBtn , fashionBtn , childBtn , entertaimentBtn , otherBtn ;
    private ImageButton loveBtn ;
    private ImageButton searchBtn;
    private EditText searchText;

    public HomeFragment() {

    }


    private View.OnClickListener selectItemListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            AddNewPostDialogFragment fragment = new AddNewPostDialogFragment();
            fragment.setTargetFragment(HomeFragment.this,0);
            Activity act = (Activity) context;
            fragment.show(act.getFragmentManager(), " addNewPostDialogFragment");
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);

        NetworkConnector.getInstance().updatePostsFeed(this);
        Button addNewPostBtn = (Button) rootView.findViewById(R.id.add_new_post_btn);
        addNewPostBtn.setOnClickListener(new View.OnClickListener(){
            //open dialog fragment (AddNewPostDialogFragment)
            @Override
            public void onClick(View v) {
                AddNewPostDialogFragment fragment = new AddNewPostDialogFragment();
                fragment.setTargetFragment(HomeFragment.this,0);
                Activity act = (Activity) context;
                fragment.show(act.getFragmentManager(), "addNewPostDialogFragment");
                initData();
            }
        });
//////////////////////////cars Btn////////////////////////////////////////
        carsBtn = (ImageButton) rootView.findViewById(R.id.car_tag_btn);
        carsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Cars");

            }
        });

        //////////////////////////tech Btn////////////////////////////////////////
        techBtn = (ImageButton) rootView.findViewById(R.id.tech_tag_btn);
        techBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Tech");

            }
        });

        //////////////////////////home Btn////////////////////////////////////////
        homeBtn = (ImageButton) rootView.findViewById(R.id.home_tag_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Home");

            }
        });

        //////////////////////////leisure Btn////////////////////////////////////////
        leisureBtn = (ImageButton) rootView.findViewById(R.id.leisure_tag_btn);
        leisureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Leisure");

            }
        });

        //////////////////////////motors Btn////////////////////////////////////////
        motorsBtn = (ImageButton) rootView.findViewById(R.id.motors_tag_btn);
        motorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Motors");

            }
        });

        //////////////////////////child Btn////////////////////////////////////////
        childBtn = (ImageButton) rootView.findViewById(R.id.child_tag_btn);
        childBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Child");

            }
        });

        //////////////////////////fashion Btn////////////////////////////////////////
        fashionBtn = (ImageButton) rootView.findViewById(R.id.fation_tag_btn);
        fashionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Fashion");

            }
        });

        //////////////////////////Entertainment Btn////////////////////////////////////////
        entertaimentBtn = (ImageButton) rootView.findViewById(R.id.entertainment_tag_btn);
        entertaimentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Entertainment");

            }
        });

        //////////////////////////other Btn////////////////////////////////////////
        otherBtn = (ImageButton) rootView.findViewById(R.id.other_tag_btn);
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByTag("Other");

            }
        });

        searchText = (EditText) rootView.findViewById(R.id.search_viewtxt);
        searchBtn = (ImageButton) rootView.findViewById(R.id.search_img_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataByKeyword(searchText.getText().toString());
            }
        });
       /* loveBtn = (ImageButton) rootView.findViewById(R.id.love_btn);
        loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAllFavoritePost("true");
            }
        });*/

        initData();
        return rootView;
    }

/////////////////////////////////////////////////////////////////////////////////////
   private void initDataByTag(String tag){
       List<PostInfo> posts = MyInfoManager.getInstance().getPostsByTag(tag);
        if( posts != null && posts.size() > 0 ){
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
            LinearLayoutManager ll = new LinearLayoutManager(context);
            ll.setOrientation(LinearLayoutManager.VERTICAL);

            GridLayoutManager gl = new GridLayoutManager(context,3);

            homeRecyclerView.setLayoutManager(gl);
            homeRecyclerView.setAdapter(adapter);
        }
    }
 ////////////////////////////////////////////////////////////////////////////////////////

    private void initData() {
        List<PostInfo> posts = MyInfoManager.getInstance().getAllPosts();
        if( posts != null && posts.size() > 0 ){
           HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
           LinearLayoutManager ll = new LinearLayoutManager(context);
           ll.setOrientation(LinearLayoutManager.VERTICAL);

           GridLayoutManager gl = new GridLayoutManager(context,3);

           homeRecyclerView.setLayoutManager(gl);
           homeRecyclerView.setAdapter(adapter);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    private void initDataByKeyword(String keyword) {
        List<PostInfo> posts = MyInfoManager.getInstance().getPostsByKeyword(keyword);
        if( posts != null && posts.size() > 0 ){
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
            LinearLayoutManager ll = new LinearLayoutManager(context);
            ll.setOrientation(LinearLayoutManager.VERTICAL);

            GridLayoutManager gl = new GridLayoutManager(context,3);

            homeRecyclerView.setLayoutManager(gl);
            homeRecyclerView.setAdapter(adapter);
        }
    }

   /* private void initAllFavoritePost(String like){
        List<PostInfo> posts = MyInfoManager.getInstance().getAllFavoritePosts(like);
        if( posts != null && posts.size() > 0 ){
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(posts,this);
            LinearLayoutManager ll = new LinearLayoutManager(context);
            ll.setOrientation(LinearLayoutManager.VERTICAL);

            GridLayoutManager gl = new GridLayoutManager(context,2);

            homeRecyclerView.setLayoutManager(gl);
            homeRecyclerView.setAdapter(adapter);
        }
    }*/
        @Override
        public void saveButtonOnClicked () {
            initData();
        }

    @Override
    public void onPreUpdate() {

    }

    @Override
    public void onPostUpdate(byte[] res, ResStatus status) {

    }

    @Override
    public void onPostUpdate(JSONObject res, ResStatus status) {
        progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
        MyInfoManager.getInstance().updatePosts(res);

        initData();
    }

    @Override
    public void onPostUpdate(Bitmap res, ResStatus status) {

    }
}

//
