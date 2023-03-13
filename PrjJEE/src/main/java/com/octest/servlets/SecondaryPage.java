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
//        try {
//			this.equipeDao.exportEquipeCSV(path);
//		} catch (DaoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
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

		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("boutonCompositionAutomatique")) {
				try {
					String critere = request.getParameter("critere");
					this.equipeDao.genererCompositionAuto(critere, this.nombreEquipeACreer);
				} catch (DaoException e) {
					e.printStackTrace();
				}
			} else if (action.equals("boutonLoadEtus")) {

			}
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/secondaryJ.jsp").forward(request, response);
	}

	public void setNombreEquipe(int nb) {
		this.nombreEquipeACreer = nb;
	}

}