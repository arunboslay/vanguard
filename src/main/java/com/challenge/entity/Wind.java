package com.challenge.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Wind {
    public double speed;
    public int deg;
}