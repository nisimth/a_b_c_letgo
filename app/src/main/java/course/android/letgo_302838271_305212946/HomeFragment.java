package course.android.letgo_302838271_305212946;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.List;


import course.android.letgo_302838271_305212946.adapters.PostInfoAdapter;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.EditPostInfoFragment;
import course.android.letgo_302838271_305212946.interfaces.CallBackListiner;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CallBackListiner{

    private Context context;

    private GridView postsListView;
    private List <PostInfo> postsList;
    PostInfoAdapter adapter;


    public HomeFragment() {
        postsList = MyInfoManager.getInstance().getAllPosts();
        /*PostInfo p1 = new PostInfo("a","adidas",R.drawable.a);
        PostInfo p2 = new PostInfo("b","nike",R.drawable.b);
        PostInfo p3 = new PostInfo("c","puma",R.drawable.c);
        PostInfo p4 = new PostInfo("d","nike",R.drawable.d);
        PostInfo p5 = new PostInfo("e","nike",R.drawable.e);
        PostInfo p6 = new PostInfo("f","nike",R.drawable.f);
        PostInfo p7 = new PostInfo("g","adidas",R.drawable.g);
        PostInfo p8 = new PostInfo("h","nike",R.drawable.h);
        PostInfo p9 = new PostInfo("i","puma",R.drawable.i);
        PostInfo p10 = new PostInfo("j","adidas",R.drawable.j);
        PostInfo p11 = new PostInfo("k","adidas",R.drawable.k);
        PostInfo p12 = new PostInfo("l","nike",R.drawable.l);
        postsList.add(p1);
        postsList.add(p2);
        postsList.add(p3);
        postsList.add(p4);
        postsList.add(p5);
        postsList.add(p6);
        postsList.add(p7);
        postsList.add(p8);
        postsList.add(p9);
        postsList.add(p10);
        postsList.add(p11);
        postsList.add(p12);*/
    }


    private View.OnClickListener selectItemListener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            EditPostInfoFragment fragment = new EditPostInfoFragment();
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


        postsListView = (GridView) rootView.findViewById(R.id.posts_list_view);
        ImageButton addNewPostBtn= (ImageButton) rootView.findViewById(R.id.add_new_post_btn);









        addNewPostBtn.setOnClickListener(new View.OnClickListener(){

            //open camera to shoot a picture and to add it to the data base
            @Override
            public void onClick(View v) {

                EditPostInfoFragment fragment = new EditPostInfoFragment();
                fragment.setTargetFragment(HomeFragment.this,0);
                Activity act = (Activity) context;
                fragment.show(act.getFragmentManager(), " edtpostinfodialog");
                initData();
            }
        });



       initData();
        return rootView;
    }



    private void initData() {
        postsList = MyInfoManager.getInstance().getAllPosts();
        if (postsList != null && postsList.size() > 0) {
            adapter = new PostInfoAdapter(context, R.layout.post_item_list_layout, postsList);
            postsListView.setAdapter(adapter);
        }
    }
        @Override
        public void saveButtonOnClicked () {
            initData();
        }
    }

//
