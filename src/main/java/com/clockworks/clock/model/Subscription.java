package com.clockworks.clock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Subscription {
    private Date date;
    private int frequency;
}
