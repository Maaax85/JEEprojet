package com.octest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octest.beans.BeanException;
import com.octest.beans.Etudiant;
import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.EtudiantDao;
import resources.Config;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Primary")
@MultipartConfig
public class PrimaryPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String path = Config.PATH;
    
    public static final int TAILLE_TAMPON = 10240;
	private EtudiantDao etudiantDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.etudiantDao = daoFactory.getEtudiantDao();
    } 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.getServletContext().getRequestDispatcher("/WEB-INF/primaryJ.jsp").forward(request, response);
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

    	String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("boutonAddEtu")) {
            	String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String genre = request.getParameter("genre");
                String previousSite = request.getParameter("previousSite");
                String previousFormation = request.getParameter("previousFormation");
                
                Etudiant etudiant;
				try {
					etudiant = new Etudiant(nom, prenom, genre, previousSite, previousFormation);
					etudiantDao.ajouter(etudiant);
                } catch (BeanException | DaoException e) {
                	request.setAttribute("erreur", e.getMessage());
                }
				
            } else if (action.equals("boutonLoadEtus")) {
            	try {
					this.etudiantDao.loadEtus(request, Config.PATH);
				} catch (DaoException e) {
					e.printStackTrace();
				}
            }
        } 
        this.getServletContext().getRequestDispatcher("/WEB-INF/primaryJ.jsp").forward(request, response);
    }
}