package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.PlanSubscription;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.DummyPaymentService;
import com.fastcampus.nextgraphql.service.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PlanSubscriptionDataResolver {

    private final DummyUserService userService;
    private final DummyPaymentService paymentService;

    @Autowired
    public PlanSubscriptionDataResolver(DummyUserService userService, DummyPaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @SchemaMapping(typeName = "PlanSubscription", field = "user")
    public User getUser(PlanSubscription subscription) {
        return userService.findById(subscription.getUserId()).orElse(null);
    }

    @SchemaMapping(typeName = "PlanSubscription", field = "payment")
    public Payment getPayment(PlanSubscription subscription) {
        return paymentService.findPaymentById(subscription.getPaymentId()).orElse(null);
    }
}
