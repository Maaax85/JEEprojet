package com.octest.servlets;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Secondary")
@MultipartConfig
public class SecondaryPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int TAILLE_TAMPON = 10240;
	public static final String CHEMIN_FICHIERS = "C:/Users/ledum/Documents"; // A changer

	private static ArrayList<String> infos;


	public SecondaryPage() {
		super();
        //this.database = new Database(); //Voir modif à faire dedans
		infos = new ArrayList<String>();
		this.fillInfos(); //Et modifier intérieur de la méthode
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/secondaryJ.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("boutonCompositionAutomatique")) {

			} else if (action.equals("boutonLoadEtus")) {

			}
		}

		HttpSession session = request.getSession();
		this.getServletContext().getRequestDispatcher("/WEB-INF/secondaryJ.jsp").forward(request, response);
	}

	public static ArrayList<String> getInformations() {
		return infos;
	}
	
	public void fillInfos() {
//		try {
//			String queryCategory = "SELECT * FROM etudiant";
//			PreparedStatement statementCategory = this.database.getConnection().prepareStatement(queryCategory);
//	        ResultSet resultCategory = statementCategory.executeQuery();
//	        while (resultCategory.next()) {
//	            infos.add(resultCategory.getString("nom") + resultCategory.getString("prenom"));
//	        }
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally {
//			this.database.closeDatabase();
//		}
		
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		infos.add("Test");
		
	}
	

}