package course.android.letgo_302838271_305212946.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.database.MyInfoManager;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.EditPostDialogFragment;
import course.android.letgo_302838271_305212946.fragments.MyProfileRelatedFragments.MySellingFragment;
import course.android.letgo_302838271_305212946.fragments.PostInfoDialogFragment;
import course.android.letgo_302838271_305212946.interfaces.ItemClickListener;

/**
 * Created by user on 05/01/2018.
 */

public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {
    private List<PostInfo> posts ;
    private Fragment hostedFragment;
    private Context context ;


    public MyPostRecyclerViewAdapter(List<PostInfo> posts, Fragment hostedFragment) {
        this.posts = posts;
        this.hostedFragment = hostedFragment;
    }

    @Override
    public MyPostRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mypost_recycler_item_layout,parent,false);
        return new MyPostRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPostRecyclerViewAdapter.ViewHolder holder, int position) {
        final PostInfo post = posts.get(position);
        holder.setData(post);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDialogFragment(post);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {

        //private TextView title , tag ;
        private ImageView image ;
        private PostInfo data ;
        private ItemClickListener itemClickListener;
        /////////////
        private ImageButton deletePostBtn;
        ////////////

        public ViewHolder(View itemView) {
            super(itemView);
            //title = (TextView)itemView.findViewById(R.id.post_title_txtview);
            //tag = (TextView)itemView.findViewById(R.id.post_tag_txtview);
            image = (ImageView)itemView.findViewById(R.id.post_img_imgView);
            itemView.setOnClickListener(this);
            deletePostBtn = (ImageButton) itemView.findViewById(R.id.delete_post_btn);
            deletePostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer deletedPosts = MyInfoManager.getInstance().deletePostFromDB(data);
                    if ( deletedPosts != 0 ){
                        Toast.makeText(context,"Post has deleted",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context,"Post has not deleted",Toast.LENGTH_LONG).show();
                    }
                }
            });
            ImageButton editPostBtn = (ImageButton) itemView.findViewById(R.id.edit_post_btn);
            editPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditPostDialogFragment editPostFragment = new EditPostDialogFragment();
                    editPostFragment.setTargetFragment(hostedFragment,1);
                    MyInfoManager.getInstance().setEditPost(data);
                    Activity act = (Activity) context;
                    editPostFragment.show(act.getFragmentManager(), "editPostDialogFragment");
                    //pullDataToEditPostDialogFragment(data);
                }
            });

        }

        public void setData(PostInfo data){
            this.data = data ;
            //title.setText(data.getTitle());
            //tag.setText(data.getTag());
            image.setImageBitmap(data.getImg());
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }


    }


    private void openDialogFragment(PostInfo data){
        // bundle data
        Bundle b = new Bundle();
        b.putString("AMOUNT_KEY",data.getItemPrice());
        b.putString("CURRENCY_KEY",data.getItemPriceCurrency());
        b.putString("DESC_KEY",data.getContent());
        b.putString("TAG_KEY",data.getTag());
        b.putByteArray("IMAGE_KEY",data.getImgByteArray());


        PostInfoDialogFragment fragment = new PostInfoDialogFragment();
        fragment.setArguments(b);

        Activity act = (Activity)context;
        fragment.show(act.getFragmentManager(),"editPostDialogFragment");
    }

    /*private void pullDataToEditPostDialogFragment(PostInfo data){
            Bundle b = new Bundle();
            b.putString("AMOUNT_KEY",data.getItemPrice());
            b.putString("CURRENCY_KEY",data.getItemPriceCurrency());
            b.putString("DESC_KEY",data.getContent());
            b.putString("TAG_KEY",data.getTag());
            b.putByteArray("IMAGE_KEY",data.getImgByteArray());


            EditPostDialogFragment fragment = new EditPostDialogFragment();
            fragment.setArguments(b);

            Activity act = (Activity)context;
            fragment.show(act.getFragmentManager(),"editPostDialogFragment");

    }*/


}
