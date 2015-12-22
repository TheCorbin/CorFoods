<%-- 
    Document   : ListUsers
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
        <title>List Users</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/correctionCSS.css">
    </head>
    <body>
        
        <div class="container-fluid" id="formFormat">
            <br>
        <div class="row">
            <div class="col-md-6">
                
                <h1 id="title">User List</h1>
                <p>Current User: ${sessionScope.user}</p>
                <p>Currently Active Sessions: ${sessions}</p>
                
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formInsert" method="POST" action="UserController?action=insert">
                    <input class="btn btn-primary" type="submit" name="insert" value="Insert"/> 
                </form>
                </sec:authorize>
                
                <br>
                <a class="btn btn-primary" href="index.html"/>Back</a> 
                <br>
                
            </div>
            <div class="col-md-1">
                <img src="corbinseal.png" alt="Smiley face" height="150" width="150">
            </div>
        </div>
                <br>
        
        <table id="formFormat" width="90%" border="1" cellspacing="0" cellpadding="4">
            <col width="5%">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">User ID</th>
                <th align="left" class="tableHead">UserName</th>
                <th align="left" class="tableHead">Password</th>
                <th align="left" class="tableHead">Enabled</th>
                <th align="left" class="tableHead">Last Update</th>
                <th align="left" class="tableHead">Version</th>
                <th align="left" class="tableHead">Delete</th>
                <th align="left" class="tableHead">Update</th>
                
        <c:forEach var="a" items="${users}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="center">${a.userId}</td>            
            <td align="center">${a.username}</td>
            <td align="left">Update to view Password</td>
            <td align="center">${a.enabled}</td>
            <td align="left">${a.lastUpdate}</td>
            <td align="center">${a.version}</td>
            
            <td align="center">
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formDelete" method="POST" action="UserController?action=delete&username=${a.username}">
                    <input class="btn btn-primary btn-xs" type="submit" name="delete" value="Delete" style="padding: 5px 10px;"/> 
                </form>
                </sec:authorize>
            </td>
             <td align="center">
                 <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formUpdate" method="POST" action="UserController?action=update&username=${a.username}">
                <input class="btn btn-primary btn-xs" type="submit" name="update" value="Update" style="padding: 5px 10px;"}/> 
                </form> 
                </sec:authorize>
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
    </body>
</html>
