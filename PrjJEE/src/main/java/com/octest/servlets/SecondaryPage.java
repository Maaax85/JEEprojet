package com.octest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.EquipeDao;
import com.octest.dao.EtudiantDao;

import resources.Config;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Secondary")
@MultipartConfig
public class SecondaryPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int TAILLE_TAMPON = 10240;
	String path = Config.PATH;
	int nombreEquipeACreer = 10;

	private EquipeDao equipeDao;
	private EtudiantDao etudiantDao;

	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.equipeDao = daoFactory.getEquipeDao();
		this.etudiantDao = daoFactory.getEtudiantDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setAttribute("equipes", equipeDao.listerEquipes());
			request.setAttribute("etudiantsSansEquipe", etudiantDao.listerSansGroupe());
			request.setAttribute("equipeDao", equipeDao);
		} catch (DaoException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/secondaryJ.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String genererCompoAuto = request.getParameter("boutonCompositionAutomatique");
		if (genererCompoAuto != null) {
			try {
				String critere = request.getParameter("critere");
				this.equipeDao.genererCompositionAuto(critere, this.nombreEquipeACreer);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		String equipeAdd = request.getParameter("equipeAdd");
		if (equipeAdd != null) {
			try {
				String nomEtudiant = request.getParameter("nomEtudiant");
				this.equipeDao.ajouterEtudiant(equipeAdd, nomEtudiant);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		String equipeRemove = request.getParameter("equipeRemove");
		String etudiantRemove = request.getParameter("etudiantRemove");
		if (equipeRemove != null && etudiantRemove != null) {
			try {
				this.equipeDao.retirerEtudiant(equipeRemove, etudiantRemove);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		String nbEquipe = request.getParameter("nombreEquipes");
		if (nbEquipe != null) {
			this.nombreEquipeACreer = Integer.parseInt(nbEquipe);
		}

		String newNameEquipe = request.getParameter("nouveauNomEquipe");
		if (newNameEquipe != null) {
			String nomEquipe = request.getParameter("equipeModifyName");
			try {
				this.equipeDao.changerNomEquipe(nomEquipe, newNameEquipe);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		
		String boutonExport = request.getParameter("boutonExport");
		if (boutonExport != null) {
			try {
				System.out.println("0");
				this.equipeDao.exportEquipeCSV(Config.PATH);
				System.out.println("1");
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect(request.getContextPath() + "/Secondary");
	}

}