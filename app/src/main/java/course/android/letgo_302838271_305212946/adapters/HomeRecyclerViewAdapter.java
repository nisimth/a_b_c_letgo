package course.android.letgo_302838271_305212946.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import course.android.letgo_302838271_305212946.R;
import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.fragments.PostInfoFragmentDialog;
import course.android.letgo_302838271_305212946.interfaces.ItemClickListener;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //private TextView title , tag ;
        private ImageView image ;
        private PostInfo data ;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            //title = (TextView)itemView.findViewById(R.id.post_title_txtview);
            //tag = (TextView)itemView.findViewById(R.id.post_tag_txtview);
            image = (ImageView)itemView.findViewById(R.id.post_img_imgView);
            itemView.setOnClickListener(this);
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

    // open item in dialog fragment
    private void openDialogFragment(PostInfo data){
        // bundle data
        Bundle b = new Bundle();
        b.putString("AMOUNT_KEY",data.getItemPrice());
        b.putString("CURRENCY_KEY",data.getItemPriceCurrency());
        b.putString("DESC_KEY",data.getContent());
        b.putString("TAG_KEY",data.getTag());
        b.putByteArray("IMAGE_KEY",data.getImgByteArray());


        PostInfoFragmentDialog fragment = new PostInfoFragmentDialog();
        fragment.setArguments(b);

        Activity act = (Activity)context;
        fragment.show(act.getFragmentManager(),"postinfodialog");

    }



}
