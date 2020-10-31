package com.example.coronatracker.model;

import java.io.Serializable;
import java.util.List;


public class City implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String state;
	
	private Integer totalInfected;
	
	
	public City() {};

	public City(Long id, String name, String state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}

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
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public Integer getTotalInfected() {
		return totalInfected;
	}

	public void setTotalInfected(Integer totalInfected) {
		this.totalInfected = totalInfected;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

}
