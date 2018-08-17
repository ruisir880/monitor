package com.ray.monitor.model;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable{

    private String username;
    private List<Weight> weights;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Weight> getWeights() {
        return weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }


}

