package org.murugappan.service;

import org.murugappan.DAO.UserCredentialsImpl;
import org.murugappan.model.UserCredentials;

import java.util.Scanner;
// Service Class For UserDetails
public class UserDetailsService {
	//Object Declaration
    UserCredentials credentials = new UserCredentials();
    HashService hashService = new HashService(credentials);
    UserCredentialsImpl userCredentialsImplementation = new UserCredentialsImpl();
    Scanner input = new Scanner(System.in);
// Service Method To Fetch Role
    public String fetchRole() {
        String userRoll;
        System.out.println("Enter Your Name");
        String name = input.nextLine();
        System.out.println("Enter Your Password");
        input.nextLine();
        System.out.println("ReEnter Your Password");
        String password = input.nextLine();
        System.out.println("Validating....");
        System.out.println("------------------");
        System.out.println("------------------");
        UserCredentialsImpl userCredentialImplementation = new UserCredentialsImpl();
        userRoll = userCredentialImplementation.fetchRole(name, Integer.toString(password.hashCode()));//Hashing The Password By Passing It To The HashingService And Checking With the DB 
        return userRoll;

    }
    // Service  Method To Create User

    public void createUser() {


        System.out.println("Enter Your Name");
        credentials.userCredentials.put("Name", input.nextLine());

        System.out.println("Enter Your Password");
        credentials.userCredentials.put("Password", input.nextLine());
        System.out.println("Enter Your Roll");
        credentials.userCredentials.put("Roll", input.nextLine().toUpperCase());
        hashService.hashPassword();// Hashing The Password

        userCredentialsImplementation.createUserCredentials(credentials.userCredentials.get("Name"), credentials.userCredentials.get("Password"), credentials.userCredentials.get("Roll"));//Method Call To Save The User Details
    }

}