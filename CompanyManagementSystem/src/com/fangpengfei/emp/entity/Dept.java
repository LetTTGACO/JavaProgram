package com.fangpengfei.emp.entity;

public class Dept {
	private int id;
	private String name;

	public Dept(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public Dept() {
		super();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + "]";
	}

}
