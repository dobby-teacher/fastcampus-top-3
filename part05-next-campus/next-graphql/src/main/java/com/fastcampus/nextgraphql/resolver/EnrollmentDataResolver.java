package com.fastcampus.nextgraphql.resolver;

import com.fastcampus.nextgraphql.model.Course;
import com.fastcampus.nextgraphql.model.Enrollment;
import com.fastcampus.nextgraphql.model.Payment;
import com.fastcampus.nextgraphql.model.User;
import com.fastcampus.nextgraphql.service.DummyCourseService;
import com.fastcampus.nextgraphql.service.DummyPaymentService;
import com.fastcampus.nextgraphql.service.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EnrollmentDataResolver {

    private final DummyUserService userService;
    private final DummyCourseService courseService;
    private final DummyPaymentService paymentService;

    @Autowired
    public EnrollmentDataResolver(DummyUserService userService, DummyCourseService courseService, DummyPaymentService paymentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.paymentService = paymentService;
    }

    @SchemaMapping(typeName = "Enrollment", field = "user")
    public User getUser(Enrollment enrollment) {
        return userService.findById(enrollment.getUserId()).orElse(null);
    }

    @SchemaMapping(typeName = "Enrollment", field = "course")
    public Course getCourse(Enrollment enrollment) {
        return courseService.findCourseById(enrollment.getCourseId()).orElse(null);
    }

    @SchemaMapping(typeName = "Enrollment", field = "payment")
    public Payment getPayment(Enrollment enrollment) {
        return paymentService.findPaymentById(enrollment.getPaymentId()).orElse(null);
    }
}
