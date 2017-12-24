package course.android.letgo_302838271_305212946.adapters;

import android.app.Activity;
import android.content.Context;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.EditPostInfoFragment;

/**
 * Created by user on 24/12/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private List<PostInfo> posts ;
    private Fragment hostedFragment;
    private Context context ;

    public HomeRecyclerViewAdapter(List<PostInfo> posts, Fragment hostedFragment) {
        this.posts = posts;
        this.hostedFragment = hostedFragment;
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_recycler_item_layout,parent,false);
        return new HomeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewAdapter.ViewHolder holder, int position) {
        PostInfo post = posts.get(position);
        holder.setData(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title , tag ;
        private PostInfo data;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.post_title_txtview);
            tag = (TextView)itemView.findViewById(R.id.post_tag_txtview);

           /* ImageButton addpostBtn = (ImageButton)itemView.findViewById(R.id.add_new_post_btn);

            addpostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditPostInfoFragment editPostInfoFragment = new EditPostInfoFragment();
                    editPostInfoFragment.setTargetFragment(hostedFragment,1);
                    MyInfoManager.getInstance().setEditPost(data);
                    Activity act = (Activity) context ;
                    editPostInfoFragment.show(act.getFragmentManager(), "EditPostInfoFragment" );
                }
            });*/

        }

        public void setData(PostInfo data){
            this.data = data ;
            title.setText(data.getTitle());
            tag.setText(data.getTag());
        }
    }


}