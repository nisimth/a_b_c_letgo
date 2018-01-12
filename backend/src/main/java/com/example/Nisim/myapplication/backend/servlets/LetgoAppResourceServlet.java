package com.example.Nisim.myapplication.backend.servlets;

import com.example.Nisim.myapplication.backend.database.operations.ConnPool;
import com.example.Nisim.myapplication.backend.database.operations.PostsResProvider;
import com.example.Nisim.myapplication.backend.objects.LetgoInfo;
import com.example.Nisim.myapplication.backend.objects.PostInfo;
import com.example.Nisim.myapplication.backend.utils.FilesUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nisim on 09/01/2018.
 */

public class LetgoAppResourceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ========
    private static final int GET_ALL_POSTS_JSON_REQ = 0;
    //private static final int INSERT_LETGO_REQ = 1;
    //private static final int DELETE_LETGO_REQ = 2;
    //private static final int INSERT_POST_REQ = 3;

    private static final int DELETE_POST_REQ = 3;
    private static final int GET_POST_IMAGE_REQ = 4;

    private static final int GET_POSTS_OF_LETGO_JSON_REQ = 5;

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

    //public static final int DB_RETRY_TIMES = 5;


    public void init(ServletConfig config) throws ServletException {

        super.init();

        String tmp = config.getServletContext().getInitParameter("localAppDir");
        if (tmp != null) {
            FilesUtils.appDirName = config.getServletContext().getRealPath(tmp);
            System.out.println(FilesUtils.appDirName);

        }

    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String respPage = null;
        String userReq = req.getParameter(REQ);
        Connection conn = null;
        //int retry = DB_RETRY_TIMES;

        if (userReq != null) {

            int reqNo = Integer.valueOf(userReq);
            System.out.println("LetgoAppResourceServlet:: req code ==>" + reqNo);
            //while (retry > 0) {

            try {

                switch (reqNo) {

                    // == folder apis
                    case GET_ALL_POSTS_JSON_REQ: {

                        conn = ConnPool.getInstance().getConnection();
                        PostsResProvider postsResProvider = new PostsResProvider();
                        List<PostInfo> postsList = postsResProvider
                                .getAllPosts(conn);
                        String resultJson = PostInfo.toJson(postsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

//					case INSERT_LETGO_REQ: {
//						String id = req.getParameter(LETGO_ID);
//						String title = req.getParameter(LETGO_PASS);
//						respPage = RESOURCE_FAIL_TAG;
//						resp.addHeader("Content-Type",
//								"application/json; charset=UTF-8");
//						conn = ConnPool.getInstance().getConnection();
//						LetgoResProvider letgoResProvider = new LetgoResProvider();
//
//						LetgoInfo letgoInfo = new LetgoInfo(id, title);
//						if (letgoResProvider.insertLetgo(letgoInfo, conn)) {
//							respPage = RESOURCE_SUCCESS_TAG;
//						}
//						PrintWriter pw = resp.getWriter();
//						pw.write(respPage);
//
//						//retry = 0;
//						break;
//					}
//
//					case DELETE_LETGO_REQ: {
//						String id = req.getParameter(LETGO_ID);
//						respPage = RESOURCE_FAIL_TAG;
//						resp.addHeader("Content-Type",
//								"application/json; charset=UTF-8");
//						conn = ConnPool.getInstance().getConnection();
//						LetgoResProvider letgoResProvider = new LetgoResProvider();
//
//						LetgoInfo tumblerInfo = new LetgoInfo(id);
//						if (letgoResProvider.deleteLetgo(tumblerInfo, conn)) {
//							respPage = RESOURCE_SUCCESS_TAG;
//						}
//						PrintWriter pw = resp.getWriter();
//						pw.write(respPage);
//
//						//retry = 0;
//						break;
//					}
//
//						case INSERT_POST_REQ: {
//							String id = req.getParameter(POST_ID);
//
//							String title = req.getParameter(POST_TITLE);
//
//							String content = req.getParameter(POST_CONTENT);
//
//							String letgoId = req.getParameter(LETGO_ID);
//
//							String tag = req.getParameter(POST_TAG);
//
//							respPage = RESOURCE_FAIL_TAG;
//							resp.addHeader("Content-Type",
//									"application/json; charset=UTF-8");
//							conn = ConnPool.getInstance().getConnection();
//							PostsResProvider postsResProvider = new PostsResProvider();
//
//							PostInfo post = new PostInfo(id);
//							post.setTitle(title);
//							post.setContent(content);
//							post.setLetgo_id(letgoId);
//							post.setTag(tag);
//
//							if (postsResProvider.insertPostInfo(post, conn)) {
//								respPage = RESOURCE_SUCCESS_TAG;
//							}
//							PrintWriter pw = resp.getWriter();
//							pw.write(respPage);
//
//							//retry = 0;
//							break;
//						}

                    case DELETE_POST_REQ: {
                        String id = req.getParameter(POST_ID);
                        respPage = RESOURCE_FAIL_TAG;
                        resp.addHeader("Content-Type",
                                "application/json; charset=UTF-8");
                        conn = ConnPool.getInstance().getConnection();
                        PostsResProvider postsResProvider = new PostsResProvider();
                        PostInfo postInfo = new PostInfo(id);
                        if (postsResProvider.deletePost(postInfo, conn)) {
                            respPage = RESOURCE_SUCCESS_TAG;
                        }
                        PrintWriter pw = resp.getWriter();
                        pw.write(respPage);

                        //retry = 0;
                        break;
                    }

                    case GET_POSTS_OF_LETGO_JSON_REQ: {

                        String id = req.getParameter(LETGO_ID);
                        conn = ConnPool.getInstance().getConnection();
                        PostsResProvider postsResProvider = new PostsResProvider();
                        LetgoInfo letgoInfo = new LetgoInfo(id);
                        List<PostInfo> itemsList = postsResProvider.getAllPostsByLetgo(
                                letgoInfo, conn);
                        String resultJson = PostInfo.toJson(itemsList);

                        if (resultJson != null && !resultJson.isEmpty()) {
                            respPage = resultJson;
                            resp.addHeader("Content-Type",
                                    "application/json; charset=UTF-8");
                            PrintWriter pw = resp.getWriter();
                            pw.write(respPage);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }

                    case GET_POST_IMAGE_REQ: {
                        String id = req.getParameter(POST_ID);
                        respPage = RESOURCE_FAIL_TAG;

                        conn = ConnPool.getInstance().getConnection();
                        PostsResProvider postsResProvider = new PostsResProvider();

                        byte[] imgBlob = postsResProvider.getImage(id, conn);

                        if (imgBlob != null && imgBlob.length > 0) {
                            resp.setContentType("application/x-download");
                            resp.setHeader("Content-disposition", "attachment; filename=" + "post.png");
                            ServletOutputStream os = resp.getOutputStream();
                            os.write(imgBlob);
                        } else {
                            resp.sendError(404);
                        }

                        //retry = 0;
                        break;
                    }



                    default:
                        //retry = 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                //	retry--;
            } catch (Throwable t) {
                t.printStackTrace();
                //	retry = 0;
            } finally {
                if (conn != null) {
                    ConnPool.getInstance().returnConnection(conn);
                }
            }
            //}
        }

    }
}
