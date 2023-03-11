package com.octest.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Equipe;
import com.octest.beans.Etudiant;

public class EquipesDaoImpl implements EquipeDao {
    private DaoFactory daoFactory;

    EquipesDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Equipe equipe) throws DaoException{
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO etudiant(nom, prenom, genre, previousSite, previousFormation) VALUES(?, ?, ?, ?, ?);");
            preparedStatement.setString(1, equipe.getNom());
            

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
    public List<Equipe> listerEquipes() throws DaoException {
        List<Equipe> equipes = new ArrayList<Equipe>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat1 = null;
        ResultSet resultat2 = null;
        ResultSet resultat3 = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat1 = statement.executeQuery("SELECT * FROM equipe;");
            

            while (resultat1.next()) {
                
            	String nom = resultat1.getString("nom");
            	resultat2 = statement.executeQuery("SELECT COUNT(*) FROM etudiant "
                		+ "JOIN equipe_etudiant ON etudiant.id_etudiant = equipe_etudiant.id_etudiant "
                		+ "JOIN equipe ON equipe.id_equipe = equipe_etudiant.id_equipe "
                		+ "WHERE equipe.nom = "+ nom +";");
            	int nombreEtu = resultat2.getInt(1);
            	resultat3 = statement.executeQuery("SELECT etudiant.* FROM etudiant "
            			+ "JOIN equipe_etudiant ON etudiant.id_etudiant = equipe_etudiant.id_etudiant "
            			+ "JOIN equipe ON equipe.id_equipe = equipe_etudiant.id_equipe "
            			+ "WHERE equipe.nom = "+ nom +";");
            	List<Etudiant> Etudiants = new ArrayList<>();
            	while (resultat3.next()) {
            		String nomEtu = resultat3.getString("nom");
                    String prenomEtu = resultat3.getString("prenom");
                    String genreEtu = resultat3.getString("genre");
                    String previousSiteEtu = resultat3.getString("previousSite");
                    String previousFormationEtu = resultat3.getString("previousFormation");
                    
                    Etudiant etudiant = new Etudiant(nomEtu,prenomEtu,genreEtu,previousSiteEtu,previousFormationEtu);
                    Etudiants.add(etudiant);
                }
            	Etudiant[] membres = Etudiants.toArray(new Etudiant[Etudiants.size()]);
                Equipe equipe = new Equipe(nombreEtu,nom,membres);

                equipes.add(equipe);
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
        return equipes;
    }

}