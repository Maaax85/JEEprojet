package com.octest.dao;

import java.util.List;

import com.octest.beans.Etudiant;

public interface EtudiantDao {
    void ajouter( Etudiant etudiant ) throws DaoException;
    List<Etudiant> lister() throws DaoException;
}