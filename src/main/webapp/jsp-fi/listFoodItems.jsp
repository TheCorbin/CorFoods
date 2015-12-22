<%-- 
    Document   : ListFoodItems
    Created on : Oct 12, 2015, 1:43:58 PM
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
        <title>List Food Items</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/correctionCSS.css">
    </head>
    <body>
        
        <div class="container-fluid" id="formFormat">
            <br>
        <div class="row">
            <div class="col-md-6">
                
                <h1 id="title">Food Products List</h1>
                <p>Current User: ${sessionScope.user}</p>
                <p>Currently Active Sessions: ${sessions}</p>
                
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formInsert" method="POST" action="FoodItemController?action=insert">
                    <input class="btn btn-primary" type="submit" name="insert" value="Insert"/> 
                </form>
                </sec:authorize>
                <br>
                
                <a class="btn btn-primary" href="index.html"/>Back</a> 
                
            </div>
            <div class="col-md-1">
                <img src="corbinseal.png" alt="Smiley face" height="150" width="150">
            </div>
        </div>
                <br>
        
        <table id="formFormat" width="90%" border="1" cellspacing="0" cellpadding="4">
            <col width="5%">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead" >ID</th>
                <th align="left" class="tableHead">Product Name</th>
                <th align="left" class="tableHead">Product Description</th>
                <th align="left" class="tableHead">Image</th>
                <th align="left" class="tableHead">Price</th>
                <th align="left" class="tableHead">Delete</th>
                <th align="left" class="tableHead">Update</th>
                <th align="left" class="talbeHead">Inventory</th>
            </tr>
        <c:forEach var="a" items="${foods}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="center" id="foodId" name="foodId">${a.foodId}</td>
            <td align="left">${a.foodName}</td>
            <td align="left">
                ${fn:substring(a.foodDescription, 0, 200)}...
            </td>
            <td align="center"><img src="${a.foodimageURL}" alt="Smiley face" height="100" width="100"></td>
            <td align="center">
                <fmt:formatNumber value="${a.foodPrice}" type="currency"/>
            </td>
            <td align="center">
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
<!--                    <button class="btn btn-primary btn-xs" id="btnDelete" name="Update" type="button" value="Delete" style="padding: 5px 10px;">Delete</button>-->
                    
                <form id="formDelete" method="POST" action="FoodItemController?action=delete&Id=${a.foodId}">
                    <input class="btn btn-primary btn-xs" type="submit" name="delete" value="Delete" style="padding: 5px 10px;"/> 
                </form>
                </sec:authorize>
            </td>
             <td align="center">
                 <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formUpdate" method="POST" action="FoodItemController?action=update&Id=${a.foodId}">
                <input class="btn btn-primary btn-xs" type="submit" name="update" value="Update" style="padding: 5px 10px;"}/> 
                </form> 
                </sec:authorize>
            </td>
            
            <td align="center">
                  <form id="formInventory" method="POST" action="FoodItemController?action=updateInventory&Id=${a.foodId}">  
                  <div class="center">
                        <p>
                          </p><div class="input-group">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="${a.foodId}inventory">
                                    <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                              <input type="text" name="${a.foodId}inventory" class="form-control input-number" value="${a.foodInventory}" min="1" max="100"
                                     action="FoodItemController?action=updateInventory&Id=${a.foodId}">
                                     <span class="input-group-btn">
                                  
                                  <button type="button" class="btn btn-success btn-number" data-type="plus"  data-field="${a.foodId}inventory">
                                  
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                                   
                              </span>
                          </div>
                                  <br>
                              <!--<form id="formInventory" method="POST" action="FoodItemController?action=updateInventory&Id=${a.foodId}">-->    
                                <sec:authorize access="hasAnyRole('ROLE_MGR, ROLE_USER')">
                                  <input class="btn btn-primary btn-xs" type="submit" name="update" value="Confirm Inventory Change"}/>
                                </sec:authorize>  
                            <p></p>
                            </form>
                    </div>                                        
                
                </form>
            </td>
        
          
        </tr>
        </c:forEach>
        </table>
                
                <br>
                
        <a class="btn btn-primary" href="index.html"/>Back</a> 
        
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize> 
                
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
        
        </div>
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
      <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
      <script src="js/numberInput.js"></script>
      <script src="js/app.js" type="text/javascript"></script>
    </body>
</html>
