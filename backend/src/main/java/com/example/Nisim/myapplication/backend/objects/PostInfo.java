package com.example.Nisim.myapplication.backend.objects;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Nisim on 09/01/2018.
 */

public class PostInfo {

    private String id;
    private String letgo_id;
    private String title; // title of post
    private String content; // description of post
    private String itemPrice ; // new attribute of price for item
    private String itemPriceCurrency ;  //currency
    private String tag; // category
    private byte[] img;

    public PostInfo(String id) {
        this.id = id;

    }

    public PostInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostInfo(String id, String title, String content, String itemPrice, String itemPriceCurrency, byte[] img,
                    String letgo_id, String tag) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.itemPrice = itemPrice;
        this.itemPriceCurrency = itemPriceCurrency;
        this.img = img;
        this.letgo_id = letgo_id;
        this.tag =tag;
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

    public byte[] getImage() {
        return img;
    }

    public void setImage(byte[] img) {
        this.img = img;
    }

    public JSONObject toJson() {

        JSONObject iObj = new JSONObject();
        iObj.put("id", getId());
        iObj.put("title", getTitle());
        iObj.put("content", getContent());
        iObj.put("itemPrice", getItemPrice());
        iObj.put("itemPriceCurrency",getItemPriceCurrency());
        iObj.put("tag", getTag());
        iObj.put("letgo_id", getLetgo_id());
        //iObj.put("img", isImageExists());

        return iObj;
    }

    public static String toJson(List<PostInfo> list) {

        JSONObject jsonObj = new JSONObject();

        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            return null;
        }

        JSONArray jsonArray = new JSONArray();

        for (PostInfo postInfo : list) {

            if (postInfo != null) {

                JSONObject itemObj = postInfo.toJson();

                jsonArray.add(itemObj);
            }

        }

        jsonObj.put("posts", jsonArray);

        return jsonObj.toString(2);
    }
}
