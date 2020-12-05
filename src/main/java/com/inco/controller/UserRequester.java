package com.inco.controller;

import com.inco.utils.DbActions;
import com.inco.utils.MyObjects;
import com.inco.utils.Storage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.SQLException;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.stop;

public class UserRequester {
    public UserRequester() {

        post("/send", "application/json", (request, response) -> {
            JSONObject body = ((JSONObject) new JSONParser().parse(request.body()));
            return DbActions.insertMessage(
                    request.session(),
                    body.get("message").toString(),
                    body.get("user_id_target").toString());
        });

        post("/auth", this::auth);

        get("/dialogs", (req, resp) -> DbActions.selectMyDialogs(req.session()));

        get("/dialogs/:user_id_target", (req, resp) ->
                DbActions.selectMyMessagesWith(req.session(), req.params(":user_id_target")));

        get("/hello", (request, response) -> "Kamon");

        post("/logout", (request, response) -> {
            request.session(true);
            request.session().attribute("logon", false);
            return String.format("Auth %s", "inactive");
        });

        post("/stop", (req, res) -> { stop(); return 1; });
    }

    public boolean auth(Request request, Response response) throws SQLException {
        String userName = request.queryParams("username");
        String password = request.queryParams("password");

        if (MyObjects.isBlank(userName) || MyObjects.isBlank(password))
            return false;

        ResultSet rs = Storage.getStatement().executeQuery(
                String.format("select SYSTEM_ID, NAME, PASSWORD from usr where NAME = '%s'", userName));

        if (rs.next() && rs.getString("password").equals(password)) {
            request.session(true);
            request.session().attribute("logon", true);
            request.session().attribute("user_id", rs.getInt("SYSTEM_ID"));
            request.session().attribute("username", userName);
        } else {
            return false;
        }

        return true;
    }
}
