package org.murugappan.DAO;

import java.sql.SQLException;
//Interface For UserCredentials   For Abstraction 
public interface UserCredentials {
    void createUserCredentials(String name, String password, String roll) throws SQLException;
    String fetchRole(String name, String password) throws SQLException;
}