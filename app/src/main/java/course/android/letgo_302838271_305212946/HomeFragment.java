package course.android.letgo_302838271_305212946;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;



import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;


import course.android.letgo_302838271_305212946.core.PostInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{

    private Context context;

    private GridView gridView;
    private List <PostInfo> postsList;
    EditText editText;


    public HomeFragment() {
        postsList = new ArrayList<PostInfo>();

        PostInfo p1 = new PostInfo("a","adidas",R.drawable.a);
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
        postsList.add(p12);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();


        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        PostInfoAdapter adapter = new PostInfoAdapter(context,R.layout.post_item_list_layout,postsList);

        gridView = (GridView) rootView.findViewById(R.id.posts_list_view);


        gridView.setAdapter(adapter);
        return rootView;
    }

}


