package org.murugappan;

import org.murugappan.service.*;
import java.math.BigDecimal;


import java.util.Scanner;

// Main Class 
public class App
{
	//Object Declaration
	static WorkFlowService workFlowService=new WorkFlowService();
	Scanner ip=new Scanner(System.in);

	public void displayHomeScreen() {
		if(workFlowService.userFlag.equals("MANAGER")||workFlowService.userFlag.equals("SALESPERSON")) {
		while (true) {
			
	        System.out.println("Welcome to Home Screen");
	        System.out.println("Enter 1 To Manage Products\nEnter 2 To Manage Transaction\nEnter 3 To Add Products To Cart\nEnter 4 To Exit");
	        int choice = ip.nextInt();
	        switch (choice) {
	            case 1:
	                String flowproduct;
	                do {
	                    workFlowService.manageProduct();
	                    System.out.println("Do you want to continue managing products? (yes/no)");
	                    flowproduct = ip.next();
	                } while (flowproduct.equals("yes"));
	                break;
	            case 2:
	            	  String flowtransaction;
		                do {
		                    workFlowService.manageTransaction();
		                    System.out.println("Do you want to continue managing transaction? (yes/no)");
		                    flowtransaction = ip.next();
		                } while (flowtransaction.equals("yes"));
		                break;
	            case 3:{
	            	workFlowService.addToCart();
	            	displayHomeScreen();
;	            }
	            case 4:
	                System.out.println("Exiting the application.");
	                return; 
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	        continue; 

		}   
		}
	    }
	//Main Method 

	public static void main(String[] args) {
	    App main = new App();
	    workFlowService.login();
	    main.displayHomeScreen();
	}

}