package com.octest.beans;

import com.octest.beans.Etudiant;

public class Equipe {

	private String name;
	private int id;
	public Etudiant[] membres;
	
	
	
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Etudiant[] getMembres() {
		return membres;
	}
	public void setMembres(Etudiant[] membres) {
		this.membres = membres;
	}
	
}
