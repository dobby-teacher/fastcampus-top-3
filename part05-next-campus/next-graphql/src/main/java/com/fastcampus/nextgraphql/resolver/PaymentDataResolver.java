package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.UserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentDataResolver {
    private final UserService userService;

    public PaymentDataResolver(UserService userService) {
        this.userService = userService;
    }

    @SchemaMapping(typeName = "Payment", field = "user")
    public User getUser(Payment payment) {
        return userService.findById(payment.getUserId()).orElse(null);
    }
}
