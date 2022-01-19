package com.poc.expectations.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.mockserver.model.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class FilterUtils {

    public static Map<String, Object> buildFilters(HttpRequest request) throws Exception {
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        Object object = parser.parse(request.getBodyAsJsonOrXmlString());

        Map<String, Object> filters = new HashMap<>();
        loadMap(object, null, filters);

        return filters;
    }

    private static void loadMap(Object object, String prefix, Map<String, Object> filters) {

        JSONObject json = (JSONObject) object;

        for (String key : json.keySet()) {

            Object value = json.get(key);
            String mapKey = buildKey(prefix, key);

            if (value instanceof JSONObject) {
                loadMap(value, mapKey, filters);
            } else {
                filters.put(mapKey, value);
            }
        }
    }

    private static String buildKey(String prefix, String key) {
        return (prefix != null) ? prefix + "." + key : key;
    }

}