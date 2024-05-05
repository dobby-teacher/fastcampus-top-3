### 1. 등록(구매, 구독) 서비스 (Enrollment Service)
- gRPC 서비스 설계
```protobuf
// EnrollmentService는 강의 등록과 구독 관리를 위한 엔드포인트를 제공합니다.
// 이 서비스를 통해 사용자는 강의 등록, 구독 생성 및 갱신을 할 수 있으며,
// 특정 강의나 구독에 대한 접근 권한을 확인할 수 있습니다.
service EnrollmentService {
    // RegisterCourse는 새로운 강의 등록 요청을 처리합니다.
    rpc RegisterCourse(CourseRegistrationRequest) returns (CourseRegistrationResponse);

    // ManageSubscription는 사용자의 새로운 구독을 등록하는 요청을 처리합니다.
    rpc ManageSubscription(SubscriptionRequest) returns (SubscriptionResponse);

    // RenewSubscription은 기존 구독을 갱신하는 요청을 처리합니다.
    rpc RenewSubscription(RenewSubscriptionRequest) returns (SubscriptionResponse);

    // CheckCourseAccess는 특정 사용자가 특정 강의에 접근할 수 있는지 확인합니다.
    rpc CheckCourseAccess(CourseAccessRequest) returns (CourseAccessResponse);

    // CheckSubscriptionAccess는 특정 사용자가 특정 구독에 접근할 수 있는지 확인합니다.
    rpc CheckSubscriptionAccess(SubscriptionAccessRequest) returns (SubscriptionAccessResponse);
}

// FakePaymentService는 가상의 결제 거래를 시뮬레이션하는 엔드포인트를 제공합니다.
// 이 서비스는 강의 또는 구독에 대한 가짜 결제를 생성하는 데 사용됩니다.
// 요청이 성공하면 결제가 완료된 것으로 간주됩니다.
service FakePaymentService {
    // CreatePayment는 결제 거래 생성을 시뮬레이션합니다.
    rpc CreatePayment(PaymentRequest) returns (PaymentResponse);
}

```

- gRPC 모델 설계
```protobuf
// Requests and responses for the EnrollmentService
message CourseRegistrationRequest {
    int64 userId = 1;
    int64 courseId = 2;
    string registrationDate = 3; // Format: "YYYY-MM-DD"
}

message CourseRegistrationResponse {
    int64 courseId = 1;
    int64 userId = 2;
    string registrationDate = 3;
}

message SubscriptionRequest {
    int64 userId = 1;
    string subscriptionId = 2;
    string startDate = 3; // Format: "YYYY-MM-DD"
    string endDate = 4; // Format: "YYYY-MM-DD"
}

message RenewSubscriptionRequest {
    string subscriptionId = 1;
    string startDate = 2; // Format: "YYYY-MM-DD"
    string endDate = 3; // Format: "YYYY-MM-DD"
}

message SubscriptionResponse {
    string subscriptionId = 1;
    int64 userId = 2;
    string startDate = 3;
    string endDate = 4;
}

message CourseAccessRequest {
    int64 courseId = 1;
    int64 userId = 2;
}

message CourseAccessResponse {
    int64 courseId = 1;
    bool hasAccess = 2;
}

message SubscriptionAccessRequest {
    string subscriptionId = 1;
    int64 userId = 2;
}

message SubscriptionAccessResponse {
    string subscriptionId = 1;
    bool hasAccess = 2;
}

// Requests and responses for the FakePaymentService
message PaymentRequest {
    int64 userId = 1;
    string type = 2; // "COURSE" or "SUBSCRIPTION"
    int64 itemId = 3;
    double amount = 4;
    string paymentMethod = 5; // E.g., "CARD"
}

message PaymentResponse {
    int64 userId = 1;
    string type = 2;
    int64 itemId = 3;
    double amount = 4;
    bool paymentSuccessful = 5;
}
```