package com.octest.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.octest.beans.BeanException;
import com.octest.beans.Equipe;
import com.octest.beans.Etudiant;

public class EquipesDaoImpl implements EquipeDao {
	private DaoFactory daoFactory;
	private EtudiantDao etudiantDao;

	EquipesDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.etudiantDao = daoFactory.getEtudiantDao();
	}

	@Override
	public void ajouter(Equipe equipe) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO equipe(nom) VALUES(?)");
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
	public List<Equipe> listerEquipes() throws DaoException {
		List<Equipe> equipes = new ArrayList<Equipe>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat1 = null;
		ResultSet resultat2 = null;
		ResultSet resultat3 = null;

		try {

			String queryCount = "SELECT COUNT(*) FROM etudiant "
					+ "JOIN equipe_etudiant ON etudiant.id_etudiant = equipe_etudiant.id_etudiant "
					+ "JOIN equipe ON equipe.id_equipe = equipe_etudiant.id_equipe " + "WHERE equipe.nom = ?";
			String querySelect = "SELECT etudiant.* FROM etudiant "
					+ "JOIN equipe_etudiant ON etudiant.id_etudiant = equipe_etudiant.id_etudiant "
					+ "JOIN equipe ON equipe.id_equipe = equipe_etudiant.id_equipe " + "WHERE equipe.nom = ?";

			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			PreparedStatement stmtCount = connexion.prepareStatement(queryCount);
			PreparedStatement stmtSelect = connexion.prepareStatement(querySelect);
			resultat1 = statement.executeQuery("SELECT * FROM equipe;");

			while (resultat1.next()) {
				String nom = resultat1.getString("nom");

				stmtCount.setString(1, nom);
				resultat2 = stmtCount.executeQuery();
				resultat2.next();
				int nombreEtu = resultat2.getInt(1);

				stmtSelect.setString(1, nom);
				resultat3 = stmtSelect.executeQuery();

				List<Etudiant> Etudiants = new ArrayList<>();
				while (resultat3.next()) {
					String nomEtu = resultat3.getString("nom");
					String prenomEtu = resultat3.getString("prenom");
					String genreEtu = resultat3.getString("genre");
					String previousSiteEtu = resultat3.getString("previousSite");
					String previousFormationEtu = resultat3.getString("previousFormation");

					Etudiant etudiant = new Etudiant(nomEtu, prenomEtu, genreEtu, previousSiteEtu,
							previousFormationEtu);
					Etudiants.add(etudiant);
				}
				Equipe equipe = new Equipe(nombreEtu, nom, Etudiants);
				equipes.add(equipe);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Impossible de communiquer avec la base de données 1");
		} catch (BeanException e) {
			throw new DaoException("Les données de la base sont invalides");
		} finally {
			try {
				if (connexion != null) {
					connexion.close();
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données 2");
			}
		}
		return equipes;
	}

	@Override
	public void ajouterEtudiant(String nomEquipe, String nomEtudiant) throws DaoException {

		try {
			for (int i = 0; i < this.listerEquipes().size(); i++) {
				if (this.listerEquipes().get(i).getNom().equals(nomEquipe)
						&& this.listerEquipes().get(i).getNombreEtu() < 6) {
					boolean etuEstPresent = false;
					for (int j = 0; j < this.listerEquipes().get(i).getNombreEtu(); j++) {
						if (this.listerEquipes().get(i).getMembres().get(j).getNom().equals(nomEtudiant)) {
							etuEstPresent = true;
						}
					}
					if (!etuEstPresent) {
						Connection connexion = null;
						try {
							ResultSet resultatIdEquipe = null;
							connexion = daoFactory.getConnection();
							String querySelectIdEquipe = "SELECT equipe.id_equipe FROM equipe WHERE equipe.nom = ?";
							PreparedStatement stmtSelectIdEquipe = connexion.prepareStatement(querySelectIdEquipe);
							stmtSelectIdEquipe.setString(1, nomEquipe);
							resultatIdEquipe = stmtSelectIdEquipe.executeQuery();
							resultatIdEquipe.next();

							ResultSet resultatIdEtudiant = null;
							String querySelectIdEtudiant = "SELECT etudiant.id_etudiant FROM etudiant WHERE etudiant.nom = ?";
							PreparedStatement stmtSelectIdEtudiant = connexion.prepareStatement(querySelectIdEtudiant);
							stmtSelectIdEtudiant.setString(1, nomEtudiant);
							resultatIdEtudiant = stmtSelectIdEtudiant.executeQuery();
							resultatIdEtudiant.next();
							
							PreparedStatement preparedStatementInsert = null;
							preparedStatementInsert = connexion.prepareStatement(
									"INSERT INTO equipe_etudiant(id_equipe, id_etudiant) VALUES(?, ?);");
							preparedStatementInsert.setString(1, resultatIdEquipe.getString("id_equipe"));
							System.out.println("0");
							preparedStatementInsert.setString(2, resultatIdEtudiant.getString("id_etudiant"));
							System.out.println("1");
							System.out.println(preparedStatementInsert.toString());
							preparedStatementInsert.executeUpdate();

						} catch (SQLException e) {
							try {
								if (connexion != null) {
									connexion.rollback();
								}
							} catch (SQLException e2) {
							}
							throw new DaoException("Impossible de communiquer avec la base de données 1");
						} finally {
							try {
								if (connexion != null) {
									connexion.close();
								}
							} catch (SQLException e) {
								throw new DaoException("Impossible de communiquer avec la base de données 2");
							}
						}
					}
				}
			}
		} catch (DaoException e) {
			throw new DaoException("Impossible de communiquer avec la base de données 3");
		}

	}

	@Override
	public void retirerEtudiant(String nomEquipe, String nomEtudiant) throws DaoException {
		try {
			for (int i = 0; i < this.listerEquipes().size(); i++) {
				if (this.listerEquipes().get(i).getNom().equals(nomEquipe)
						&& this.listerEquipes().get(i).getNombreEtu() < 6) {
					for (int j = 0; j < this.listerEquipes().get(i).getNombreEtu(); j++) {
						if (this.listerEquipes().get(i).getMembres().get(j).getNom().equals(nomEtudiant)) {
							Connection connexion = null;
							try {
								ResultSet resultatIdEquipe = null;
								connexion = daoFactory.getConnection();
								String querySelectIdEquipe = "SELECT equipe.id_equipe FROM equipe WHERE equipe.nom = ?;";
								PreparedStatement stmtSelectIdEquipe = connexion.prepareStatement(querySelectIdEquipe);
								stmtSelectIdEquipe.setString(1, nomEquipe);
								resultatIdEquipe = stmtSelectIdEquipe.executeQuery();

								ResultSet resultatIdEtudiant = null;
								String querySelectIdEtudiant = "SELECT equipe.id_equipe FROM equipe WHERE equipe.nom = ?;";
								PreparedStatement stmtSelectIdEtudiant = connexion
										.prepareStatement(querySelectIdEtudiant);
								stmtSelectIdEtudiant.setString(1, nomEquipe);
								resultatIdEtudiant = stmtSelectIdEtudiant.executeQuery();

								PreparedStatement preparedStatementDelete = null;
								preparedStatementDelete = connexion.prepareStatement(
										"DELETE FROM equipe_etudiant WHERE id_equipe = ? AND id_etudiant = ?;");
								preparedStatementDelete.setString(1, resultatIdEquipe.getString("id_equipe"));
								preparedStatementDelete.setString(2, resultatIdEtudiant.getString("id_eleve"));
								preparedStatementDelete.executeUpdate();

							} catch (SQLException e) {
								try {
									if (connexion != null) {
										connexion.rollback();
									}
								} catch (SQLException e2) {
								}
								throw new DaoException("Impossible de communiquer avec la base de données 1");
							} finally {
								try {
									if (connexion != null) {
										connexion.close();
									}
								} catch (SQLException e) {
									throw new DaoException("Impossible de communiquer avec la base de données 2");
								}
							}
						}
					}
				}
			}
		} catch (DaoException e) {
			throw new DaoException("Impossible de communiquer avec la base de données 3");
		}

	}

	@Override
	public void genererCompositionAuto(String critereGeneration, int nbEquipeACreer) throws DaoException {
		if (critereGeneration.equals("Random")) {
			try {
				int nbEtuParEquipe = this.etudiantDao.listerSansGroupe().size() / nbEquipeACreer;
				int nbEtuRestant = this.etudiantDao.listerSansGroupe().size() % nbEquipeACreer;

				Random random = new Random();

				for (int i = 0; i < nbEquipeACreer; i++) {
					String nomEquipe = "Equipe " + (i + 1);
					try {
						this.ajouter(new Equipe(0, nomEquipe, null));
					} catch (BeanException e) {
						e.printStackTrace();
					}

					for (int j = 0; j < nbEtuParEquipe; j++) {
						int numeroRandomEtudiant = random.nextInt(this.etudiantDao.listerSansGroupe().size());
						this.ajouterEtudiant(nomEquipe,
								this.etudiantDao.listerSansGroupe().get(numeroRandomEtudiant).getNom());
					}
					if (nbEtuRestant != 0) {
						int numeroRandomEtudiant = random.nextInt(this.etudiantDao.listerSansGroupe().size());
						this.ajouterEtudiant(nomEquipe,
								this.etudiantDao.listerSansGroupe().get(numeroRandomEtudiant).getNom());
						nbEtuRestant--;
					}
				}

			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else if (critereGeneration == "Alphabetique") {
			try {
				int nbEtuParEquipe = this.etudiantDao.listerSansGroupe().size() / nbEquipeACreer;
				int nbEtuRestant = this.etudiantDao.listerSansGroupe().size() % nbEquipeACreer;

				for (int i = 0; i < nbEquipeACreer; i++) {
					String nomEquipe = "Equipe " + (i + 1);
					try {
						this.ajouter(new Equipe(0, nomEquipe, null));
					} catch (BeanException e) {
						e.printStackTrace();
					}

					for (int j = 0; j < nbEtuParEquipe; j++) {
						int indiceEtu = 0;
						for (int k = 1; k < this.etudiantDao.listerSansGroupe().size(); k++) {
							int resultat = this.etudiantDao.listerSansGroupe().get(indiceEtu).getNom()
									.compareTo(this.etudiantDao.listerSansGroupe().get(k).getNom());
							if (resultat > 0) {
								indiceEtu = k;
							}
						}
						this.ajouterEtudiant(nomEquipe, this.etudiantDao.listerSansGroupe().get(indiceEtu).getNom());
					}
					if (nbEtuRestant != 0) {
						int indiceEtu = 0;
						for (int k = 1; k < this.etudiantDao.listerSansGroupe().size(); k++) {
							int resultat = this.etudiantDao.listerSansGroupe().get(indiceEtu).getNom()
									.compareTo(this.etudiantDao.listerSansGroupe().get(k).getNom());
							if (resultat > 0) {
								indiceEtu = k;
							}
						}
						this.ajouterEtudiant(nomEquipe, this.etudiantDao.listerSansGroupe().get(indiceEtu).getNom());
						nbEtuRestant--;
					}

				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void changerNomEquipe(String nomEquipe, String nouveauNomEquipe) throws DaoException {
		for (int i = 0; i < this.listerEquipes().size(); i++) {
			if (this.listerEquipes().get(i).getNom().equals(nomEquipe)) {
				Connection connexion = null;
				try {
					connexion = daoFactory.getConnection();
					PreparedStatement preparedStatementUpdate = null;
					preparedStatementUpdate = connexion
							.prepareStatement("UPDATE equipe SET nom = ? WHERE nom_equipe = ?;");
					preparedStatementUpdate.setString(1, nouveauNomEquipe);
					preparedStatementUpdate.setString(1, nomEquipe);
					preparedStatementUpdate.executeUpdate();
				} catch (SQLException e) {
					try {
						if (connexion != null) {
							connexion.rollback();
						}
					} catch (SQLException e2) {
					}
					throw new DaoException("Impossible de communiquer avec la base de données 1");
				} finally {
					try {
						if (connexion != null) {
							connexion.close();
						}
					} catch (SQLException e) {
						throw new DaoException("Impossible de communiquer avec la base de données 2");
					}
				}
			}
		}
	}

	public void exportEquipeCSV(String path) throws DaoException {
		String csvFilePath = path;
		String[] columnHeaders = { "Equipe", "Nom", "Prenom", "Genre", "PreviousFormation", "PreviousSite" };
		String[][] data = new String[this.listerEquipes().size()][6];

		for (int i = 0; i < this.listerEquipes().size(); i++) {
			String nomEquipe = this.listerEquipes().get(i).getNom();
			for (int k = 0; k < this.listerEquipes().get(i).getMembres().size(); k++) {
				List<Etudiant> listeEtu = this.listerEquipes().get(i).getMembres();
				data[i] = new String[] { nomEquipe, listeEtu.get(k).getNom(), listeEtu.get(k).getPrenom(),
						listeEtu.get(k).getGenre(), listeEtu.get(k).getPreviousFormation(),
						listeEtu.get(k).getPreviousSite() };
			}
		}

		try (FileWriter writer = new FileWriter(csvFilePath + "Equipe.csv")) {
			// Écrire les en-têtes de colonne
			writer.write(String.join(",", columnHeaders));
			writer.write("\n");

			// Écrire les données dans le fichier
			for (String[] row : data) {
				writer.write(String.join(",", row));
				writer.write("\n");
			}

			System.out.println("Le fichier CSV a été créé avec succès !");
		} catch (IOException e) {
			System.out.println("Une erreur s'est produite lors de la création du fichier CSV.");
			e.printStackTrace();
		}
	}

	public List<Etudiant> listerEtudiantsEquipe(String nomEquipe) throws DaoException {
		List<Etudiant> listeEtudiant = null;
		for (int i = 0; i < this.listerEquipes().size(); i++) {
			if (this.listerEquipes().get(i).getNom().equals(nomEquipe)) {
				listeEtudiant = this.listerEquipes().get(i).getMembres();
			}
		}
		return listeEtudiant;
	}

}