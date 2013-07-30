package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

public class ChartDataRep {

    private List<ChartDataSeriesRep> series = new ArrayList<>();

    public void addSerie(ChartDataSeriesRep serie) {
        series.add(serie);
    }

    public List<ChartDataSeriesRep> getSeries() {
        return series;
    }

    public void setSeries(List<ChartDataSeriesRep> series) {
        this.series = series;
    }
}
