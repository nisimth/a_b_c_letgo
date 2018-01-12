package com.example.Nisim.myapplication.backend.database.operations;

import com.example.Nisim.myapplication.backend.objects.LetgoInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nisim on 09/01/2018.
 */

public class LetgoResProvider {

    private static final String update_sql = "UPDATE letgoes SET password=? WHERE id=?;";

    private static final String select_sql = "SELECT * FROM  letgoes WHERE id=?;";

    private static final String insert_sql = "INSERT INTO letgoes (id, password) VALUES (?,?);";

    private static final String delete_sql = "DELETE FROM letgoes WHERE id=?;";

    //private static final String select_all_sql = "SELECT * FROM letgoes;";


    public boolean insertTumbler(LetgoInfo obj, Connection conn) {

        boolean result = false;
        ResultSet rs = null;
        ResultSet rs1 = null;
        PreparedStatement ps = null;
        PreparedStatement stt = null;

        try {

            String id = obj.getId();
            String password = obj.getPassword();

            stt = (PreparedStatement) conn.prepareStatement(select_sql);
            stt.setString(1, id);


            if (stt.execute()) {
                rs1 = stt.getResultSet();
                if (rs1.next()) {

                    // its execute update
                    ps = (PreparedStatement) conn.prepareStatement(update_sql);
                    ps.setString(1, password);
                    // where
                    ps.setString(2, id);
                    ps.execute();
                    result = true;
                } else {

                    // its execute insert
                    ps = (PreparedStatement) conn.prepareStatement(insert_sql);
                    ps.setString(1,id);
                    ps.setString(2, password);
                    ps.execute();
                    result = true;

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
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


    public boolean deleteLetgo(LetgoInfo obj,
                                 Connection conn) throws SQLException {

        boolean result = false;
        PreparedStatement ps = null;


        try {

            if (obj != null) {

                String id = obj.getId();

                PostsResProvider itemResProvider = new PostsResProvider();
                itemResProvider.deleteAllPostsByLetgo(new LetgoInfo(id), conn);

                ps = (PreparedStatement) conn.prepareStatement(delete_sql);


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
