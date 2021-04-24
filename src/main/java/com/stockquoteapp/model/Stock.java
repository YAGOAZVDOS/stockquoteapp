package com.stockquoteapp.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock {
	
	@Id
	@Column(unique = true, nullable = false)
	private String name;
	
	private ArrayList<String> quotes;

	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getQuotes() {
		return quotes;
	}

	public void setQuotes(ArrayList<String> quotes) {
		this.quotes = quotes;
	}

	
}
