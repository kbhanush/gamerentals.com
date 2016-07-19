/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package product;
import database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.jcraft.jsch.JSchException;

/**
 *
 * @author chirag
 */
public class product {

    private int productId; 
    private ArrayList<String> category = new ArrayList<String>();
    private ArrayList<String> subcategory = new ArrayList<String>();;
    private ArrayList<String> company = new ArrayList<String>();;
    private Connection con;
    
    
    public int getId (String productName) throws SQLException{
        int id;
        String getIdSQL = "SELECT  product_id " +
                        "FROM  products " +
                        "WHERE  product_name = '"+productName+"'";
        
        Statement st = con.createStatement();
        ResultSet executeQuery = st.executeQuery(getIdSQL);
        executeQuery.next();
        id = executeQuery.getInt("product_id");
        return id;
    }
    public ArrayList<String> getCategory() throws SQLException, ClassNotFoundException, JSchException {
        DBCSConnection c =DBCSConnectionManager.getConnection();
        con = c.getConnection();
        String getCategorySQL = "SELECT  category_name FROM  category ";
        PreparedStatement psmt = con.prepareStatement(getCategorySQL);
        ResultSet executeQuery = psmt.executeQuery();
        while (executeQuery.next()){
            String category1 = executeQuery.getString ("category_name");
            this.category.add(category1);
        }
        return category;
    }

    public ArrayList<String> getSubcategory() throws SQLException, ClassNotFoundException, JSchException {
        DBCSConnection c = DBCSConnectionManager.getConnection();
        con = c.getConnection();
        String getCategorySQL = "SELECT sub_category_name " +
                                "FROM  sub_category";
        PreparedStatement psmt = con.prepareStatement(getCategorySQL);
        ResultSet executeQuery = psmt.executeQuery();
        while (executeQuery.next()){
            String subcategory1 = executeQuery.getString ("sub_category_name");
            this.subcategory.add(subcategory1);
        }
        return subcategory;
    }
    
    public ArrayList<String> getSubcategory(String category) throws SQLException, ClassNotFoundException, JSchException {
        DBCSConnection c = DBCSConnectionManager.getConnection();
        con = c.getConnection();
        String getCategorySQL = "SELECT  sub_category_name " +
                                "FROM  sub_category " +
                                "WHERE  category_name =  ?";
        
        PreparedStatement psmt = con.prepareStatement(getCategorySQL);
        psmt.setString(1, category);
        ResultSet executeQuery = psmt.executeQuery();
        while (executeQuery.next()){
            String subcategory1 = executeQuery.getString ("sub_category_name");
            this.subcategory.add(subcategory1);
        }
        return subcategory;
    }

    public ArrayList<String> getCompany(String category) throws SQLException, ClassNotFoundException, JSchException {

        DBCSConnection c = DBCSConnectionManager.getConnection();
        con = c.getConnection();
        String getCategorySQL = "SELECT  company_name " +
                                "FROM  products " +
                                "GROUP BY  company_name ";
        
        PreparedStatement psmt = con.prepareStatement(getCategorySQL);
        psmt.setString(1, category);
        ResultSet executeQuery = psmt.executeQuery();
        while (executeQuery.next()){
            String company_name = executeQuery.getString ("company_name");
            this.company.add(company_name);
        }
        return company;
    }
    
    public ArrayList<String> getCompany() throws SQLException, ClassNotFoundException, JSchException {

        DBCSConnection c =DBCSConnectionManager.getConnection();
        con = c.getConnection();
        String getCategorySQL = "SELECT  company_name " +
                                "FROM  products " +
                                "GROUP BY  company_name ";
        
        PreparedStatement psmt = con.prepareStatement(getCategorySQL);
        
        ResultSet executeQuery = psmt.executeQuery();
        while (executeQuery.next()){
            String company_name = executeQuery.getString ("company_name");
            this.company.add(company_name);
        }
        return company;
    }
    
    public static void main (String args []) throws SQLException, ClassNotFoundException{
        product p = new product();
        try{
	        ArrayList<String> company1 = p.getCompany();
	        ArrayList<String> category1 = p.getCategory();
	        ArrayList<String> subcategory1 = p.getSubcategory("Computers");
        
    
        for (int i=0;i<company1.size() ; i++){
            System.out.println(" "+company1.get(i));
        }
        
        
        System.out.println("");
        
        for (int i=0;i<subcategory1.size() ; i++){
            System.out.println(" "+subcategory1.get(i));
        } 
        
        Scanner sc = new Scanner(System.in);
        String next = sc.next();
        p.getId(next);
        
            System.out.println(" "+
                    p.getId(next));
        }catch(JSchException e){
        	e.printStackTrace();
        }
    }
    
}
