package com.clouck.comparator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class CompareResult<R> {
    private List<R> add = new ArrayList<>();
    private List<R> delete = new ArrayList<>();
    private List<Pair<R, R>> update = new ArrayList<>();

    public List<R> getAdd() {
        return add;
    }

    public void setAdd(List<R> add) {
        this.add = add;
    }

    public List<R> getDelete() {
        return delete;
    }

    public void setDelete(List<R> delete) {
        this.delete = delete;
    }

    public List<Pair<R, R>> getUpdate() {
        return update;
    }

    public void setUpdate(List<Pair<R, R>> update) {
        this.update = update;
    }
}
