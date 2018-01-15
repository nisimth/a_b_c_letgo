package com.example.Nisim.myapplication.backend.servlets;

import com.example.Nisim.myapplication.backend.database.operations.ConnPool;
import com.example.Nisim.myapplication.backend.database.operations.PostsResProvider;
import com.example.Nisim.myapplication.backend.objects.PostInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nisim on 09/01/2018.
 */

public class UploadPostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    private static final String POST_ID = "post_id";
    private static final String POST_TITLE = "post_title";
    private static final String POST_CONTENT = "post_content";
    private static final String POST_ITEM_PRICE = "post_item_price";
    private static final String POST_ITEM_PRICE_CURRENCY = "post_item_price_currency";
    private static final String LETGO_ID = "letgo_id";
    private static final String POST_TAG = "post_tag";

    //public static final int DB_RETRY_TIMES=5;


    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Commons file upload classes are specifically instantiated
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(500000);

        ServletFileUpload upload = new ServletFileUpload(factory);
        ServletOutputStream out = null;

        //int retry = DB_RETRY_TIMES;
        Connection conn = null;



        String id = null;
        String title = null;
        String content = null;
        String itemPrice = null;
        String itemPriceCurrency = null;
        String letgoId = null;
        String tag = null;

        String fileName = null;

        byte [] image= null;
        String respPage = RESOURCE_FAIL_TAG;
        try {

            System.out.println("======= upload PostInfo with image Servlet =======");
            // Parse the incoming HTTP request
            // Commons takes over incoming request at this point
            // Get an iterator for all the data that was sent
            List<FileItem> items = upload.parseRequest(req);
            Iterator<FileItem> iter = items.iterator();

            // Set a response content type
            resp.setContentType("text/html");

            // Setup the output stream for the return XML data
            out = resp.getOutputStream();

            // Iterate through the incoming request data
            while (iter.hasNext()) {
                // Get the current item in the iteration
                FileItem item = (FileItem) iter.next();

                // If the current item is an HTML form field
                if (item.isFormField()) {
                    // If the current item is file data

                    // If the current item is file data
                    String fieldname = item.getFieldName();
                    String fieldvalue = item.getString();

                    System.out.println(fieldname + "=" + fieldvalue);


                    if (fieldname.equals(POST_ID)) {
                        id =fieldvalue;
                    } else if (fieldname.equals(POST_TITLE)) {
                        title = fieldvalue;
                    } else if (fieldname.equals(POST_CONTENT)) {
                        content = fieldvalue;
                    } else if (fieldname.equals(POST_ITEM_PRICE)) {
                        itemPrice = fieldvalue;
                    } else if (fieldname.equals(POST_ITEM_PRICE_CURRENCY)) {
                        itemPriceCurrency = fieldvalue;
                    } else if (fieldname.equals(LETGO_ID)) {
                        letgoId = fieldvalue;
                    } else if (fieldname.equals(POST_TAG)) {
                        tag = fieldvalue;
                    }



                } else {

                    //fileName = item.getName();
                    image=item.get();

                }
            }

            //while (retry > 0) {

            try {

                conn = ConnPool.getInstance().getConnection();
                PostsResProvider itemResProvider = new PostsResProvider();
                PostInfo postInfo = new PostInfo(id, title, content, itemPrice, itemPriceCurrency, image, letgoId, tag);
                if(itemResProvider.insertPostInfo(postInfo, conn)) {
                    respPage = RESOURCE_SUCCESS_TAG;
                }

                //retry = 0;

            } catch (SQLException e) {
                e.printStackTrace();
                //retry--;
            } catch (Throwable t) {
                t.printStackTrace();
                //retry = 0;
            } finally {
                if (conn != null) {
                    ConnPool.getInstance().returnConnection(conn);
                }
            }

            //	}

            out.println(respPage);
            out.close();


        } catch (FileUploadException fue) {
            fue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
