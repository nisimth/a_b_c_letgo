package course.android.letgo_302838271_305212946;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_302838271_305212946.core.PostInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private ListView list;
    private List <PostInfo> postsList;


    public HomeFragment() {
        postsList = new ArrayList<PostInfo>();

        PostInfo p1 = new PostInfo("a",R.drawable.a);
        PostInfo p2 = new PostInfo("b",R.drawable.b);
        PostInfo p3 = new PostInfo("b",R.drawable.c);
        PostInfo p4 = new PostInfo("b",R.drawable.d);
        PostInfo p5 = new PostInfo("b",R.drawable.e);
        PostInfo p6 = new PostInfo("b",R.drawable.f);
        postsList.add(p1);
        postsList.add(p2);
        postsList.add(p3);
        postsList.add(p4);
        postsList.add(p5);
        postsList.add(p6);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        list = (ListView) rootView.findViewById(R.id.posts_list_view);
        PostInfoAdapter adapter = new PostInfoAdapter(context,R.layout.post_item_list_layout,postsList);
        list.setAdapter(adapter);

        return rootView;
    }

}
