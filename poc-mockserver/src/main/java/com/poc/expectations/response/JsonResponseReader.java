package com.poc.expectations.response;

import com.poc.expectations.exception.JsonParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.FileReader;
import java.util.Map;
import java.util.Optional;

public class JsonResponseReader {

    private static final String RESPONSE_PATH = "/config/responses/";
    private static final String EXTENSION = ".json";

    private final JSONParser parser;

    public JsonResponseReader() {
        parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
    }

    public Optional<JSONObject> getFiltered(String file, Map<String, Object> filter) {

        JSONArray array = (JSONArray) readFile(file);

        return array.stream()
                .filter(obj -> {

                    JSONObject filters = (JSONObject) ((JSONObject) obj).get("filters");

                    for (Map.Entry<String, Object> entry : filter.entrySet()) {

                        String filterKey = entry.getKey();
                        Object filterValue = entry.getValue();

                        if (filters.get(filterKey) == null || !filters.get(filterKey).equals(filterValue)) {
                            return false;
                        }
                    }

                    return true;
                })
                .findFirst()
                .map(JSONObject.class::cast);
    }

    public Optional<JSONObject> getDefault(String file) {

        JSONArray array = (JSONArray) readFile(file);

        return array.stream()
                .filter(obj -> {
                    JSONObject json = (JSONObject) obj;
                    return (boolean) json.get("default");
                })
                .findFirst()
                .map(JSONObject.class::cast);
    }

    private Object readFile(String file) {
        try (FileReader reader = new FileReader(RESPONSE_PATH + file + EXTENSION)) {
            return parser.parse(reader);
        } catch (Exception e) {
            throw new JsonParseException("Error to parse file " + file, e);
        }
    }
}