package com.octest.dao;

import java.util.List;

import com.octest.beans.Etudiant;

public interface EtudiantDao {
    void ajouter( Etudiant etudiant );
    List<Etudiant> lister();
}