package org.murugappan.repo;
import java.sql.*;

public class JDBC {
    public Connection establishConnection()  {
    	
        String url = "jdbc:mysql://localhost:3306/shopBillingManagement";
        String username = "root";
        String password = "RDJ.krish1";
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
        	System.out.println("connection Failed");
            e.printStackTrace();
            return null; 
        }


    }

}
