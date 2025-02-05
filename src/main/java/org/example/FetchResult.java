package org.example;

import com.google.gson.Gson;

import java.util.HashMap;

public class FetchResult {
    public static String Json(HashMap<String, String> objectFetch) {
        Gson gson = new Gson();
        return gson.toJson(objectFetch);
    }
}
