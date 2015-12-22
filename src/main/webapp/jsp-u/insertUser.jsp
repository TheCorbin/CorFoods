<%-- 
    Document   : insertFoodItem
    Created on : Oct 15, 2015, 4:27:25 PM
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
        <title>Food Items - Insert</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/screen.css"/>
        <link rel="stylesheet" type="text/css" href="css/correctionCSS.css">
         <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/jquery.validate.js"></script>
 
    </head>
    <body>
        <div class="container-fluid" id="formFormat">
            <br>
        <div class="row">
            <div class="col-md-6">
                
                <h1 id="title">Insert A User</h1>
                <p>Current User: ${sessionScope.user}</p>
            </div>
            <div class="col-md-1">
                <img src="corbinseal.png" alt="The Seal" height="150" width="150" margin="10">
            </div>
        </div>
            <br>
        <form id="form1" name="form1" Method="POST" action="UserController?action=insertFinal">
             <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <fieldset>
                    <p>
                        <label for="username">User Name <Span> (Required, Email Only)</span></label>
                        <br>
                        <input id="username" name="username"  minlength="2" type="email" required/>
                    </p>
                    <p>
                        <label for="password">Password<span>(Required)</span></label>    
                        <br>
                        <input  id="password" name="password" type="text" required="true"/>
                    </p> 
                     <p>
                        <label for="enabled">Enabled</label>  
                        <br>
                        <input id="enabled" name="enabled"  type="radio"/>
                    </p>
                     <p>
                        <label for="lastUpdate"> Last Update<span>(Required)</span></label>  
                        <br>
                        <input id="lastUpdate"name="lastUpdate" value="${date}" required/>
                    </p>
                    <p>
                        <label for="version">Version<span>(Required)</span></label>    
                        <br>
                        <input id="version" name="version" type="text" required="true"/>
                    </p> 
                    
                    <br>
                    <input class="btn btn-primary" type="submit" name="submit" value="Insert" style="margin: 10px 0px 10px 0px;"/>
            </fieldset>
            <br>
            </sec:authorize>
        </form>
        <form id="form2" name="form1" Method="POST" action="UserController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel" style="margin: 10px 0px 10px 0px;"/>
        </form> 
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize> 
        </div>  
        
        <script>
         $('#form1').validate();   
        </script>
    </body>
</html>
