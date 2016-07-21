
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>CI Demo Game Store</title>
        <jsp:useBean class="product.product" id="product" scope="session"></jsp:useBean>

        <%@page import="java.sql.*, database.*" %>
        <link rel="shortcut icon" href="images/logo/ico.ico"/>
        <link rel="stylesheet" type="text/css" href="css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="css/text.css"/>
        <link rel="stylesheet" type="text/css" href="css/960_16.css"/>
        <link rel="stylesheet" type="text/css" href="css/product.css"  />

        <link rel="stylesheet" type="text/css" href="css/lightbox.css"  />

        <link rel="stylesheet" type="text/css" href="css/styles.css"/>

        <script src="js/jquery-1.7.2.min.js"></script>
        <script src="js/lightbox.js"></script>
        <script src="js/myScript.js"></script>
    </head>
    <body>
        
        
        <%
        if (session.getAttribute("src/main/java/user") == null ){// THen new user, show join now
            %>
            <jsp:include page="includesPage/_joinNow.jsp"></jsp:include>
        <%
        }else {
            %>
            <jsp:include page="includesPage/_logout.jsp"></jsp:include>
        <%
        }
        %>



        <div class="container_16">
            <div id = "contents">
                
                <div id="leftside" class="grid_3">
                    
                    <%
                    String heading = "All products" ;
        			
                        String sql = "SELECT * FROM  products p "
                                        + "INNER JOIN  images i "
                                        + "USING (product_name) where i.isThumbnail = 1 ";
                   		String category=request.getParameter("cat");
                        if( category != null){
                        	System.out.println("User clicked category:"+ category);
                        	sql+=" and p.category_name = '"+category+"'";
                        	heading = category;
                        }
                        System.out.println(sql);
                      %>

                </div>
            </div>

            <!-- Middle -->
            <div id="middle"class="grid_13">
                <div class="grid_13" id="whiteBox">
                    <div class="ProductHeading">
                        <div class="grid_12">
                                <h2 class="heading">Products >
                                 
                                    <%=heading %>
                                </h2>
                        </div>
                        
                    </div>
                    <div class="grid_12 productListing">

                       <%

                        DBCSConnection con = DBCSConnectionManager.getConnection();
                        Connection c = con.getConnection();
                        Statement st = c.createStatement();
                        ResultSet rs = st.executeQuery(sql);

                        while (rs.next()) {
                             
	                        String product_id = rs.getString("product_id");
	
	                        String product_name = rs.getString("product_name");
	
	                        String sub_category_name = rs.getString("sub_category_name");
	
	                        String category_name = rs.getString("category_name");
	
	                        String company_name = rs.getString("company_name");
	
	                        String price = rs.getString("price");
	
	                        String summary = rs.getString("product_summary");
	                        
	                        String image_name = rs.getString("image_name");

                        %>
                        <div class="clear"></div>
                        <div class="grid_2">
                            <a href="product.jsp?id=<%=product_id%>"><img src="<%= image_name %>" /></a>
                        </div>
                        <div class="grid_9">
                            <div class="grid_5">
                                <p id="info"><a href="product.jsp?id=<%=product_id%>"><h3><span class="blue"> <%=product_name %></span></h3></a>By <%= company_name+" "+ category_name %><br/><span class="red">$ <%= price %></span></p>
                            </div>
                            <div class="grid_3 push_2">
                                <p><%=sub_category_name %>  <a href="addToCart.jsp?id=<%= product_id %>" id="greenBtn">Add to cart</a></p><p>Will Be delivered in 3 Working days</p>
                            </div>
                        </div>
                        <div class="clear"></div>
                        <%
                              }
                          rs.close();
                          st.close();
                          c.close();
                        %>

                    </div>
                </div>

                <jsp:include page="includesPage/mainHeaders/topMostViewedProducts_4.jsp"></jsp:include>

            </div> 
            
            <!--The Middle Content Div Closing -->
        </div>
        <!--The Center Content Div Closing -->
<jsp:include page="includesPage/_footer.jsp"></jsp:include>

    </body>
</html>
