/**
 * 
 */
package org.iita.accessions2.servlet;

/**
 * @author ken
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccessObject {
	private static Connection dbConnection = null;
	private static final String dbDriver = "com.mysql.jdbc.Driver";
	private static final String dbUrl = "jdbc:mysql://localhost/accession2?useUnicode=true&amp;connectionCollation=utf8_general_ci&amp;characterSetResults=utf8&amp;characterEncoding=utf8";
	
	private static final String dbUser = "root";
	private static final String dbPassword = "mysql";
	
	public static Connection getConnection() {
		if (dbConnection != null)
			return dbConnection;
		else {
			try 
			{
				Class.forName(dbDriver);
			}
			catch (ClassNotFoundException e) 
			{
				System.out
						.println("Add MySQL JDBC Driver in classpath ");
				e.printStackTrace();
			}

			try 
			{
			dbConnection = DriverManager.getConnection(dbUrl, dbUser,dbPassword);
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

			if (dbConnection == null) 
			{
				System.out.println("Failed to make connection!");
			}
		}
		return dbConnection;
	}
}