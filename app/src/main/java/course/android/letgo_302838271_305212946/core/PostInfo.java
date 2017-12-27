package course.android.letgo_302838271_305212946.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Nisim on 06/12/2017.
 */

public class PostInfo {

    private String id;
    private String title;
    private String content;
    private String tag; // category
    private Bitmap img;

    public PostInfo(String title,String content,String tag, Bitmap img) {
        this.id = generateId();
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.img = img;


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

    public byte[] getImgByteArray(){
        byte[] res = new byte[0];
        if(img!=null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG,0,outputStream);
            res = outputStream.toByteArray();
        }
        return res;
    }



    public void setImgFromByteArray(byte[] imgFromByteArray) {
        if (imgFromByteArray != null) {
            img = BitmapFactory.decodeByteArray(imgFromByteArray, 0 , imgFromByteArray.length);
        }
    }

}
