package com.ray.monitor.model;

import java.io.Serializable;

public class Weight implements Serializable {
    private double weight;
    private String wdate;

    public Weight(double weight, String wdate) {
        this.weight = weight;
        this.wdate = wdate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }
}