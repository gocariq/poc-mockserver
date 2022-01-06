package com.poc.expectations.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.expectations.model.Payment;
import org.mockserver.mock.action.ExpectationForwardAndResponseCallback;
import org.mockserver.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentForwardCallback implements ExpectationForwardAndResponseCallback {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public HttpRequest handle(HttpRequest httpRequest) throws Exception {

        Payment payment = mapper.readValue(httpRequest.getBodyAsJsonOrXmlString(), Payment.class);

        payment.setId(UUID.randomUUID().toString());
        payment.setDate(LocalDateTime.now().toString());

        return HttpRequest.request()
                .withPath("/payment/register/forward")
                .withMethod("POST")
                .withHeader("Host", "localhost:9191")
                .withHeader("Port", "9191")
                .withBody(JsonBody.json(payment));
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        Payment payment = mapper.readValue(httpRequest.getBodyAsJsonOrXmlString(), Payment.class);
        payment.setCard(null);

        return HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(JsonBody.json(payment));
    }

}
