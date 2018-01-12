package course.android.letgo_302838271_305212946.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_302838271_305212946.core.PostInfo;
import course.android.letgo_302838271_305212946.network.utils.NetworkConnector;
import course.android.letgo_302838271_305212946.network.utils.NetworkResListener;
import course.android.letgo_302838271_305212946.network.utils.ResStatus;

/**
 * Created by Nisim on 13/12/2017.
 */

public class MyInfoManager implements NetworkResListener {

    private static MyInfoManager instance;
    private PostsDataBase postsDataBase;
    private Context context;


    private String myUserId = "letgo";

    private PostInfo editPost;

    public static MyInfoManager getInstance(){
        if(instance==null){
            instance = new MyInfoManager();
        }
        return instance;
    }

    private MyInfoManager(){

    }



    public String getMyUserId() {
        return myUserId;
    }
    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public void openDataBase (Context ctx){
        this.context = ctx;
        postsDataBase = new PostsDataBase(context);
        postsDataBase.open();
    }

    public void closeDataBase(){
        if(postsDataBase!=null){
            postsDataBase.close();
        }

    }
    public void addNewPost(PostInfo postInfo){
        if(postsDataBase!=null) {
            if(postsDataBase.addNewPostInfo(postInfo)) {
                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_POST_REQ, postInfo, instance);
            }

        }
    }



    public Boolean deletePostFromDB(PostInfo post){
        boolean result = false;
        if(postsDataBase!=null) {
            result=postsDataBase.deletePost(post);
            if(result){
                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_POST_REQ, post, instance);
            }
        }
        return result;
    }

    public List<PostInfo> getAllPosts(){
        List<PostInfo> list = new ArrayList<PostInfo>();
        if(postsDataBase!=null) {
            list = postsDataBase.getAllPosts();
        }
        return list;

    }

    public List<PostInfo> getAllMyPosts(){
        List<PostInfo> list = new ArrayList<PostInfo>();
        if(postsDataBase!=null) {
            list = postsDataBase.getAllMyPosts();
        }
        return list;

    }
    public List<PostInfo> getPostsByTag(String tag){
        return postsDataBase.getPostsByTag(tag);
    }

   /* public List<PostInfo> getAllFavoritePosts(String like){
        return postsDataBase.getAllFavoritePosts(like);
    }*/

    public PostInfo getEditPost() {
        return editPost;
    }

    // update single post
    public boolean updatePost(PostInfo post) {
        boolean result = false;
        if(postsDataBase!=null) {
            result =postsDataBase.updatePost(post);
            if(result){
                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_POST_REQ, post, instance);
            }
        }
        return result;
    }

    // update all posts
    public void updatePosts(JSONObject res) {
        if(postsDataBase==null){
            return;
        }
        List<PostInfo> posts = PostInfo.parseJson(res);
        for(PostInfo post:posts){
            if(!postsDataBase.addNewPostInfo(post)) {
                postsDataBase.updatePost(post);
            }
        }
    }


    public void updatePostImage(PostInfo post){
        if(post.getImg()!=null) {
            String itemId = post.getId();
            if(postsDataBase!=null){
                postsDataBase.updatePostImage(post);
            }
        }
    }
    public void setEditPost(PostInfo editPost) {
        this.editPost = editPost;
    }



    @Override
    public void onPreUpdate() {
        Toast.makeText(context,"Sync stated...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUpdate(byte[] res, ResStatus status) {
        Toast.makeText(context,"Sync finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUpdate(JSONObject res, ResStatus status) {
        if(res!=null){
            Toast.makeText(context,"Sync finished...status " + res.toString(),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Sync finished...status " + status.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPostUpdate(Bitmap res, ResStatus status) {

    }

}
