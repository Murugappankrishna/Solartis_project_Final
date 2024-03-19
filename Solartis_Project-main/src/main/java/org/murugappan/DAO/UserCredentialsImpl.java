package org.murugappan.DAO;

import org.murugappan.repo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Implements UserCredentials
public class UserCredentialsImpl implements UserCredentials {
    JDBC jdbc = new JDBC();
   

// Function To Create User
    @Override
    public void createUserCredentials(String name, String password, String roll) {
        PreparedStatement insertStatement;
        Connection connection = jdbc.establishConnection();
        try {

            insertStatement = connection.prepareStatement("INSERT INTO user_Credentials (username, password, role) VALUES (?,?,?)");
            insertStatement.setString(1, name);
            insertStatement.setString(2, password);
            insertStatement.setString(3, roll);
            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted >= 1) {
                System.out.println("User Created Successfully1");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }

    }
// Function To Fetch User Role
    @Override
    public String fetchRole(String name, String password) {
        PreparedStatement selectStatement = null;
        Connection connection = jdbc.establishConnection();
        try {

            selectStatement = connection.prepareStatement("select role from user_Credentials where username= ? AND password = ?");

            selectStatement.setString(1, name);
            selectStatement.setString(1, name);
            selectStatement.setString(2, password);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            } else {
                System.out.println("No matching user found or incorrect credentials Enter A Valid UserName And Password.");
                return ""; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
        finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }


    }

}