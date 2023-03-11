package com.octest.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.octest.beans.Etudiant;

public class EtudiantDaoImpl implements EtudiantDao {
    private DaoFactory daoFactory;

    EtudiantDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Etudiant etudiant) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO etudiant(nom, prenom, genre, previousSite, previousFormation) VALUES(?, ?, ?, ?, ?);");
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getGenre());
            preparedStatement.setString(4, etudiant.getPreviousSite());
            preparedStatement.setString(5, etudiant.getPreviousFormation());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    

    @Override
    public List<Etudiant> lister() {
        List<Etudiant> etudiants = new ArrayList<Etudiant>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT nom, prenom FROM etudiant;");

            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");

                Etudiant etudiant = new Etudiant("nom","prenom","genre","previousSite","previousFormation");
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);

                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

}