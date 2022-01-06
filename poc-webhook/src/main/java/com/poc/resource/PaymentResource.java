package com.poc.resource;

import com.poc.model.Payment;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/payment")
public class PaymentResource {

    @Post("/register/forward")
    public void forward(@Body Payment payment) {
        log.info("Payment: {}, card: {}, date: {}", payment.getId(), payment.getCard(), payment.getDate());
    }
}
