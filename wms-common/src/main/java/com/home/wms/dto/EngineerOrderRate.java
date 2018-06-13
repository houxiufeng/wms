package com.home.wms.dto;

import java.io.Serializable;

/**
 * Created by fitz on 2018/6/13.
 */
public class EngineerOrderRate implements Serializable{
    private Integer year;
    private Integer month;
    private Long engineerId;
    private Long goodNum;
    private Long normalNum;
    private Long badNum;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Long engineerId) {
        this.engineerId = engineerId;
    }

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    public Long getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(Long normalNum) {
        this.normalNum = normalNum;
    }

    public Long getBadNum() {
        return badNum;
    }

    public void setBadNum(Long badNum) {
        this.badNum = badNum;
    }
}
