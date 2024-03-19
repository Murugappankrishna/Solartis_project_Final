package org.murugappan.DAO;

import org.murugappan.repo.JDBC;

import java.sql.*;
//Implements ProductsDAO Interface
public class ProductsImpl implements ProductsDAO {
    JDBC jdbc = new JDBC();
  
// Function To Add Products To The Inventory
    @Override
    public void addProducts(String productName, String description, int costPrice, int sellingPrice, int stock, int tax) {
        PreparedStatement insertStatement;
        Connection connection = jdbc.establishConnection();
        try {
            insertStatement = connection.prepareStatement("INSERT INTO product_details (product_name, product_des, cost_price, selling_price, Stock,Tax_Percent) VALUES(?,?,?,?,?,?)"
            );
            insertStatement.setString(1, productName);
            insertStatement.setString(2, description);
            insertStatement.setInt(3, costPrice);
            insertStatement.setInt(4, sellingPrice);
            insertStatement.setInt(5, stock);
            insertStatement.setInt(6, tax);
            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted >= 1) {
                System.out.println("Product Added Successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }

        }
    }
// Function To Delete Product From Inventory
    @Override
    public void deleteProduct(int productId) {
    	  Connection connection = jdbc.establishConnection();
        PreparedStatement deleteStatement;
        try {
            deleteStatement = connection.prepareStatement("UPDATE product_details SET activeflag = 'N' WHERE product_id = ?;");
            deleteStatement.setInt(1, productId);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Product Deleted Successfully!");
            }
            else {
            	System.out.println("Enter A valid Product ID");
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
//Function To Show Products In Inventory
    @Override
    public void showProducts() {
    	  Connection connection = jdbc.establishConnection();
        Statement selectStatement;
        try {
            selectStatement = connection.createStatement();
            ResultSet rs = selectStatement.executeQuery("SELECT * FROM product_details WHERE activeflag = 'Y'");
            System.out.printf("%-15s%-20s%-20s%-15s%-15s%-10s%-10s%n",
                    "Product ID", "Product Name", "Product Description",
                    "Cost Price", "Selling Price", "Stock", "Tax Percent");

            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String productDes = rs.getString("product_des");
                int costPrice = rs.getInt("cost_price");
                int sellingPrice = rs.getInt("selling_price");
                int stock = rs.getInt("Stock");
                int tax = rs.getInt("Tax_Percent");


                System.out.printf("%-15d%-20s%-25s%-15d%-15d%-10d%-10d%n",
                        productId, productName, productDes, costPrice, sellingPrice, stock, tax);

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
//Function To Edit A Product
    @Override
    public void editProduct(String columnToUpdate, int productId, String newValue) {
    	  Connection connection = jdbc.establishConnection();
        PreparedStatement updateStatement;

        try {
            updateStatement = connection.prepareStatement("UPDATE product_details SET " + columnToUpdate + " = ? WHERE product_id = ? AND activeflag = 'Y'");

            updateStatement.setString(1, newValue);
            updateStatement.setInt(2, productId);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Product Edited Successfully!");
            }
            else {
            	System.out.println("Make Sure The Product Is Active And Its A Valid Product Id");
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

    @Override
    // Function To Retrive Low Stock Items Send It  To Mailing Method
    public String showLowStockItems() {
    	  Connection connection = jdbc.establishConnection();
        String listProducts = "";
        Statement selectStatement;
        try {
            selectStatement = connection.createStatement();
            ResultSet rs = selectStatement.executeQuery("select product_name from product_details where Stock<20 AND activeflag = 'Y'");
            while (rs.next()) {
                listProducts += (rs.getString("product_name")) + "</br>";

            }

            return listProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            return listProducts;
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