package com.fangpengfei.emp.entity;

public class Emp {
	private int id;
	private String name;
	private String gender;
	private String address;
	private double salary;
	private int detpId;
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Emp(int id, String name, String gender, String address, double salary, int detpId) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.salary = salary;
		this.detpId = detpId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getDetpId() {
		return detpId;
	}
	public void setDetpId(int detpId) {
		this.detpId = detpId;
	}
	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", gender=" + gender + ", address=" + address + ", salary=" + salary
				+ ", detpId=" + detpId + "]";
	}
	
	
}
