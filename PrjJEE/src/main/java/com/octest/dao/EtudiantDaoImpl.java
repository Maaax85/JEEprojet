package com.octest.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.octest.beans.BeanException;
import com.octest.beans.Etudiant;
import com.octest.servlets.PrimaryPage;

public class EtudiantDaoImpl implements EtudiantDao {
	private DaoFactory daoFactory;

	EtudiantDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Etudiant etudiant) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement(
					"INSERT INTO etudiant(nom, prenom, genre, previousSite, previousFormation) VALUES(?, ?, ?, ?, ?);");
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
		} finally {
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
			resultat = statement.executeQuery("SELECT * " + "FROM etudiant e "
					+ "LEFT OUTER JOIN equipe_etudiant ee ON e.id_etudiant = ee.id_etudiant "
					+ "WHERE ee.id_etudiant IS NULL;");

			while (resultat.next()) {
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				String genre = resultat.getString("genre");
				String previousSite = resultat.getString("previousSite");
				String previousFormation = resultat.getString("previousFormation");

				Etudiant etudiant = new Etudiant(nom, prenom, genre, previousSite, previousFormation);

				etudiants.add(etudiant);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données");
		} catch (BeanException e) {
			throw new DaoException("Les données de la base sont invalides");
		} finally {
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

	@Override
	public void loadEtus(HttpServletRequest request, String path) {
		try {
			Part part = request.getPart("fichier");

			String nomFichier = this.getNomFichier(part);
			if (nomFichier != null && !nomFichier.isEmpty()) {
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);

				this.ecrireFichier(part, nomFichier, path);
				this.addEtudiants(request, path, nomFichier);
			}
		} catch (Exception ePasDeFichier) {
			System.out.println("Pas de fichier ou mauvais type de fichier fourni");
		}
	}

	private String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void addEtudiants(HttpServletRequest request, String nomChamp, String nomFichier) throws IOException {
		File file = new File(nomChamp + nomFichier);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		String[] tempArr;
		while ((line = br.readLine()) != null) {
			tempArr = line.split(",");
			int index = 0;
			String[] infosEtu = new String[5];

			for (String tempStr : tempArr) {
				infosEtu[index] = tempStr;
				index++;
			}

			try {
				this.ajouter(new Etudiant(infosEtu));
			} catch (DaoException e) {
				request.setAttribute("erreur", e.getMessage());
			}

			request.setAttribute(nomChamp, nomFichier);
		}
		br.close();
	}

	private void ecrireFichier(Part part, String nomFichier, String chemin) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(part.getInputStream(), PrimaryPage.TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)),
					PrimaryPage.TAILLE_TAMPON);

			byte[] tampon = new byte[PrimaryPage.TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}

}