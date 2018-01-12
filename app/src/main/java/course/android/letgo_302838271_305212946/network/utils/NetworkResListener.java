package course.android.letgo_302838271_305212946.network.utils;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by Nisim on 09/01/2018.
 */

public interface NetworkResListener {
    /**
     * callback method which called when the resources update is started
     */
    public void onPreUpdate();

    /**
     * callback method which called after resources update is finished
     * @param  res  - the data
     * @param status - the status of the update process
     */
    public void onPostUpdate(byte[] res, ResStatus status);

    public void onPostUpdate(JSONObject res, ResStatus status);

    public void onPostUpdate(Bitmap res, ResStatus status);



}
