package com.biroas.poc.file.search.api.model.query;

public class RangeNumeric {

    private Integer from;
    private Integer to;

    public RangeNumeric() {

    }

    public RangeNumeric(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from: ").append(from);
        stringBuilder.append(" to: ").append(to);
        return stringBuilder.toString();
    }
}