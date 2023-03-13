package com.octest.beans;

import java.util.List;

public class Equipe {

	private String nom;
	int nombreEtu;
	 List<Etudiant> membres;

	@Override
	public String toString() {
		return "Equipe [nom=" + nom + "]";
	}

	public Equipe(int nombreEtu, String nom, List<Etudiant> membres) throws BeanException {
		if (membres.size() != nombreEtu) {
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

	public List<Etudiant> getMembres() {
		return membres;
	}

	public void setMembres( List<Etudiant> membres) {
		this.membres = membres;
	}

}
