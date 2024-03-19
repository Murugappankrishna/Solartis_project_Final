package org.murugappan.service;

import java.util.Scanner;

import org.murugappan.model.Cart;


import org.murugappan.DAO.*;
// Service Class For Cart Service 
public class CartService {
	// Object Creation 
	Cart cart= new Cart();
	
	CartDAO cartImplementation = new CartImpl();
	TransactionDAO transactionImplementation =new TransactionImpl();
	CreatePDF createPDF =new CreatePDF();
	MailService mailService=new MailService();
	ProductsService productsService=new ProductsService();
	// Service Function To Add Products To Cart
	public void addToCart() {
		Scanner input =new Scanner(System.in);
		int flag;
		String isBilling;
		String modePayment;
		System.out.println("Enter The Products To Be Added TO The Cart");	
		System.out.println("Enter The User ID ");
		cart.userCart.put("UserID", input.nextInt());// Data Injected To HashMap
		do {
		System.out.println("Here Is The List Of Products Available");
		productsService.showProducts();//Function Call To Show Products
		System.out.println("Enter THe Product ID To Be Added In The Cart");
		cart.userCart.put("ProductID", input.nextInt());// Data Injected To HashMap
		System.out.println("Enter The Quantify Needed");// Data Injected To HashMap
		cart.userCart.put("Quantity", input.nextInt());// Data Injected To HashMap
		int quantityResult= cartImplementation.checkProductQuantity(cart.userCart.get("Quantity"),cart.userCart.get("ProductID"));// Function Call To Check Product Quantity
		if(cart.userCart.get("Quantity")>quantityResult) {
			System.out.println("We Are Sorry..! We Are Running Out Of Stock");
		}//validation
		else {
		cartImplementation.addToCart(cart.userCart.get("UserID"),cart.userCart.get("ProductID"),cart.userCart.get("Quantity"));

		}
		System.out.print("Do Want More Products To Be added Enter 1 Else 2");
		flag= input.nextInt();
		}while(flag==1);
		System.out.println("Your Products Has Been Added");
		cartImplementation.showCart(cart.userCart.get("UserID"));//Function Call To Show Products In Cart
		System.out.println("DO Want To Proceed To Billing Section Enter YES else NO");
		input.next();
		System.out.println("Confirm Again");
		isBilling= input.next().toUpperCase();
		if(isBilling.equals("YES")) {
			System.out.println("Say Your Choice of Payment Mode");
			modePayment= input.next().toUpperCase();
			String userName= cartImplementation.getUserName(cart.userCart.get("UserID"));
			createPDF.createInvoice(cart.userCart.get("UserID"), modePayment,userName);// Function Call To Generate Bill
			cartImplementation.updateProductQuantity();// Function Call To Update Quantity In Inventory
			createPDF.oppenPDF();// Function Call To Open The Generated PDF
			mailService.sendMail();//Function Call To Send Mail To Notify Low Stock Products
			transactionImplementation.insertData(modePayment);//Function Call To update Transaction Table
			cartImplementation.deleteCart(cart.userCart.get("UserID"));// Function Call To Delete Cart Details 
			System.out.println("Bill Generated ");

		
		}
		
		
	}
	
	

}