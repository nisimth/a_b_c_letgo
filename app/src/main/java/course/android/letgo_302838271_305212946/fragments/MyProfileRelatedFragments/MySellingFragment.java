package course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments;


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
import android.widget.ImageButton;

import java.util.List;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.adapters.MyPostRecyclerViewAdapter;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.AddNewPostDialogFragment;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySellingFragment extends Fragment implements CallBackListiner {

    private Context context ;
    private RecyclerView myPostRecyclerView;

    public MySellingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_my_selling, container, false);
        myPostRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_post_recycler_view);


        initData();
        return rootView ;
    }

    private void initData() {
        List<PostInfo> posts = MyInfoManager.getInstance().getAllPosts();
        if( posts != null && posts.size() > 0 ){
            MyPostRecyclerViewAdapter adapter = new MyPostRecyclerViewAdapter(posts,this);
            LinearLayoutManager ll = new LinearLayoutManager(context);
            ll.setOrientation(LinearLayoutManager.VERTICAL);

            GridLayoutManager gl = new GridLayoutManager(context,2);

            myPostRecyclerView.setLayoutManager(gl);
            myPostRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void saveButtonOnClicked() {
        initData();
    }
}


