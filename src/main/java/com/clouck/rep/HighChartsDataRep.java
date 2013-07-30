package com.clouck.rep;

public class HighChartsDataRep {
    private Integer y;
    private String id;
    private Long millisecond;

    public HighChartsDataRep(Integer y, String id, Long millisecond) {
        this.y = y;
        this.id = id;
        this.millisecond = millisecond;
    }
    
    public HighChartsDataRep clone(Long millisecond) {
        return new HighChartsDataRep(y, id, millisecond);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(Long millisecond) {
        this.millisecond = millisecond;
    }
}
