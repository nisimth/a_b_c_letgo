package com.example.Nisim.myapplication.backend.database.operations;

import com.example.Nisim.myapplication.backend.objects.LetgoInfo;
import com.example.Nisim.myapplication.backend.objects.PostInfo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nisim on 09/01/2018.
 */

public class PostsResProvider {

    private static final String update_sql = "UPDATE posts SET title=?, content=?, image=?, letgo_id=?, tag=? WHERE id=?;";

    private static final String select_sql = "SELECT * FROM  posts WHERE id=?;";

    private static final String select_img_sql = "SELECT image FROM  posts WHERE id=?;";

    private static final String insert_sql = "INSERT INTO posts (id, title, content, itemPrice, itemPriceCurrency, image, letgo_id, tag) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String delete_sql = "DELETE FROM posts WHERE id=?;";

    private static final String delete_all_sql_by_id = "DELETE FROM posts WHERE letgo_id=?;"; // delete posts by letgo id

    private static final String select_all_sql_by_id = "SELECT * FROM posts WHERE letgo_id=?;"; // get posts of letgo id

    private static final String select_all_sql = "SELECT * FROM posts;"; // get all posts


    public List<PostInfo> getAllPosts(Connection conn)
            throws SQLException {

        List<PostInfo> results = new ArrayList<PostInfo>();

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_all_sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                String id = rs.getString(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String itemPrice = rs.getString(4) ;
                String itemPriceCurrency = rs.getString(5);

                java.sql.Blob imageBlob = rs.getBlob(6);
                byte[] image = null;
                if (imageBlob != null) {
                    image = imageBlob.getBytes(1, (int) imageBlob.length());
                }



                String tumblerId = rs.getString(7);
                String tag = rs.getString(8);
                PostInfo postInfo = new PostInfo(id, title, content, itemPrice, itemPriceCurrency, image,tumblerId,tag);

                results.add(postInfo);

            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public List<PostInfo> getAllPostsByLetgo(LetgoInfo letgoInfo, Connection conn)
            throws SQLException {

        List<PostInfo> results = new ArrayList<PostInfo>();

        if (letgoInfo == null) {
            return results;
        }

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_all_sql_by_id);

            ps.setString(1, letgoInfo.getId());

            rs = ps.executeQuery();

            while (rs.next()) {

                String id = rs.getString(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String itemPrice = rs.getString(4) ;
                String itemPriceCurrency = rs.getString(5);
                java.sql.Blob imageBlob = rs.getBlob(6);
                byte[] image = null;
                if (imageBlob != null) {
                    image = imageBlob.getBytes(1, (int) imageBlob.length());
                }



                String letgoId = rs.getString(7);
                String tag = rs.getString(8);
                PostInfo postInfo = new PostInfo(id, title, content,itemPrice,itemPriceCurrency, image,letgoId,tag);

                results.add(postInfo);

            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public byte[] getImage(String postId, Connection conn)
            throws SQLException {

        byte[] result = null;

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(select_img_sql);

            ps.setString(1, postId);

            rs = ps.executeQuery();

            while (rs.next()) {

                java.sql.Blob imageBlob = rs.getBlob(1);
                if (imageBlob != null) {
                    result = imageBlob.getBytes(1, (int) imageBlob.length());
                }
            }

        } catch (SQLException e) {
            throw e;

        } catch (Throwable e) {
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean insertPostInfo(PostInfo obj, Connection conn) throws SQLException{

        boolean result = false;
        ResultSet rs = null;
        ResultSet rs1 = null;
        PreparedStatement ps = null;
        PreparedStatement stt = null;

        try {

            String id = obj.getId();
            String title = obj.getTitle();
            String content = obj.getContent();
            String itemPrice = obj.getItemPrice() ;
            String itemPriceCurrency = obj.getItemPriceCurrency();

            byte[] imageBytes = obj.getImage();

            String letgoId = obj.getLetgo_id();
            String tag = obj.getTag();

            if (imageBytes == null) {
                try {
                    imageBytes = getImage(id, conn);
                }catch (Throwable e){
                    System.out.println("no image for post "+ id);
                }
            }



            stt = (PreparedStatement) conn.prepareStatement(select_sql);
            stt.setString(1, id);

            if (stt.execute()) {
                rs1 = stt.getResultSet();
                if (rs1.next()) {
                    // its execute update
                    ps = (PreparedStatement) conn.prepareStatement(update_sql);

                    ps.setString(1, title);
                    ps.setString(2, content);
                    ps.setString(3, itemPrice) ;
                    ps.setString(4, itemPriceCurrency);

                    if (imageBytes != null) {
                        InputStream is = new ByteArrayInputStream(imageBytes);
                        ps.setBlob(5, is);

                    } else {

                        ps.setNull(5, Types.BLOB);
                    }



                    ps.setString(6, letgoId);
                    ps.setString(7, tag);

                    // where
                    ps.setString(8, id);

                    ps.execute();

                    result = true;

                } else {

                    // its execute insert
                    ps = (PreparedStatement) conn.prepareStatement(insert_sql);
                    ps.setString(1, id);
                    ps.setString(2, title);
                    ps.setString(3, content);
                    ps.setString(4, itemPrice) ;
                    ps.setString(5, itemPriceCurrency);

                    if (imageBytes != null) {
                        InputStream is = new ByteArrayInputStream(imageBytes);
                        ps.setBlob(6, is);

                    } else {

                        ps.setNull(6, Types.BLOB);
                    }

                    ps.setString(7, letgoId);
                    ps.setString(8, tag);

                    ps.execute();

                    result = true;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (stt != null) {
                try {
                    stt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

    public boolean deleteAllPostsByLetgo(LetgoInfo letgoInfo, Connection conn)
            throws SQLException {

        boolean result = false;

        PreparedStatement ps = null;

        try {

            ps = (PreparedStatement) conn.prepareStatement(delete_all_sql_by_id);

            ps.setString(1, letgoInfo.getId());

            ps.execute();

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean deletePost(PostInfo obj, Connection conn) throws SQLException {

        boolean result = false;
        PreparedStatement ps = null;

        try {

            if (obj != null) {

                ps = (PreparedStatement) conn.prepareStatement(delete_sql);

                String id = obj.getId();

                ps.setString(1, id);

                ps.execute();

                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
