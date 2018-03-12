package com.home.wms.dto;

import java.io.Serializable;

/**
 * Created by fitz on 2018/1/30.
 */
public class WMSResponse implements Serializable{
	private int code = 0;
	private String msg;
	private Object data;

	public WMSResponse(){}

	public WMSResponse(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public WMSResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
