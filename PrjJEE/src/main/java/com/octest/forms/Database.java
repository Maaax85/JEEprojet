package com.octest.forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private Connection con;
	private String url = "jdbc:mysql://localhost:3306/bdd_java2e";
	private String name = "root";
	private String password = "";
	
	public Database() {
	      try {
	         Class.forName("com.mysql.cj.jdbc.Driver"); //Mon ide ne trouve pas le jdbc Driver, donc le code plante ici
	         this.con = DriverManager.getConnection(url, name, password);
	         System.out.println("Connexion réussie !");
	      }
	      catch (SQLException | ClassNotFoundException e) {
	         System.err.println(e.getMessage());
	      }
	}
	
	public void closeDatabase() {
		try {
			this.con.close();
			System.out.println("Fermeture de la connexion");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.con;
	}
	
}