package course.android.letgo_302838271_305212946.database;

import android.content.Context;

import java.util.List;

import course.android.letgo_302838271_305212946.core.PostInfo;

/**
 * Created by Nisim on 13/12/2017.
 */

public class MyInfoManager {

    private static MyInfoManager instance;

    private PostsDataBase postsDataBase;

    private PostInfo editPost;

    public static MyInfoManager getInstance(){
        if(instance==null){
            instance = new MyInfoManager();
        }
        return instance;
    }

    private MyInfoManager(){

    }

    public void openDataBase (Context context){
        postsDataBase = new PostsDataBase(context);
        postsDataBase.open();
    }

    public void closeDataBase(){
        if(postsDataBase!=null){
            postsDataBase.close();
        }

    }
    public boolean addNewPost(PostInfo postInfo){
        return postsDataBase.addNewPostInfo(postInfo);
    }

    /*public boolean deletePost(PostInfo postInfo){
        return  postsDataBase.deletPost(postInfo);
    }*/

    public Integer deletePostFromDB(PostInfo post){
        return postsDataBase.deletePost(post);
    }

    public List<PostInfo> getAllPosts(){
        return postsDataBase.getAllPosts();
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

    public void updatePost(PostInfo post) {
        if(postsDataBase!=null){
            postsDataBase.updatePost(post);
        }
    }

    public void setEditPost(PostInfo editPost) {
        this.editPost = editPost;
    }
}
