package com.poc.expectations.callback;

import com.poc.expectations.response.JsonResponseReader;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class GetCardholderProfileCallback implements ExpectationResponseCallback {

    private static final String FILE = "getCardholderProfile";

    private final JsonResponseReader jsonResponseReader;

    public GetCardholderProfileCallback() {
        jsonResponseReader = new JsonResponseReader();
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws Exception {

        return HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application.json")
                .withBody(
                        jsonResponseReader.getFiltered(FILE, null)
                                .or(() -> jsonResponseReader.getDefault(FILE))
                                .orElseThrow(() -> new RuntimeException("default not found"))
                                .toJSONString()
                );
    }
}
