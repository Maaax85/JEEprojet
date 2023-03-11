package com.octest.beans;

public class Etudiant {

	private String nom;
	private String prenom;
	private String genre;
	private String previousSite;
	private String previousFormation;

	public Etudiant(String nom, String prenom, String genre, String previousSite, String previousFormation)
			throws BeanException {
		if (nom.length() > 10) {
			throw new BeanException("Le nom est trop grand ! (10 caractères maximum)");
		} if (prenom.length() > 10) {
	        throw new BeanException("Le prénom est trop grand ! (10 caractères maximum)");
	    } else {
			this.nom = nom;
			this.prenom = prenom;
			this.genre = genre;
			this.previousSite = previousSite;
			this.previousFormation = previousFormation;
		}
	}

	public Etudiant(String[] infosEtu) {
		if (infosEtu.length == 5) {
			this.nom = infosEtu[0];
			this.prenom = infosEtu[1];
			this.genre = infosEtu[2];
			this.previousSite = infosEtu[3];
			this.previousFormation = infosEtu[4];

			System.out.println("Etudiant : " + infosEtu[0]);
		}
	}

	// Getters and Setters

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPreviousSite() {
		return previousSite;
	}

	public void setPreviousSite(String previousSite) {
		this.previousSite = previousSite;
	}

	public String getPreviousFormation() {
		return previousFormation;
	}

	public void setPreviousFormation(String previousFormation) {
		this.previousFormation = previousFormation;
	}
}
