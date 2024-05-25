package com.fastcampus.nextgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private User user;
    private String paymentType; // This can be an enum if defined elsewhere
    private Float amount;
    private String paymentMethod;
    private String paymentDate;
}
