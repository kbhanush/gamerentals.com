<%-- 
    Document   : topMostViewedProducts
    Created on : 13 Dec, 2012, 6:45:53 PM
    Author     : chirag
--%>

        <%@page import="java.sql.ResultSet"%>
        <%@page import="java.sql.Statement"%>
        <%@page import="database.DBCSConnection"%>
        <%@page import="java.sql.Connection"%>
        <%@page import="java.util.ArrayList"%>
<%@ page import="database.DBCSConnectionManager" %>

<style type="text/css">
            .prodGrid {
                margin: 10px;
                margin-right: -12px;
                margin-left: 36px;
            }
        </style>
<div class="grid_13" id="productStrip"> 
                       <a href="viewProducts_.jsp">
                           <div class="ProductHeading">
                               <div class="grid_12">
                                   <h2 class="heading">Top Most Viewed Products Currently</h2>
                               </div>
                               <!--<div id="viewMore" class="grid_3">
                                   <h6 class="blue">View More</h6>
                               </div>-->
                           </div>
                       </a>
                       <div class="clear"></div>
<%
 String sqlTopMostProds = "SELECT * "
+" FROM  products p"
+" LEFT JOIN  images i"
+" USING (  product_name ) "
 + "WHERE product_qty > 5 and i.isThumbnail = 1 and ROWNUM < 5"
+" ORDER BY  hits DESC ";
 
 
 ArrayList<String> topMost = new ArrayList<String>();
 topMost.clear();
 Connection c = DBCSConnectionManager.getConnection().getConnection();
 Statement st = c.createStatement();
 ResultSet rs = st.executeQuery(sqlTopMostProds);
 String name, productId, category, subCategory, company, imageName, price;
 int i= 1;
 
 while (rs.next()){ 
     
      price = rs.getString("price");
      imageName = rs.getString("image_name");
      productId = rs.getString("product_id");
      name = rs.getString("product_name");
      subCategory = rs.getString("sub_category_name");
      category = rs.getString("category_name");
      company = rs.getString("company_name");
      %>
                <div id="productList" class="grid_3 prodGrid"> 
                    <a href="product.jsp?id=<%= productId %>"><img src="<%= imageName %>" /></a>
                    <p id="info">
                        <a href="product.jsp?id=<%= productId %>"><span class="blue"><%= name %></span></a><br/>
                        By <%= company %> <%= category %><br/>
                        <span class="red">$  <%= price %></span>
                    </p>            
                </div>
                       <%
    if (i%4 == 0){
        %>
                <div class="clear"></div>
                       <%
    }
      i++;
 }
 
 if (i == 0){
%>
                <div class="clear"></div><div id="productList" class="grid_3 prodGrid"> 
                    <a href="product.jsp"><img src="images/textures/linen.png" /></a>
                    <p id="info">
                        <a href="product.jsp?id="><span class="blue">No Product Added in Database</span></a><br/>
                        <br/>
                        <span class="red">$  0</span>
                    </p>            
                </div>
                       <%
}
%>
            </div>