package com.challenge.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Weather {

    public int id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer weatherId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "weatherInfoId")
    private WeatherInfo weatherInfo;
    private String main;
    private String description;
    private String icon;
}