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

    public List<PostInfo> getAllPosts(){
        return postsDataBase.getAllPosts();
    }
}
