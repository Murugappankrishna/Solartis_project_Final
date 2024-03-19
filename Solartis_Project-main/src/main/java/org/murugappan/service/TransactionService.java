package org.murugappan.service;
import org.murugappan.DAO.TransactionImpl;
import org.murugappan.DAO.TransactionDAO;

import java.math.BigDecimal;
import java.util.*;
// Service Class For Transaction Service 
public class TransactionService {
	// Object declaration
Scanner ip=new Scanner(System.in);
	TransactionDAO transactionImpl=new TransactionImpl();
	Scanner input = new Scanner(System.in);
	//Service Method To Show Profit Made On A Particular Day
public void showProfitForADay() {
	
	System.out.println("Enter The Date To See The Profit Made On That Day In The Format Of YYYY-MM-DD");
	String date= input.nextLine();
	BigDecimal profit=transactionImpl.fetchProfitByDate(date);//Method Call To DAO to Show Profit 
	if(profit!=null) {
	System.out.println(profit);
	}
	else {
		System.out.println("Eneter A Valid Date");
	}
}
// Service Method To Show Tax Paid For A Month
public void  showTaxForMonth(){
	int month;
	int year;
	System.out.println("Enter The Month In The Format Of MM To See The Tax Paid For The Month");
	month= input.nextInt();
	System.out.println("Enter The Year In The Format Of YYYY");
	year=input.nextInt();
	Integer tax=transactionImpl.fetchTaxByMonth(month,year);// Method Call To DAO To Show Tax
	if(tax.equals(null)) {
		System.out.println("Eneter A Valid Date");
	}
	else {
		
		System.out.println("The Total Pax For The Month is "+tax);
	}
	
}
// Service Method To Show Items Bought
public void showItemBought() {
	System.out.println("Here the List Of Products Bought Over The Period Of Time");
	transactionImpl.fetchItemBought();//Method Call To  DAO To Show Item Bought
	
}
// Service Method To Show Tax For Each Product
public void TaxForEachProduct() {
	System.out.println("Here is The List of Products Sold Along With The Tax");
	transactionImpl.fetchTaxForEachProduct();//Method Call To DAO To Show Tax For Each Product
}
// Service Method To Show Products And Their Mode Of Payment
public void productsOnPaymentMode() {
	System.out.println("Enter The Mode Of Payment");
	String modeOfPay=ip.next();
	System.out.println("Here Is The Products Based On The Mode Of Pay");
	transactionImpl.fetchProductsOnPaymentMode(modeOfPay);// Method Call To DAO To Show Products And Their Respective Mode Of Payment
}
}