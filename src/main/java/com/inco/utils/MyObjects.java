package com.inco.utils;

import com.inco.model.Dialog;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public abstract class MyObjects {
    public static boolean isBlank(final String text) {
        return text == null
                || text.equals("");
    }

    public static boolean isBlank(final Object obj, final String instancetype) {
        return obj == null
                || !obj.getClass().getCanonicalName().equals(instancetype)
                || obj.equals("");
    }

    public static ArrayList<Dialog> createDialogsByJsonList(final String json) {
        JSONParser parser = new JSONParser();
        ArrayList<Dialog> res = new ArrayList<>();

        Object obj = null;
        try {
            obj = parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray array = (JSONArray) obj;

        array.forEach((d) -> res.add(new Dialog().setTarget((long) d)));

        return res;
    }
}
