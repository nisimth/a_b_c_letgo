package course.android.letgo_302838271_305212946.core;

/**
 * Created by Nisim on 06/12/2017.
 */

public class PostInfo {


    private String name;
    private int imgId;
    private String tag;

    public PostInfo(String name,String tag, int imgId) {
        this.name = name;
        this.tag = tag;
        this.imgId = imgId;

    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }
}
