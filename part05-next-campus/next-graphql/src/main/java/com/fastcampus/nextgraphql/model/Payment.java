package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long userId;
    private User user;
    private String paymentType;
    private Float amount;
    private String paymentMethod;
    private String paymentDate;
}
