package course.android.letgo_302838271_305212946.network.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import course.android.letgo_302838271_305212946.core.PostInfo;

/**
 * Created by Nisim on 09/01/2018.
 */

public class NetworkConnector {
    private static NetworkConnector mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    private final String HOST_URL =  "http://10.0.2.2:8080/"; //"http://192.168.1.103:8080/";
    //private final String HOST_URL =  "http://192.168.43.152:8080/"; //"http://192.168.1.103:8080/";
    private  final String BASE_URL = HOST_URL + "app_res";

    private int TIME_OUT = 10000;
    public static  int GET_ALL_POSTS_JSON_REQ = 0;
    //private static final int INSERT_LETGO_REQ = 1;
    //private static final int DELETE_LETGO_REQ = 2;

    public static final int DELETE_POST_REQ = 3;
    public static final int GET_POST_IMAGE_REQ = 4;

    public static final int GET_POSTS_OF_LETGO_JSON_REQ = 5;

    public static final int INSERT_POST_REQ = 6;


    private static final String LETGO_ID = "letgo_id";
    //private static final String LETGO_PASS = "letgo_pass";

    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    private static final String POST_ID = "post_id";
    private static final String POST_TITLE = "post_title";
    private static final String POST_CONTENT = "post_content";
    private static final String POST_ITEM_PRICE = "post_item_price";
    private static final String POST_ITEM_PRICE_CURRENCY = "post_item_price_currency";
    private static final String POST_TAG = "post_tag";


    private static final String REQ = "req";
    private static int REQ_TIRES =  3;//DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

    private NetworkConnector() {

    }

    public static synchronized NetworkConnector getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkConnector();
        }
        return mInstance;
    }

    public void initialize(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private void addToRequestQueue(String query, final NetworkResListener listener) {

        String reqUrl = BASE_URL + "?" + query;
        notifyPreUpdateListener(listener);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, reqUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        notifyPostUpdateListener(response, ResStatus.SUCCESS, listener);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        JSONObject err = null;
                        try {
                            err = new JSONObject(RESOURCE_FAIL_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListener(err, ResStatus.FAIL, listener);
                        }

                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                REQ_TIRES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(jsObjRequest);
    }

    private void addImageRequestToQueue(String query, final NetworkResListener listener){

        String reqUrl = BASE_URL + "?" + query;

        notifyPreUpdateListener(listener);

        getImageLoader().get(reqUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bm = response.getBitmap();
                notifyPostBitmapUpdateListener(bm, ResStatus.SUCCESS, listener);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                notifyPostBitmapUpdateListener(null, ResStatus.FAIL, listener);
            }
        });
    }

    private ImageLoader getImageLoader() {
        return mImageLoader;
    }


    private void uploadPostInfo(final PostInfo item, final NetworkResListener listener) {

        String reqUrl = HOST_URL + "upload_post?";
        notifyPreUpdateListener(listener);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, reqUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            notifyPostUpdateListener(obj, ResStatus.SUCCESS, listener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(RESOURCE_FAIL_TAG );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListener(obj, ResStatus.FAIL, listener);
                        }

                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(POST_ID, item.getId());
                params.put(POST_TITLE, item.getTitle());
                params.put(POST_CONTENT,  item.getContent());
                params.put(POST_ITEM_PRICE, item.getItemPrice());
                params.put(POST_ITEM_PRICE_CURRENCY, item.getItemPriceCurrency());
                params.put(POST_TAG, item.getTag());
                params.put(LETGO_ID, item.getLetgo_id());
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                byte[] pic = PostInfo.getImgByteArray(item.getImg());
                params.put("fileField", new DataPart(imagename + ".png", pic));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                REQ_TIRES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        getRequestQueue().add(volleyMultipartRequest);
    }


    public void sendRequestToServer(int requestCode, PostInfo data, NetworkResListener listener){

        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();


        switch (requestCode){
            case INSERT_POST_REQ:{

                uploadPostInfo(data, listener);

                break;
            }

            case DELETE_POST_REQ:{
                builder.appendQueryParameter(REQ , String.valueOf(requestCode));
                builder.appendQueryParameter(POST_ID , data.getId());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case GET_POST_IMAGE_REQ: {
                builder.appendQueryParameter(REQ , String.valueOf(requestCode));
                builder.appendQueryParameter(POST_ID , data.getId());

                String query = builder.build().getEncodedQuery();
                addImageRequestToQueue(query, listener);

                break;
            }
        }



    }


    public void updatePostsFeed(NetworkResListener listener){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter(REQ , String.valueOf(GET_ALL_POSTS_JSON_REQ));
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);
    }


    private void clean() {

    }


    private  void notifyPostBitmapUpdateListener(final Bitmap res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPostUpdateListener(final JSONObject res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPreUpdateListener(final NetworkResListener listener) {


        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPreUpdate();
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

}
