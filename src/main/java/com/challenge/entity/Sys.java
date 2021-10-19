package com.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Sys {
    public int type;
    @Column(name = "sysid")
    public int id;
    public double message;
    public String country;
    public int sunrise;
    public int sunset;
}
