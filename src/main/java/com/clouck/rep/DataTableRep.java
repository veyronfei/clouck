package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

public class DataTableRep {

    private Integer sEcho;
    private Integer iTotalRecords;
    private Integer iTotalDisplayRecords;
    private List<List<String>> aaData = new ArrayList<>();

    public void addRow(List<String> row) {
        aaData.add(row);
    }

    public List<List<String>> getAaData() {
        return aaData;
    }

    public void setAaData(List<List<String>> aaData) {
        this.aaData = aaData;
    }

    public Integer getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Integer iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Integer getsEcho() {
        return sEcho;
    }

    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }
}
