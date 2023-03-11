package com.octest.beans;


public class Equipe {

	private String nom;
	int nombreEtu;
	public Etudiant[] membres;

	public Equipe(int nombreEtu, String nom, Etudiant[] membres) throws BeanException {
		if (membres.length != nombreEtu) {
			throw new BeanException("Le nombre d'étudiant ne correspond pas");}
		else {
			this.nom = nom;
			this.membres = membres;
		}
	}

	// Getters and Setters
	public int getNombreEtu() {
		return nombreEtu;
	}

	public void setNombreEtu(int nombreEtu) {
		this.nombreEtu = nombreEtu;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Etudiant[] getMembres() {
		return membres;
	}

	public void setMembres(Etudiant[] membres) {
		this.membres = membres;
	}

}
