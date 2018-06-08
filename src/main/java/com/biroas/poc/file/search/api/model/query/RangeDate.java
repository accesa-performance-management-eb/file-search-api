package com.biroas.poc.file.search.api.model.query;

import java.util.Date;

public class RangeDate {
    private Date from;
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("from: ").append(from);
        stringBuilder.append(" to: ").append(to);
        return stringBuilder.toString();
    }
}
