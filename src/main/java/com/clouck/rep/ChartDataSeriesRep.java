package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

public class ChartDataSeriesRep {

    private List<List<Long>> data = new ArrayList<>();

    public void addData(List<Long> dataRep) {
        data.add(dataRep);
    }

    public List<List<Long>> getData() {
        return data;
    }

    public void setData(List<List<Long>> data) {
        this.data = data;
    }
}
