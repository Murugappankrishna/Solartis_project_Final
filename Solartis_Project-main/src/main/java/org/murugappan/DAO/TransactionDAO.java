package org.murugappan.DAO;
import java.math.BigDecimal;
//Interface For TransactionDAO  For Abstraction 
public interface  TransactionDAO {
	void insertData( String modePayment);
	BigDecimal fetchProfitByDate(String date);
	int fetchTaxByMonth(int month,int year);
	void fetchItemBought();
	void fetchTaxForEachProduct();
	void fetchProductsOnPaymentMode(String modeOfPay);
	
}