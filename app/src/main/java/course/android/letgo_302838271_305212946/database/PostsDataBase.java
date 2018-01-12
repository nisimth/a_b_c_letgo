package course.android.letgo_302838271_305212946.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import course.android.letgo_302838271_305212946.core.PostInfo;

/**
 * Created by Nisim on 13/12/2017.
 */

public class PostsDataBase extends SQLiteOpenHelper  {


    private static final String DATABASE_NAME = "myletgodatabase";
    private static final int VERSION =4;

    private Context context;
    private SQLiteDatabase db;

    private static final String POSTS_TABLE = "post";
    private static final String POST_ID ="id";
    private static final String LETGO_ID ="letgoId";
    private static final String POST_TITLE = "title";
    private static final String POST_CONTENT = "content";
    private static final String POST_ITEM_PRICE = "itemPrice";
    private static final String POST_ITEM_PRICE_CURRENCY = "itemPriceCurrency";
    private static final String POST_TAG = "tag";
    private static final String POST_IMG = "img";
    //private static final String POST_LIKE_OR_NOT = "like"; /// new change

    public static final  String[] POSTS_COLUMNS =
            { POST_ID, POST_TITLE, POST_CONTENT, POST_ITEM_PRICE,POST_ITEM_PRICE_CURRENCY , POST_TAG, POST_IMG, LETGO_ID };

    public PostsDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPostsTableSqL = "create table if not exists " + POSTS_TABLE + "(" +
                POST_ID + " text primary key, "
                + POST_TITLE + " text, "
                + POST_CONTENT + " text, "
                + POST_ITEM_PRICE + " text, "
                + POST_ITEM_PRICE_CURRENCY + " text, "
                + POST_TAG + " text, "
                + POST_IMG + " blob, "
                + LETGO_ID + " text"

