package com.poc.expectations.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.mockserver.model.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class FilterUtils {

    private FilterUtils() {}

    public static Map<String, Object> buildFilters(HttpRequest request) throws ParseException {
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        Object object = parser.parse(request.getBodyAsJsonOrXmlString());

        Map<String, Object> filters = new HashMap<>();
        loadMap(object, null, filters);

        return filters;
    }

    private static void loadMap(Object object, String prefix, Map<String, Object> filters) {

        JSONObject json = (JSONObject) object;

        for (Map.Entry<String, Object> entry : json.entrySet()) {

            Object value = entry.getValue();
            String mapKey = buildKey(prefix, entry.getKey());

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