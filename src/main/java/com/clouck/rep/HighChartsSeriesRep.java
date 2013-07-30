package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

public class HighChartsSeriesRep {

    private String name;
    private List<HighChartsDataRep> data = new ArrayList<>();
    private String color;

    public void addData(HighChartsDataRep dataRep) {
        getData().add(dataRep);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HighChartsDataRep> getData() {
        return data;
    }

    public void setData(List<HighChartsDataRep> data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
