package com.inco.utils;

import com.inco.model.Dialog;
import spark.Request;
import spark.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DbActions {
    public static boolean insertMessage(final Session session, final String message, final String user_id_target) {
        ResultSet res;
        try {
            res = Storage.getStatement().executeQuery(
                    String.format("""
                                select count(*) count from usr where system_id = '%s'
                            """,
                            user_id_target));

            if (res.next() && res.getInt("count") == 1) {
                Storage.getStatement().executeUpdate(
                        String.format("""
                                            insert into messages (user_id, textw, user_id_target)
                                            values (%s, '%s', %s)
                                        """,
                                session.attribute("user_id"),
                                message,
                                user_id_target), Statement.RETURN_GENERATED_KEYS);
                res = Storage.getStatement().getGeneratedKeys();
                res.next();

                return res.getLong(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

//    public static boolean insertUser(final String username, final String password) {
    public static boolean insertUser(final Request request) {
        ResultSet res;

        String[] body = request.body().split("&");
        Map<String, String> bodyParse = new HashMap<>();
        for (String s : body) {
            bodyParse.put(s.split("=")[0], s.split("=")[1]);
        }

        try {
            res = Storage.getStatement().executeQuery(
                    String.format("""
                                select count(*) count from usr where name = '%s'
                            """,
                            bodyParse.get("username")));

            if (res.next() && res.getInt("count") == 0) {
                Storage.getStatement().executeUpdate(
                        String.format("""
                                            insert into usr (name, password)
                                            values ('%s', '%s')
                                        """,
                                bodyParse.get("username"),
                                bodyParse.get("password")), Statement.RETURN_GENERATED_KEYS);
                res = Storage.getStatement().getGeneratedKeys();
                res.next();

                return res.getLong(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static Dialog selectMyMessagesWith(final Session session, final String user_id_target) {
        Statement st = Storage.getStatement();
        Dialog dialog = new Dialog();

        try {
            ResultSet rs = st.executeQuery(
                    String.format(
                            """
                                select
                                    SYSTEM_ID,
                                    TEXT,
                                    SEND_DATE
                                from messages
                                where user_id = %s
                                  and user_id_target = %s
                            """,
                            session.attribute("user_id"),
                            user_id_target)
            );

            while (rs.next()) {
                dialog
                        .setTarget(Integer.parseInt(user_id_target))
                        .addMessage(
                                rs.getString("TEXT"),
                                rs.getTimestamp("SEND_DATE"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dialog;
    }

    public static ArrayList<Dialog> selectMyDialogs(final Session session) {
        Statement st = Storage.getStatement();
        ArrayList<Dialog> dialogs = null;

        try {
            ResultSet rs = st.executeQuery(
                    String.format(
                            """
                                select
                                    u.name,
                                    d.user_id,
                                    d.target_obj
                                from dialogs d
                                inner join usr u
                                  on u.system_id = d.user_id
                                where u.name = '%s'
                            """, (String) session.attribute("username"))
            );

            rs.next();
            dialogs = MyObjects.createDialogsByJsonList(rs.getString("target_obj"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dialogs;
    }
}