                + ")";
        db.execSQL(createPostsTableSqL);
    }


    public void open(){
        db =  getWritableDatabase();

    }
    public void close(){
        if(db != null){db.close();}


    }


    public boolean updatePost(PostInfo post) {

        long result = -1;
        ContentValues values = new ContentValues();
        values.put(POST_ID, post.getId());
        values.put(POST_TITLE, post.getTitle());
        values.put(POST_CONTENT, post.getContent());
        values.put(POST_ITEM_PRICE, post.getItemPrice());
        values.put(POST_ITEM_PRICE_CURRENCY, post.getItemPriceCurrency());
        values.put(POST_TAG, post.getTag());
        Bitmap image = post.getImg();
        if (image != null) {
            byte[] img= PostInfo.getImgByteArray(image);
            if(img!=null && img.length>0){
                values.put(POST_IMG, img);
            }
        }
        values.put(LETGO_ID, post.getLetgo_id());
        //values.put(POST_LIKE_OR_NOT, post.getLikeOrNot());/// new change


        String [] whereArg = {post.getId()};
        result = db.update(POSTS_TABLE,values, POST_ID + "=?", whereArg);
        if(result > 0){
            return true;
        }
        return false;
    }



    public boolean addNewPostInfo(PostInfo post){
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(POST_ID,post.getId());
        values.put(POST_TITLE,post.getTitle());
        values.put(POST_CONTENT,post.getContent());
        values.put(POST_ITEM_PRICE,post.getItemPrice());
        values.put(POST_ITEM_PRICE_CURRENCY, post.getItemPriceCurrency());
        values.put(POST_TAG,post.getTag());


        Bitmap postImg = post.getImg();
        if (postImg != null) {
            byte[] data = PostInfo.getImgByteArray(postImg);
            if (data != null && data.length > 0) {
                values.put(POST_IMG, data);
            }
        }
        values.put(LETGO_ID,post.getLetgo_id());
        //values.put(POST_LIKE_OR_NOT, post.getLikeOrNot());/// new change

        result = db.insert(POSTS_TABLE,null,values);

        if (result > 0){
            return true;
        }
        return false;
    }


    public Boolean deletePost (PostInfo post){
        String [] whereArg = {post.getId()};
        int result = db.delete(POSTS_TABLE, " id = ? ",whereArg )  ;
        if(result>0){
            return true;
        }
        return false;
    }

    public int updatePostImage(PostInfo post) {

        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();

            //images
            if (post.getImg() != null) {
                byte[] data = PostInfo.getImgByteArray(post.getImg());
                if (data != null && data.length > 0) {
                    values.put(POST_IMG, data);
                }
            }
            else{
                values.putNull(POST_IMG);
            }

            // update
            cnt = db.update(POSTS_TABLE, values, POST_ID + " = ?",
                    new String[] { post.getId() });
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return  cnt;
    }

    // get all posts stored in the data base
    public List<PostInfo> getAllPosts(){
        List<PostInfo> result = new ArrayList<PostInfo>();
        Cursor cursor=null;
        try {

            cursor = db.query( POSTS_TABLE, POSTS_COLUMNS, null, null , null , null , null );
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    // id, title, content, itemPrice, itePriceCurrency, tag, img, letgoId
                    PostInfo post = new PostInfo();
                    post.setId(cursor.getString(0));
                    post.setTitle(cursor.getString(1));
                    post.setContent(cursor.getString(2));
                    post.setItemPrice(cursor.getString(3));
                    post.setItemPriceCurrency(cursor.getString(4));
                    post.setTag(cursor.getString(5));
                    post.setImgFromByteArray(cursor.getBlob(6));
                    post.setLetgo_id(cursor.getString(7));
                    //post.setLikeOrNot(cursor.getString(8)); // new change
                    result.add(post);
                    cursor.moveToNext();
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();

            }
        }
        return result;
    }

    public List<PostInfo> getPostsByTag (String tagFromUser){
        List<PostInfo> result = new ArrayList<PostInfo>();
        Cursor cursor=null;
        String [] tag = {tagFromUser};
        try {
            //cursor = db.query(POSTS_TABLE, POSTS_COLUMNS, POST_TAG , tag , null, null,null);

            String query = "Select * FROM " + POSTS_TABLE + " WHERE " + POST_TAG + " =  \"" + tagFromUser + "\"";
            SQLiteDatabase db = this.getWritableDatabase();

            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PostInfo post = new PostInfo();
                    post.setId(cursor.getString(0));
                    post.setTitle(cursor.getString(1));
                    post.setContent(cursor.getString(2));
                    post.setItemPrice(cursor.getString(5));
                    post.setItemPriceCurrency(cursor.getString(6));
                    post.setTag(cursor.getString(3));
                    post.setImgFromByteArray(cursor.getBlob(4));
                    post.setLetgo_id(cursor.getString(7));
                   // post.setLikeOrNot(cursor.getString(8)); // new change
                    result.add(post);
                    cursor.moveToNext();
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();

            }
        }
        return result;
    }

    public List<PostInfo> getAllMyPosts(){
        List<PostInfo> results = new ArrayList<PostInfo>();
        Cursor cursor = null;
        try{
            String whereArgs[] = {MyInfoManager.getInstance().getMyUserId()};
            String query = "Select * FROM " + POSTS_TABLE + " WHERE " + LETGO_ID + " =  \"" + whereArgs + "\"";
            //cursor = db.query(POSTS_TABLE, POSTS_COLUMNS, LETGO_ID+"=?",whereArgs, null,null,null);

            SQLiteDatabase db = this.getWritableDatabase();

            cursor = db.rawQuery(query, null);
            if(cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PostInfo post = new PostInfo();
                    post.setId(cursor.getString(0));
                    post.setTitle(cursor.getString(1));
                    post.setContent(cursor.getString(2));
                    post.setItemPrice(cursor.getString(5));
                    post.setItemPriceCurrency(cursor.getString(6));
                    post.setTag(cursor.getString(3));
                    post.setImgFromByteArray(cursor.getBlob(4));
                    post.setLetgo_id(cursor.getString(7));
                    //post.setLikeOrNot(cursor.getString(8)); // new change
                    results.add(post);
                    cursor.moveToNext();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return results;
    }

  /*  public List<PostInfo> getAllFavoritePosts (String like){
        List<PostInfo> result = new ArrayList<PostInfo>();
        Cursor cursor=null;
        String [] xxx = {like};
        try {
            //   cursor = db.query(POSTS_TABLE, POSTS_COLUMNS, POST_TAG , tag , null, null,null);

            String query = "Select * FROM " + POSTS_TABLE + " WHERE " + POST_LIKE_OR_NOT + " =  \"" + like + "\"";
            SQLiteDatabase db = this.getWritableDatabase();

            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PostInfo post = new PostInfo();
                    post.setId(cursor.getString(0));
                    post.setTitle(cursor.getString(1));
                    post.setContent(cursor.getString(2));
                    post.setItemPrice(cursor.getString(3));
                    post.setItemPriceCurrency(cursor.getString(4));
                    post.setTag(cursor.getString(5));
                    post.setImgFromByteArray(cursor.getBlob(6));
                    post.setLikeOrNot(cursor.getString(7)); // new change
                    result.add(post);
                    cursor.moveToNext();
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();

            }
        }
        return result;
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
