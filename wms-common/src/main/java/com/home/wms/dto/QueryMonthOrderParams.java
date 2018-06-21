package com.home.wms.dto;

import java.io.Serializable;

/**
 * Created by fitz on 2018/6/13.
 */
public class QueryMonthOrderParams implements Serializable{
    private String monthBegin;
    private String monthEnd;
    private Long engineerId;
    private Long branchId;
    private Integer status;
    private Long organizationId;
    private Integer iDisplayStart = 0;
    private Integer iDisplayLength = 4;

    public String getMonthBegin() {
        return monthBegin;
    }

    public void setMonthBegin(String monthBegin) {
        this.monthBegin = monthBegin;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
    }

    public Long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Long engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
