package com.octest.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Etudiant;

public class EtudiantDaoImpl implements EtudiantDao {
    private DaoFactory daoFactory;

    EtudiantDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Etudiant etudiant) throws DaoException{
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
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }

    }
    
    

    @Override
    public List<Etudiant> listerSansGroupe() throws DaoException {
        List<Etudiant> etudiants = new ArrayList<Etudiant>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * "
            		+ "FROM etudiant e "
            		+ "LEFT OUTER JOIN equipe_etudiant ee ON e.id_etudiant = ee.id_etudiant "
            		+ "WHERE ee.id_etudiant IS NULL;");

            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String genre = resultat.getString("genre");
                String previousSite = resultat.getString("previousSite");
                String previousFormation = resultat.getString("previousFormation");

                Etudiant etudiant = new Etudiant(nom,prenom,genre,previousSite,previousFormation);
                
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        } catch (BeanException e) {
            throw new DaoException("Les données de la base sont invalides");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return etudiants;
    }

}