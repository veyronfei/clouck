package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

public class HighChartsRep {

    private List<String> categories = new ArrayList<>();
    private List<HighChartsSeriesRep> series = new ArrayList<>();

    public void addSerie(HighChartsSeriesRep serie) {
        series.add(serie);
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<HighChartsSeriesRep> getSeries() {
        return series;
    }

    public void setSeries(List<HighChartsSeriesRep> series) {
        this.series = series;
    }
}
