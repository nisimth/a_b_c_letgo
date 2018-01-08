package course.android.letgo_302838271_305212946.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import course.android.letgo_302838271_305212946.core.PostInfo;

/**
 * Created by Nisim on 13/12/2017.
 */

public class PostsDataBase extends SQLiteOpenHelper  {


    private static final String DATABASE_NAME = "myletgodatabase";
    private static final int VERSION = 1;

    private SQLiteDatabase db;

    private static final String POSTS_TABLE = "post";
    private static final String POST_ID ="id";
    private static final String POST_TITLE = "title";
    private static final String POST_CONTENT = "content";
    private static final String POST_ITEM_PRICE = "price"; // add new column of price
    private static final String POST_ITEM_PRICE_CURRENCY = "priceCurrency"; // add new column of price currency
    private static final String POST_TAG = "tag";
    private static final String POST_IMG = "img";
    //private static final String POST_LIKE_OR_NOT = "like"; /// new change

    public static final  String[] POSTS_COLUMNS =
            { POST_ID,POST_TITLE,POST_CONTENT,POST_ITEM_PRICE,POST_ITEM_PRICE_CURRENCY,POST_TAG,POST_IMG };
    public PostsDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
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
                + POST_IMG + " blob "

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
        values.put(POST_IMG, post.getImgByteArray());
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
        //values.put(POST_LIKE_OR_NOT, post.getLikeOrNot());/// new change

        Bitmap postImg = post.getImg();
        if (postImg != null) {
            byte[] data = getBitmapAsByteArray(postImg);
            if (data != null && data.length > 0) {
                values.put(POST_IMG, data);
            }
        }

        result = db.insert(POSTS_TABLE,null,values);

        if (result > 0){
            return true;
        }
        return false;
    }
///////////////////////////////////////////////////////
    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
//////////////////////////////////////////////////////
    public Integer deletePost (PostInfo post){
        String [] whereArg = {post.getId()};
        return db.delete(POSTS_TABLE, " id = ? ",whereArg )  ;
    }

    // get all posts stored in the data base
    public List<PostInfo> getAllPosts(){
        List<PostInfo> result = new ArrayList<PostInfo>();
        Cursor cursor=null;
        try {
            cursor = db.query( POSTS_TABLE, POSTS_COLUMNS, null , null, null , null , null , null );
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
                    //post.setLikeOrNot(cursor.getString(7)); // new change
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
         //   cursor = db.query(POSTS_TABLE, POSTS_COLUMNS, POST_TAG , tag , null, null,null);

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
                    post.setItemPrice(cursor.getString(3));
                    post.setItemPriceCurrency(cursor.getString(4));
                    post.setTag(cursor.getString(5));
                    post.setImgFromByteArray(cursor.getBlob(6));
                   // post.setLikeOrNot(cursor.getString(7)); // new change
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
