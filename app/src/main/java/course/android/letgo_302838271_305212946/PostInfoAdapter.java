package course.android.letgo_302838271_305212946;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import course.android.letgo_302838271_305212946.core.PostInfo;

/**
 * Created by Nisim on 06/12/2017.
 */

public class PostInfoAdapter extends ArrayAdapter <PostInfo> {
    Context context;
    private List <PostInfo> postsList;
    private ArrayList<PostInfo> arraylist;

    public PostInfoAdapter(@NonNull Context context, @LayoutRes int resource, List <PostInfo> postsList ) {
        super(context, resource);
        this.context = context;
        this.postsList = postsList;
        this.arraylist = new ArrayList<PostInfo>();
        this.arraylist.addAll(postsList);
    }

    @Override
    public int getCount(){return postsList.size();}

    @Override
    public PostInfo getItem(int position) {
        return postsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.post_item_list_layout,null,false);

        ImageView img = (ImageView) itemView.findViewById(R.id.post_img_txtview);
        TextView txt = (TextView) itemView.findViewById(R.id.item_info_txtview);
        EditText et = (EditText) itemView.findViewById(R.id.search_viewtxt);

        PostInfo post = postsList.get(position);

        img.setImageResource(post.getImgId());
        txt.setText(post.getName());




        return itemView;



    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arraylist.clear();
        if (charText.length() == 0) {
            arraylist.addAll(arraylist);
        } else {
            for (PostInfo wp : arraylist) {
                if (wp.getTag().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    arraylist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
