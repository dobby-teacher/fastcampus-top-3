package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanSubscription {
    private Long id;
    private User user;
    private Payment payment;
    private String startDate;
    private String endDate;
}
