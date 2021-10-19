package com.challenge.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer weatherInfoId;
    @Embedded
    private Coord coord;
    @OneToMany(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private List<Weather> weather;
    private String base;
    @Embedded
    private Main main;
    private int visibility;
    @Embedded
    private Wind wind;
    @Embedded
    private Clouds clouds;
    private long dt;
    @Embedded
    private Sys sys;
    @Column(name = "responseid")
    private long id;
    private String name;
    private int cod;
    private String cityCode;
    private String countryCode;


}
