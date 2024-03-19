package org.murugappan.DAO;

import org.murugappan.App;
import org.murugappan.repo.JDBC;
import org.murugappan.service.WorkFlowService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
//Implements CartDAO Interface
public class CartImpl implements CartDAO {
    JDBC jdbc = new JDBC();
    


    double totalPriceInclusiveOfTax = 0.0;
    //Function To Add Products To Cart
    @Override
    public void addToCart(Integer userId, Integer productId, Integer quantity) {
    	App main=new App();
    	  Connection connection = jdbc.establishConnection();
        try {

            PreparedStatement insertStatement;
            insertStatement = connection.prepareStatement("INSERT INTO cart (user_id, product_id, quantity, total_amount) VALUES (?, ?, ?, (SELECT selling_price * ? FROM product_details WHERE product_id = ?))");
            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, productId);
            insertStatement.setInt(3, quantity);
            insertStatement.setInt(4, quantity);
            insertStatement.setInt(5, productId);
           insertStatement.executeUpdate();
        } 
        catch(SQLIntegrityConstraintViolationException e) {
        	System.out.println("Enter a Valid User ID and Product ID So Exiting To Home Screen");
        	main.displayHomeScreen();
        	
        	
        }
        catch (SQLException e) {
        	  e.printStackTrace();
        }
        finally {
        	try {
				connection.close();
			} catch (SQLException e ) {
				
				e.printStackTrace();
			}
        }

    }
    
    //Function To Show The Products In The Cart
    @Override
    public void showCart(Integer userId) {
    	  Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("SELECT" + "  u.username, " + "  pd.product_name," + "  c.quantity," + "  pd.selling_price AS unit_price," + "  pd.Tax_Percent AS tax_percent," + "  (pd.selling_price * c.quantity) AS total_price_before_tax," + "  (" + "    (pd.selling_price * c.quantity)* pd.Tax_Percent / 100" + "  ) AS tax_amount, " + "  (" + "    (pd.selling_price * c.quantity) + (" + "      pd.selling_price * c.quantity * pd.Tax_Percent / 100" + "    )" + "  ) AS price_inclusive_of_tax " + "FROM " + "  cart c " + "  JOIN users u ON c.user_id = u.user_id " + "  JOIN product_details pd ON c.product_id = pd.product_id " + "WHERE " + "  u.user_id = ?");

            selectQuery.setInt(1, userId);
            ResultSet rs = selectQuery.executeQuery();
            System.out.printf("%-15s%-15s%-10s%-15s%-15s%-25s%-15s%-10s\n", "Username", "Product Name", "Quantity", "unit_price", "Tax Percent", "Total Price Before Tax", "Tax Value", "Price Inclusive of Tax");

            while (rs.next()) {
                String userName = rs.getString("username");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double priceBeforeTax = rs.getDouble("unit_price");
                int taxPercent = rs.getInt("tax_percent");
                double totalPriceBeforeTax = rs.getDouble("total_price_before_tax");
                double taxAmount = rs.getDouble("tax_amount");
                double priceInclusiveOfTax = rs.getDouble("price_inclusive_of_tax");


                totalPriceInclusiveOfTax += priceInclusiveOfTax;

                System.out.printf("%-15s%-15s%-10d%-20.2f%-10d%-25.2f%-15.2f%-10.2f\n", userName, productName, quantity, priceBeforeTax, taxPercent, totalPriceBeforeTax, taxAmount, priceInclusiveOfTax);


            }
            System.out.println("Your Total Cart Value is " + totalPriceInclusiveOfTax);


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
  //Function To Generate Bill
    @Override

    public ResultSet generateBillPDF(int userId) {
        ResultSet rs = null;
        Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("SELECT" + "  u.username, " + "  pd.product_name," + "  c.quantity," + "  pd.selling_price AS unit_price," + "  pd.Tax_Percent AS tax_percent," + "  (pd.selling_price * c.quantity) AS total_price_before_tax," + "  (" + "    (pd.selling_price * c.quantity)* pd.Tax_Percent / 100" + "  ) AS tax_amount, " + "  (" + "    (pd.selling_price * c.quantity) + (" + "      pd.selling_price * c.quantity * pd.Tax_Percent / 100" + "    )" + "  ) AS price_inclusive_of_tax " + "FROM " + "  cart c " + "  JOIN users u ON c.user_id = u.user_id " + "  JOIN product_details pd ON c.product_id = pd.product_id " + "WHERE " + "  u.user_id = ?");

            selectQuery.setInt(1, userId);
            rs = selectQuery.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }
        
    }
 // Function To Delete Cart
    @Override
    public void deleteCart(int userId) {
    	  Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("SELECT cart_id FROM cart WHERE user_id = ?");
            PreparedStatement deteteQuery = connection.prepareStatement("DELETE FROM cart WHERE cart_id = ?");
            selectQuery.setInt(1, userId);
            ResultSet rs = selectQuery.executeQuery();
            while (rs.next()) {
                int cartId = rs.getInt("cart_id");
                deteteQuery.setInt(1, cartId);
                int rowsAffected = deteteQuery.executeUpdate();

            }
        } catch (SQLException e) {

        }
        finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	}
    }
  //Function To Retrieve USerName
    @Override
    public String getUserName(Integer userId) {
    	  Connection connection = jdbc.establishConnection();
        String username = null;
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select username from users where user_id=?");
            selectQuery.setInt(1, userId);
            ResultSet rs = selectQuery.executeQuery();
            while (rs.next()) {
                username = rs.getString(1);

            }
        } catch (SQLException e) {

        }
        finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
        return username;
    }
    //Function To Update Product Quantity After Billing
    @Override
    public void updateProductQuantity() {
    	  Connection connection = jdbc.establishConnection();
        try {

            PreparedStatement selectQuery = connection.prepareStatement("SELECT product_id FROM cart");
            PreparedStatement updateQuery = connection.prepareStatement("UPDATE product_details SET stock = stock - ? WHERE product_id = ?");
            PreparedStatement selectQuery1 = connection.prepareStatement("SELECT quantity FROM cart WHERE product_id = ?");

            ResultSet rs = selectQuery.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt(1);
                selectQuery1.setInt(1, productId);
                ResultSet rs1 = selectQuery1.executeQuery();

                if (rs1.next()) {
                    int quantity = rs1.getInt(1);
                    updateQuery.setInt(1, quantity);
                    updateQuery.setInt(2, productId);
                    updateQuery.executeUpdate();
                }
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
    //Function To Check Product Quantity Before Adding To The Cart
    @Override
    public int checkProductQuantity(Integer quantity, Integer productID) {
    	  Connection connection = jdbc.establishConnection();
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select Stock from product_details where product_id=?");
            selectQuery.setInt(1, productID);
            ResultSet rs = selectQuery.executeQuery();
            rs.next();
            int quantityResult = rs.getInt(1);
            return quantityResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	}
    }


}