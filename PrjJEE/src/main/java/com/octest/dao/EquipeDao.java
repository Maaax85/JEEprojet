package com.octest.dao;

import java.util.List;

import com.octest.beans.Equipe;
import com.octest.beans.Etudiant;

public interface EquipeDao {

	void ajouter(Equipe equipe) throws DaoException;

	List<Equipe> listerEquipes() throws DaoException;
	
	void ajouterEtudiant(String nomEquipe, String nomEtudiant) throws DaoException;
	
	void retirerEtudiant(String nomEquipe, String nomEtudiant) throws DaoException;

	void genererCompositionAuto(String critereGeneration, int nbEquipeACreer) throws DaoException;

	void changerNomEquipe(String nomEquipe, String nouveauNomEquipe) throws DaoException;

	List<Etudiant> listerEtudiantsEquipe(String nomEquipe) throws DaoException;

	void exportEquipeCSV(String path) throws DaoException;
}
