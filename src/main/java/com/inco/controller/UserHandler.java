package com.inco.controller;

import com.inco.utils.DbActions;

import static spark.Spark.get;
import static spark.Spark.post;

public class UserHandler {
    public UserHandler() {
        post("/create-user", (request, response) -> DbActions.insertUser(request));
    }
}
