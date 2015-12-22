<%-- 
    Document   : updateAuthor
    Created on : Oct 15, 2015, 4:27:41 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Items - Update</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/screen.css"/>
        <link rel="stylesheet" type="text/css" href="css/correctionCSS.css">
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/jquery.validate.js"></script>
    </head>
    <body class="foodItemsList">
        <div class="container-fluid" id="formFormat">
            <br>
                <div class="row">
                    <div class="col-md-6">
                    <h1 id="title">Update an Order</h1>
                    <p>Current User: ${sessionScope.user}</p>
            </div>
            <div class="col-md-1">
                <img src="corbinseal.png" alt="The Seal" height="150" width="150" margin="10">
            </div>
        </div>
            <br>
        
        <form id="form1" name="form1" Method="POST" action="OrderController?action=updateFinal">
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <fieldset>
                    <p>
                        <label for="orderId">Order Id<span> ${order.orderId}</span></label>
                        <br>
                        <input hidden="orderId" name="orderId" value="${order.orderId}" required>
                    </p>
                    
                    <p>
                        <label for="customerEmail"> Customer Email<Span> (Required, email format)</span></label>
                        <br>
                        <input id="customerEmail" name="customerEmail"  minlength="2" type="email" value="${order.customerEmail}" required/>
                    </p>
                    <p>This will remove all current order items on this order</p>
                    <table id="foodItemListTable" style="width: 50%;" border="1" cellspacing="0" cellpadding="4">
                        <thead>
                        <tr style="background-color: black;color:white;">
                            <th align="left" class="tableHead">Product ID</th>
                            <th align="left" class="tableHead">Product Name</th>
                            <th align="right" class="tableHead">Amount Ordered</th>
                        </tr>
                        </thead>
                        <tbody id="foodItemTblBody">
                
                        </tbody>
                    </table>
                    <p>
                        <label for="orderTotal"> Order Total<span>(Required)</span></label>  
                        <br>
                        <input id="orderTotal"name="orderTotal" number="true" value="${order.orderTotal}"required/>
                    </p>
          
                    <br>
                    <input class="btn btn-primary" type="submit" name="submit" value="Update"  style="margin: 10px 0px 10px 0px;"/>
             </fieldset>
            <br>
            </sec:authorize>
        </form>
        <form id="form2" name="form1" Method="POST" action="OrderController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel" style="margin: 10px 0px 10px 0px;"/>
        </form> 
         <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize> 
        </div>   
            
                    
        <script>
         $('#form1').validate(); 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
        <script src="js/app.js" type="text/javascript"></script>
        </script>
    </body>
</html>
