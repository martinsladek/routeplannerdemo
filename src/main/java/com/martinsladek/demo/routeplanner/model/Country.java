package com.martinsladek.demo.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private String cca3;
    private List<String> borders;
    private List<Double> latlng;
    @JsonProperty("capitalInfo")
    private CapitalInfo capitalInfo;

    public String getCca3() {
        return cca3;
    }

    public List<String> getBorders() {
        return borders;
    }

    public double getLat() {
        if (capitalInfo != null && capitalInfo.latlng != null && capitalInfo.latlng.size() == 2) {
            return capitalInfo.latlng.get(0);
        }

        return latlng != null && latlng.size() > 0 ? latlng.get(0) : 0.0;
    }

    public double getLng() {
        if (capitalInfo != null && capitalInfo.latlng != null && capitalInfo.latlng.size() == 2) {
            return capitalInfo.latlng.get(1);
        }

        return latlng != null && latlng.size() > 1 ? latlng.get(1) : 0.0;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CapitalInfo {
        @JsonProperty("latlng")
        public List<Double> latlng;

//        public List<Double> getLatlng() {
//            return latlng;
//        }
    }

//    public CapitalInfo getCapitalInfo() {
//        return capitalInfo;
//    }
}
