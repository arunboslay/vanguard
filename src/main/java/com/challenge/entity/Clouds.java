package com.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Clouds {
    @Column(name = "`all`")
    public int all;
}