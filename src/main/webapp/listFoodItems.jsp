<%-- 
    Document   : ListFoodItems
    Created on : Oct 12, 2015, 1:43:58 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <form id="formInsert" method="POST" action="FoodItemController?action=insert">
                    <input class="btn btn-primary" type="submit" name="insert" value="Insert"/> 
                </form>
                
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
            <td align="center">${a.productID}</td>
            <td align="left">${a.productName}</td>
            <td align="left">
                ${fn:substring(a.productDescription, 0, 200)}...
            </td>
            <td align="center"><img src="${a.imageURL}" alt="Smiley face" height="100" width="100"></td>
            <td align="center">
                <fmt:formatNumber value="${a.productPrice}" type="currency"/>
            </td>
            <td align="center">
                <form id="formDelete" method="POST" action="FoodItemController?action=delete&Id=${a.productID}">
                    <input class="btn btn-primary btn-xs" type="submit" name="delete" value="Delete" style="padding: 5px 10px;"/> 
                </form>
            </td>
             <td align="center">
                <form id="formUpdate" method="POST" action="FoodItemController?action=update&Id=${a.productID}">
                <input class="btn btn-primary btn-xs" type="submit" name="update" value="Update" style="padding: 5px 10px;"}/> 
                </form>
            </td>
            <td align="center">
                  <form id="formInventory" method="POST" action="FoodItemController?action=updateInventory&Id=${a.productID}">  
                  <div class="center">
                        <p>
                          </p><div class="input-group">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="${a.productID}inventory">
                                    <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                              <input type="text" name="${a.productID}inventory" class="form-control input-number" value="${a.productInventory}" min="1" max="100"
                                     action="FoodItemController?action=updateInventory&Id=${a.productID}">
                                     <span class="input-group-btn">
                                  <button type="button" class="btn btn-success btn-number" data-type="plus"  data-field="${a.productID}inventory">
                                      
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                              </span>
                          </div>
                                  <br>
                              <!--<form id="formInventory" method="POST" action="FoodItemController?action=updateInventory&Id=${a.productID}">-->    
                                <input class="btn btn-primary btn-xs" type="submit" name="update" value="Confirm Inventory Change"}/>
                            <p></p>
                            </form>
                    </div>                      
<!--                    <input type="number" name="inventory" min="1" max="200" value="${a.productInventory}">
                    <input class="btn btn-primary btn-xs" type="submit" name="update" value="Confirm Inventory Change"}/>-->
                
                </form>
            </td>
        
          
        </tr>
        </c:forEach>
        </table>
        
        
                
                
        
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
        
        </div>
      <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
      <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
      <script src="js/numberInput.js"></script>
    </body>
</html>
