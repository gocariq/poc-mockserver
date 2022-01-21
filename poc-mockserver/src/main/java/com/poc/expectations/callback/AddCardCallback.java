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
        JSONObject response = new JSONObject();

        response.put("responseCode", "00");
        response.put("responseDesc", "Add Card");
        response.put("newCardNumber", newCardNumber());
        response.put("sharingCards", sharingCards());
        response.put("balance", 0.0);
        response.put("transId", transId());
        response.put("arn", "mock-arn");
        response.put("clerkId", "mock-clerkId");
        response.put("customerId", "110000000009999999");
        response.put("fee", 0.0);
        response.put("referenceId", RandomValuesUtil.stringNumbers(15));
        response.put("batchReferenceId", RandomValuesUtil.stringNumbers(20));
        response.put("nameOnCard", nameOnCard(addCard));

        return response;
    }

    private JSONObject newCardNumber() {
        JSONObject newCardNumber = new JSONObject();
        newCardNumber.put("number", "6088850592739273");
        newCardNumber.put("expiryDate", "122099");
        return newCardNumber;
    }

    private JSONArray sharingCards() {
        JSONArray sharingCards = new JSONArray();
        sharingCards.add(sharingCard("1111111111111111", "mock-scrid-1111"));
        sharingCards.add(sharingCard("2222222222222222", "mock-scrid-2222"));
        return sharingCards;
    }

    private JSONObject sharingCard(String number, String referenceId) {
        JSONObject sharingCard = new JSONObject();
        sharingCard.put("sharingCardNumber", number);
        sharingCard.put("sharingCardReferenceId", referenceId);
        return sharingCard;
    }

    private String transId() {
        return "D2" + RandomValuesUtil.stringNumbers(9);
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
