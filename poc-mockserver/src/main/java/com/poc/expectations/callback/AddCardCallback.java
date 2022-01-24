package com.poc.expectations.callback;

import com.poc.expectations.utils.RandomValuesUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class AddCardCallback implements ExpectationResponseCallback {

    private static final String SPACE = " ";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        JSONObject request = (JSONObject) parser.parse(httpRequest.getBodyAsJsonOrXmlString());
        JSONObject addCard = (JSONObject) request.get("addCard");

        return HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(response(addCard).toJSONString());
    }

    private JSONObject response(JSONObject addCard) {
        JSONObject addCardResponse = new JSONObject();

        addCardResponse.put("responseCode", "00");
        addCardResponse.put("responseDesc", "Supplementary Card Purchase Order");
        addCardResponse.put("newCardNumber", newCardNumber());
        addCardResponse.put("balance", 0.0);
        addCardResponse.put("transId", transId());
        addCardResponse.put("customerId", "110000000009999999");
        addCardResponse.put("fee", 0.0);
        addCardResponse.put("referenceId", RandomValuesUtil.stringNumbers(15));
        addCardResponse.put("batchReferenceId", RandomValuesUtil.stringNumbers(5));
        addCardResponse.put("nameOnCard", nameOnCard(addCard));
        addCardResponse.put("lastDepositAmount", 0.0);
        addCardResponse.put("ledgerBalance", 0.0);

        JSONObject response = new JSONObject();
        response.put("addCardResponse", addCardResponse);
        return response;
    }

    private JSONObject newCardNumber() {
        JSONObject newCardNumber = new JSONObject();
        newCardNumber.put("number", "6088850592739273");
        newCardNumber.put("expiryDate", "122099");
        return newCardNumber;
    }

    private String transId() {
        return "F" + RandomValuesUtil.stringNumbers(9);
    }

    private String nameOnCard(JSONObject addCard) {
        JSONObject profile = (JSONObject) addCard.get("profile");

        if (profile != null) {

            StringBuilder nameOnCard = new StringBuilder();

            nameOnCard.append(profile.getAsString("firstName"));

            if (profile.containsKey("middleName")) {
                nameOnCard.append(SPACE);
                nameOnCard.append(profile.getAsString("middleName"));
            }

            if (profile.containsKey("lastName")) {
                nameOnCard.append(SPACE);
                nameOnCard.append(profile.getAsString("lastName"));
            }

            return (nameOnCard.length() < 60) ? nameOnCard.toString() : nameOnCard.substring(0, 59);
        }

        return null;
    }
}
