package org.murugappan.service;

import java.util.Scanner;

import org.murugappan.App;


//  Service Class For WorkFlow
public class WorkFlowService {
	//Object Declaration
	UserDetailsService userdetailsService= new UserDetailsService();
    ProductsService productsService=new ProductsService();
    CartService cartService=new CartService();
    TransactionService transactionService=new TransactionService(); 
    Scanner input =new Scanner(System.in);
    public String userFlag;
    App main = new App();
    //Method To Execute Login Services 
    public void login() {
    
    		System.out.println("Enter 1 To Register As A New User \nEnter 2 To Login As Existing User");
    		int isNewUserOrExistingUser = input.nextInt();
    		 if (isNewUserOrExistingUser == 1) {
    			 userdetailsService.createUser();//Method Call TO Create USer
    			 login();
    			 }
    		 else if (isNewUserOrExistingUser == 2) {   	         
    				 userFlag= (userdetailsService.fetchRole().toUpperCase());//Method Call To Fetch Role
    				 System.out.println(userFlag);
    			 }
    		 else
    			 System.out.println("Eneter A Valid Input Exiting...");
		//login();
    	        } 
    	
    // Method To Execute Product Services
     public void manageProduct() {
    	   if(userFlag.equals("MANAGER")) {
    		   int managerFunction;
    		   System.out.println("Welcome Manager!\nHave A Nice Day");
    		   System.out.println("Enter 1 To See All Products In The Inventory\nEnetr 2 To Add A Product\nEnter 3 To Edit A Product\nEnter 4 To Remove A Productt\nEnter 5 To Exit");
    		   managerFunction= input.nextInt();
    		   if(managerFunction==1) 
            	  productsService.showProducts();//Method Call To Show Products
            	else if(managerFunction==2)
            		productsService.addProduct();// Method Call To Add Products
            	else if(managerFunction==3)
            		productsService.editProducts(); // Method Call To Edit Products      		
            	else if(managerFunction==4)
            		productsService.deleteProduct(); // Method Call To Delete Products
            	else if(managerFunction==5)
            		main.displayHomeScreen();
            		
            	else {
            		System.out.println("Enter a Valid Choise Returning To Home Screen");
            		manageProduct();}
            	
    	   }
    	   else if(userFlag.equals("SALESPERSON")) {
    		   System.out.println("Welcome SalesPerson!\nHave A Nice Day");
    		   System.out.println("Enetr 1 To Add A Product\nEnter 2 To Edit A Product\nEnter 3 To Remove A Product\nEnter 4 To Exit");
    		   int salespersonFunction;
    		   salespersonFunction= input.nextInt();
    		   if(salespersonFunction==1)
    			   productsService.addProduct();//Method Call To Add Products
    		   else if(salespersonFunction==2)
    			   productsService.editProducts(); // Method Call To Edit Products  
    		   else if(salespersonFunction==3)
    			   productsService.deleteProduct(); // Method Call To Delete Products
    		   else if(salespersonFunction==4)
    			   main.displayHomeScreen();
    			  
    		   else {
           		System.out.println("Enter a Valid Choise Returning To Home Screen");
           		manageProduct();
           	}
    			   
    		   
    	   }
     }
     //Method To Execute Transaction Services 
   public  void manageTransaction() {
    	if(userFlag.equals("MANAGER")) {
    		System.out.println("Welcome Manager\nHave A Nice Day");
    		System.out.println("Enter 1 To Show Profit For A Particular Date\nEnter 2 Get The Tax Paid For a Particular Month\nEnter 3 See The Items Sold\nEnter 4 To See The Products Based On Mode OF Payment\nEnter 5 To See Tax Paid For Each Product\nEnter 6 to exit");
    		int managerFunction= input.nextInt();
    		if(managerFunction==1)
    			transactionService.showProfitForADay();//Method Call To ShowProfit-Product
 		   else if(managerFunction==2)
 			  transactionService.showTaxForMonth();//Method Call To ShowTax-Product
 		   else if(managerFunction==3)
 			  transactionService.showItemBought(); //Method Call To ShowItems Bought
 		   else if(managerFunction==4)
 			  transactionService.productsOnPaymentMode(); //Method Call To Show ModeOfPayment-Product
 		   else if(managerFunction==5)
 			 transactionService.TaxForEachProduct(); //Method Call To Tax-Product
 		  else if(managerFunction==6) {
 			 main.displayHomeScreen();
		   }
 		  else {
      		System.out.println("Enter a Valid Choise Returning To Home Screen");
      		manageTransaction();
      	}
    		
    	}
    	else if(userFlag.equals("SALESPERSON")){
    		System.out.println("Welcome SalesPerson!\nHave A Nice Day");
    		System.out.println("Enter 1 See The Items Sold\nEnter 2 To See The Products Based On Mode OF Payment\nEnter 3 To See Tax Paid For Each Product\nEnter 4 to exit");
    		 int salespersonFunction= input.nextInt();
    		   if(salespersonFunction==1)
    			   transactionService.showItemBought();//Method Call To ShowItems Bought
    		   else if(salespersonFunction==2)
    			   transactionService.productsOnPaymentMode();//Method Call To Show ModeOfPayment-Product
    		   else if(salespersonFunction==3)
    			   transactionService.TaxForEachProduct();	//Method Call To Tax-Product   
    		   else if(salespersonFunction==4) {
    			   main.displayHomeScreen();
    		   }
    		   else {
    	      		System.out.println("Enter a Valid Choise Returning To Home Screen");
    	      		manageTransaction();
    	      	}
    		  
    	}
    }
   //Method To Execute Cart Services
  
     public void addToCart() {
    	 cartService.addToCart();//Method Call To Add Products To Cart
     }
}


