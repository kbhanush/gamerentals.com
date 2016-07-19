/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import cart.cart;
import database.DBCSConnection;
import database.DBCSConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jcraft.jsch.JSchException;

import user.user;

/**
 *
 * @author chirag
 */
@WebServlet(name = "buyItems", urlPatterns = {"/buyItems"})
public class buyItems extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("Buy Servlet Hit");
        doPost(request,response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        doPost(request, response);

    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        //processRequest(request, response);

        // THIS IS BUY SERVLET DOING THE FOLLOWING TASKS
        /*
         1. CREATE AN ORDER FOR THE CURRENT USER LOGGED IN
         * GOT ALL THE INFORMATION ABOUT THE USER TO FINALLY FILL IN TH INDFORMATION
         * 2. FIRE ON ORDER sql QUERY AND GET LATEST ORDER ID OF THE CURRENT USER
         * 3. CREATE SALES TABLE ENTRIES AND FILL IN THE TABLE WITH SAME ORDER ID
         * 4  FIRE SALES sql QUERY AND LIST THE APPROVAL AS PENDING
         * 5  DECREMENT THE QUANTITY in the CART FROM products TABLE 
                    
         *  DONE REDIRECT BACK TO USER INFO
         */


        System.out.println("Post callback hit");
        // GETTING ALL THE VALUES FROM THE USER
        String name, age, address, mobile;
        int order_id;
        Connection c = null;
        HttpSession session = request.getSession();
        user User;
        User = (user) session.getAttribute("user");
        cart Cart;
        Cart = (cart) session.getAttribute("cart");
        
        name = request.getParameter("name");
        age = request.getParameter("age");
        address = request.getParameter("address");
        mobile = request.getParameter("mobile");
        
        
        if (name.trim().length() > 1
                && address.trim().length() >= 5
                && mobile.trim().length() >= 5
                && mobile.trim().length() <= 12) {
            
            if (!(session.getAttribute("user") == null)
                    && !(session.getAttribute("cart") == null)) {
                
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    
                    c = DBCSConnectionManager.getConnection().getConnection();
                    
                    //******* Starting a Transaction
                    c.setAutoCommit(false);
                    String insertOrder;
                    insertOrder = "    INSERT INTO  order_details "
                            + "    VALUES ("
                            + "    SYS_GUID() ,  ?,  'paid',  ?,  ?,  ?, ?, CURRENT_DATE ,  ?"
                            + "    )";
                    
                    PreparedStatement preparedSQL1 =
                            c.prepareStatement(insertOrder);
                    
                    preparedSQL1.setString(1, User.getUserId()); // user iD

                    preparedSQL1.setString(2, name); //`shippers_name` 

                    preparedSQL1.setString(3, address); //`address` 

                    preparedSQL1.setString(4, mobile); //`mobile` 

                    preparedSQL1.setString(5, User.getUserEmail()); //`shippers_email` 

                    preparedSQL1.setDouble(6, Cart.getTotalPriceOfCart()); //`total_order_price`` 

                    int res = preparedSQL1.executeUpdate();
                    System.out.println("Order update:"+res);
                    if( res == 1){
                    	request.getRequestDispatcher("gameDownload.jsp").forward(request, response);
                    }
                    
                } catch (SQLException ex) {
                            ex.printStackTrace();
                            response.sendError(500);
               
                } catch (ClassNotFoundException ex) {
                    out.println("you user " + ex);
                    //response.sendRedirect("/saikiranBookstoreApp/buyItems.jsp");
                    
                } catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                session.removeAttribute("cart");
            } else {
                //response.sendRedirect("/saikiranBookstoreApp/index.jsp");
                out.println ("No items in cart");
            }
            
        } else {
            //response.sendRedirect("/saikiranBookstoreApp/buyItems.jsp");
            out.println ("NOt validated");
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
