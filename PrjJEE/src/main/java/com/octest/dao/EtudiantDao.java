package com.octest.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.octest.beans.Etudiant;

public interface EtudiantDao {
    void ajouter( Etudiant etudiant ) throws DaoException;
    List<Etudiant> listerSansGroupe() throws DaoException;
    void loadEtus(HttpServletRequest request, String path) throws DaoException;
}