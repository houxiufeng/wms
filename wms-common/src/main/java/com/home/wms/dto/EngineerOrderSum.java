package com.home.wms.dto;

import cn.hutool.core.date.DateUtil;
import com.home.wms.utils.MyUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fitz on 2018/6/13.
 */
public class EngineerOrderSum implements Serializable{
    private Integer year;
    private Integer month;
//    private String monthBegin;
//    private String monthEnd;
    private Long engineerId;
    private Long checkingNum;
    private Long fixingNum;
    private Long completeNum;

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

    public String getMonthBegin() {
        if (this.year != null && this.month != null) {
            return MyUtils.getFirstDayOfMonth(this.year, this.month);
        }
        return "";
    }

    public String getMonthEnd() {
        if (this.year != null && this.month != null) {
            return MyUtils.getLastDayOfMonth(this.year, this.month);
        }
        return "";
    }

    public Long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Long engineerId) {
        this.engineerId = engineerId;
    }

    public Long getCheckingNum() {
        return checkingNum;
    }

    public void setCheckingNum(Long checkingNum) {
        this.checkingNum = checkingNum;
    }

    public Long getFixingNum() {
        return fixingNum;
    }

    public void setFixingNum(Long fixingNum) {
        this.fixingNum = fixingNum;
    }

    public Long getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Long completeNum) {
        this.completeNum = completeNum;
    }
}
