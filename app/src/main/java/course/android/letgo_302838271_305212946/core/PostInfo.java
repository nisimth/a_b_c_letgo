package course.android.letgo_302838271_305212946.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nisim on 06/12/2017.
 */

public class PostInfo {

    private String id;
    private String letgo_id;
    private String title; // title of post
    private String content; // description of post
    private String itemPrice ; // new attribute of price for item
    private String itemPriceCurrency ;  //currency
    private String tag; // category
    private Bitmap img;

   // private String likeOrNot  ;


    public PostInfo(String letgo_id, String title , String content , String itemPrice , String itemPriceCurrency , String tag , Bitmap img ) {
        this.id = generateId();
        this.letgo_id = letgo_id;
        this.title = title;
        this.content = content;
        this.itemPrice = itemPrice ;
        this.itemPriceCurrency = itemPriceCurrency ;
        this.tag = tag;
        this.img = img;
      //  this.likeOrNot = likeOrNot ;


    }

    private String generateId(){
        return "post_ " + System.currentTimeMillis();


    }
    public PostInfo(){
        this.id = generateId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLetgo_id() {
        return letgo_id;
    }

    public void setLetgo_id(String letgo_id) {
        this.letgo_id = letgo_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemPriceCurrency() {
        return itemPriceCurrency;
    }

    public void setItemPriceCurrency(String itemPriceCurrency) {
        this.itemPriceCurrency = itemPriceCurrency;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

 /*   public String getLikeOrNot() {
        return likeOrNot;
    }

    public void setLikeOrNot(String likeOrNot) {
        this.likeOrNot = likeOrNot;
    }*/

    public static byte[] getImgByteArray(Bitmap bm){
        byte[] res = new byte[0];
        if(bm !=null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,0,outputStream);
            res = outputStream.toByteArray();
        }
        return res;
    }



    public void setImgFromByteArray(byte[] imgFromByteArray) {
        if (imgFromByteArray != null) {
            img = BitmapFactory.decodeByteArray(imgFromByteArray, 0 , imgFromByteArray.length);
        }
    }

    public static List<PostInfo> parseJson(JSONObject json) {

        List<PostInfo> posts = null;
        try {

            posts = new ArrayList<PostInfo>();

            JSONArray foldersJsonArr = json.getJSONArray("posts");

            for (int i = 0; i < foldersJsonArr.length(); i++) {
                try {
                    JSONObject iObj = foldersJsonArr.getJSONObject(i);
                    PostInfo post = new PostInfo();
                    post.setId(iObj.getString("id"));
                    post.setTitle(iObj.getString("title"));
                    post.setContent(iObj.getString("content"));
                    post.setItemPrice(iObj.getString("itemPrice"));
                    post.setItemPriceCurrency(iObj.getString("itemPriceCurrency"));
                    post.setTag(iObj.getString("tag"));
                    post.setLetgo_id(iObj.getString("letgo_id"));

                    posts.add(post);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return posts;
    }
}
