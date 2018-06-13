package com.home.wms.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by fitz on 2018/6/12.
 */
public class StatOrderVo implements Serializable{
    private Object[][] ticks;
    private int[][] data;

    public Object[][] getTicks() {
        return ticks;
    }

    public void setTicks(Object[][] ticks) {
        this.ticks = ticks;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public String getTicksStr() {
        return JSON.toJSONString(this.ticks);
    }

    public String getDataStr() {
        return JSON.toJSONString(this.data);
    }
}
