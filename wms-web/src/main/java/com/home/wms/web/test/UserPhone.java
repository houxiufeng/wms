package com.home.wms.web.test;

/**
 * Created by fitz on 2018/1/18.
 */
public class UserPhone {
	private Long id;
	private String name;
	private Integer age;
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static void main(String[] args) {
		String s = "000011";
		System.out.println(Integer.parseInt(s));
		Integer d = 3456;
		System.out.println(String.format("%06d", d));
	}
}
