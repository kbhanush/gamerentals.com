/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import database.DBCSConnection;
import java.sql.*;
import java.util.Scanner;

import database.DBCSConnectionManager;

/**
 *
 * @author chirag
 */
public class user {
    public String userEmail = null;
    public String userId = null;
    public String username;
    public String address;
    public String gender;
    public String userImage;
    public String mobileNum;
    
    Connection c;
    
    public void setUserEmail (String userEmail) throws SQLException, ClassNotFoundException{
        try{
        	this.userId = findUserId(userEmail);
        	boolean fetchAllValues = fetchAllValues(getUserId());
        }catch (Exception e){
        	e.printStackTrace();
        }
        this.userEmail = userEmail;        
    }
    public String getUserEmail (){
        return userEmail;
    }
    public String getUserId (){
        return userId;
    }
    public String getUsername (){
        return username;
    }
    public String getAddress (){
        return address;
    }
    public String getGender (){
        return gender;
    }
    public String getMobileNum (){
        return mobileNum;
    }
    public String getUserImage (){
        return userImage;
    }
    
    public String findUserId (String email) throws SQLException, ClassNotFoundException{
        String sqlGetUserId = "SELECT  user_id FROM  user_auth WHERE  email =  ?";
        c= DBCSConnectionManager.getConnection().getConnection();
        PreparedStatement psmt  = c.prepareStatement(sqlGetUserId);
        psmt.setString(1, email);
        ResultSet executeQuery = psmt.executeQuery();
        executeQuery.next();
        userId = executeQuery.getString("user_id");
        return userId;
    }
    
    public boolean fetchAllValues (String string) throws SQLException, ClassNotFoundException{
        // GETS ALL THE VALUES FROM THE TABLE user-deails
        
        String fetchSql;
        boolean fetched;
        fetchSql = "SELECT * FROM  user_details WHERE  user_id =? ";
        c = DBCSConnectionManager.getConnection() .getConnection();
        
        PreparedStatement psmt = c.prepareStatement(fetchSql);
        psmt.setString(1, string);
        
        ResultSet executeQuery = psmt.executeQuery();
        boolean next = executeQuery.next();
        if (next){
            username = executeQuery.getString("username"); 
            address = executeQuery.getString("address");
            gender = executeQuery.getString("gender");
            userImage = executeQuery.getString("userImage");
            mobileNum = executeQuery.getString("mobile_no");
            fetched = true;
        }else {
            username = null;
            address = null;
            gender = null;
            userImage = null;
            mobileNum = null;
            fetched = false;
        }
       
        return fetched;
    }
    
    public static void main (String args[]) throws SQLException, ClassNotFoundException{
        System.out.println("Ok then gimme an email to give u an ID");
        Scanner sc = new Scanner (System.in);
        String next = sc.next();
        user user = new user ();
        user.setUserEmail(next);
        user.getUsername();
        System.out.println("Dude u have a email of an id "+
        user.getUsername()+" Address of "+user.getAddress());
    }

    @Override
    public String toString() {
        return "user{" +
                "userEmail='" + userEmail + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", userImage='" + userImage + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                '}';
    }
}
