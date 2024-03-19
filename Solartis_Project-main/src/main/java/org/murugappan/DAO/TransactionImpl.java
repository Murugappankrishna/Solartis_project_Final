package org.murugappan.DAO;

import org.murugappan.repo.JDBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Implements TransactionDAO
public class TransactionImpl implements TransactionDAO {
    JDBC jdbc = new JDBC();
   

    // Function To Put Data in TransactionTable
    @Override
    public void insertData(String modePayment) {
    	 Connection connection = jdbc.establishConnection();
        try {
            String Query = "INSERT INTO transactionDB (userID, username, productID, productName, quantity, costPrice, sellingPrice, profit, totalPriceExcludingTax, taxPercent, totalCostIncludingTax, taxCost, transactionDate, modeOfPayment) " + "SELECT " + "    c.user_id AS userID, " + "    u.username AS username, " + "    c.product_id AS productID, " + "    pd.product_name AS productName, " + "    c.quantity AS quantity, " + "    pd.cost_price AS costPrice, " + "    pd.selling_price AS sellingPrice, " + "    (pd.selling_price - pd.cost_price) * c.quantity AS profit, " + "    pd.selling_price * c.quantity AS totalPriceExcludingTax, " + "    pd.Tax_Percent  AS taxPercent, " + "    (pd.selling_price * c.quantity) + ((pd.selling_price * c.quantity) * (pd.Tax_Percent / 100)) AS totalCostIncludingTax, " + "    (pd.selling_price * c.quantity) * (pd.Tax_Percent  / 100) AS taxCost, " + "    CURDATE() AS transactionDate, " + "    ? AS modeOfPayment " + "FROM " + "    cart c " + "JOIN " + "    users u ON c.user_id = u.user_id " + "JOIN " + "    product_details pd ON c.product_id = pd.product_id;";


            PreparedStatement insertQuery = connection.prepareStatement(Query);
            insertQuery.setString(1, modePayment);
            int rowsAffected = insertQuery.executeUpdate();
            if (rowsAffected > 1) {
                System.out.println("Note For the Manager and Admin TransactionTable Updated For This Transactions");
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
// Function To Fetch Profit By Date
    @Override
    public BigDecimal fetchProfitByDate(String date) {
    	 Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select sum(profit) from transactionDB where transactionDate =?");
            selectQuery.setString(1, date);
            ResultSet rs = selectQuery.executeQuery();
            rs.next();
            return rs.getBigDecimal(1);


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
        return BigDecimal.valueOf(0.0);
    }
// Function To Fetch Tax By Month
    @Override
    public int fetchTaxByMonth(int month, int year) {
    	 Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("SELECT SUM(taxCost) AS totalTaxPaid FROM transactionDB WHERE MONTH(transactionDate) = ? AND YEAR(transactionDate) = ?");
            selectQuery.setInt(2, year);
          selectQuery.setInt(1, month);
            ResultSet rs = selectQuery.executeQuery();
            rs.next();
            return rs.getInt(1);

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
        return -1;
    }
// Function To Fetch Items Bought
	@Override
	public void fetchItemBought() {
		 Connection connection = jdbc.establishConnection();
		try {
		    PreparedStatement selectQuery = connection.prepareStatement("SELECT productName, quantity, transactionDate FROM transactionDB");
		    ResultSet resultSet = selectQuery.executeQuery();

		    System.out.printf("%-33s %-10s %-21s\n", "Product Name", "Quantity", "Transaction Date");
		    System.out.println("-----------------------------------------------------------------------");

		    while (resultSet.next()) {
		        String productName = resultSet.getString("productName");
		        int quantity = resultSet.getInt("quantity");
		        String transactionDate = resultSet.getString("transactionDate");

		        System.out.printf("%-33s %-10d %-21s\n", productName, quantity, transactionDate);
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
// Function To Fetch Tax For Each Product
public void fetchTaxForEachProduct() {
	 Connection connection = jdbc.establishConnection();
	try {
	    PreparedStatement selectQuery = connection.prepareStatement("SELECT productName,quantity, taxCost FROM transactionDB");
	    ResultSet resultSet = selectQuery.executeQuery();
	    System.out.printf("%-33s %-10s %-21s\n", "Product Name", "Quantity", "Tax Cost");
	
	    System.out.println("---------------------------------------------------------");

	    while (resultSet.next()) {
	        String productName = resultSet.getString("productName");
	        double taxCost = resultSet.getDouble("taxCost");
	        int quantity = resultSet.getInt("quantity");

	        
	        System.out.printf("%-33s %-10d %-21s\n", productName, quantity, taxCost);
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
// Function To Fetch Products Based On Mode Of Payment
public void fetchProductsOnPaymentMode(String modeOfPay) {
	 Connection connection = jdbc.establishConnection();
	try {
        modeOfPay = modeOfPay.toUpperCase(); 
        PreparedStatement selectQuery = connection.prepareStatement("SELECT productName, quantity, profit FROM transactionDB WHERE modeOfPayment=?");
        selectQuery.setString(1, modeOfPay);
        ResultSet resultSet = selectQuery.executeQuery();

        System.out.printf("%-30s %-10s %-10s\n", "Product Name", "Quantity", "Profit");
        System.out.println("-----------------------------------------");

        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            int quantity = resultSet.getInt("quantity");
            double profit = resultSet.getDouble("profit");

            System.out.printf("%-30s %-10d %-10.2f\n", productName, quantity, profit);
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

}