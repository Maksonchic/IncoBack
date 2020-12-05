package com.inco;

import com.inco.controller.UserHandler;
import com.inco.controller.UserRequester;
import com.inco.utils.MyObjects;
import com.inco.utils.Storage;

import static spark.Spark.*;

public class IncognitoMessageMain {
    public static void main(String[] args) {

        Storage.initStorage();

        options("/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        UserRequester ur = new UserRequester();

        before((req, resp) -> {
            if (MyObjects.isBlank(req.session().attribute("logon"), "java.lang.Boolean")
                    || !(boolean) req.session().attribute("logon"))
                if (!ur.auth(req, resp))
                    halt(401);
        });

        UserHandler uh = new UserHandler();
    }
}
