package com.challenge.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Main {
    public double temp;
    public int pressure;
    public int humidity;
    public double temp_min;
    public double temp_max;
}