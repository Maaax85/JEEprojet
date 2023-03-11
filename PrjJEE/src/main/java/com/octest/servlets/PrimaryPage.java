package com.octest.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.servlet.http.Part;

import com.octest.beans.Etudiant;
import com.octest.dao.DaoFactory;
import com.octest.dao.EtudiantDao;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Primary")
@MultipartConfig
public class PrimaryPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String path = getServletContext().getInitParameter("PATH");
    
    public static final int TAILLE_TAMPON = 10240;
	private EtudiantDao etudiantDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.etudiantDao = daoFactory.getEtudiantDao();
    }   
    public void addEtudiants(HttpServletRequest request, String nomChamp, String nomFichier) throws IOException {
    	File file = new File(nomChamp+nomFichier);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tempArr;
        while((line = br.readLine()) != null) {
           tempArr = line.split(",");
           int index=0;
           String[] infosEtu = new String[5];
           
           for(String tempStr : tempArr) {
              infosEtu[index] = tempStr;
              index++;
           }
           
           etudiantDao.ajouter(new Etudiant(infosEtu));
        
	       request.setAttribute(nomChamp, nomFichier);
		}
	    br.close();
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
                
                Etudiant etudiant = new Etudiant(nom, prenom, genre, previousSite, previousFormation);
                etudiantDao.ajouter(etudiant);
                
            } else if (action.equals("boutonLoadEtus")) {
            	
	        	try {
	            	// On r�cup�re le champ du fichier
	            	Part part = request.getPart("fichier");
	            	
	            	// On v�rifie qu'on a bien re�u un fichier
	                String nomFichier = this.getNomFichier(part);
	
	                // Si on a bien un fichier
	                if (nomFichier != null && !nomFichier.isEmpty()) {
	                    String nomChamp = part.getName();
	                    // Corrige un bug du fonctionnement d'Internet Explorer
	                     nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
	                            .substring(nomFichier.lastIndexOf('\\') + 1);
	
	                    // On �crit d�finitivement le fichier sur le disque
	                    ecrireFichier(part, nomFichier, path);
	                     
	                    // On r�cup�re les infos du fichier et on cr�� les �tudiants r�sultants
	                    this.addEtudiants(request, path, nomFichier);
	                }
	            }
	            catch ( Exception ePasDeFichier ){
	            	System.out.println("Pas de fichier ou mauvais type de fichier fourni");
	            }
            }
        }
        
        //TODO : Cr�er seconde page
        
        HttpSession session = request.getSession();
        this.getServletContext().getRequestDispatcher("/WEB-INF/primaryJ.jsp").forward(request, response);
    }
    
    private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
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
    
    private static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }   

}