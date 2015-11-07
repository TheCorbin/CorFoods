<%-- 
    Document   : updateAuthor
    Created on : Oct 15, 2015, 4:27:41 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <body>
        <div class="container-fluid" id="formFormat">
            <br>
                <div class="row">
                    <div class="col-md-6">
                    <h1 id="title">Update A Food Item</h1>
                    <p>Current User: ${sessionScope.user}</p>
            </div>
            <div class="col-md-1">
                <img src="corbinseal.png" alt="The Seal" height="150" width="150" margin="10">
            </div>
        </div>
            <br>
        <h1 id="title">Update A Food Item</h1>
        
        <form id="form1" name="form1" Method="POST" action="FoodItemController?action=updateFinal">
            <fieldset>
                    <p>
                        <label for="foodItemID">Food Item ID<span> ${foodItem.productID}</span></label>
                        <br>
                        <input hidden="productID" name="foodItemID" value="${foodItem.productID}" required>
                    </p>
                    
                    
                    <p>
                        <label for="foodItemID">Food Item Name<span>(Required, more than two characters)</span></label>
                        <br>
                        <input id="foodItemName" name="foodItemName" value="${foodItem.productName}" type="text" required/>
                    </p>
                    
                    <p>
                        <label for="foodItemDescription">Food Description<span>(Required)</span></label>
                        <br>
                        <input id="foodItemDescription" name="foodItemDescription" value="${foodItem.productDescription}" type="text" required/>
                    </p>
                    
                    <p>
                        <label for="foodItemURL">Food Item Image URL<span>(Required, URL Format (ex: http://www...))</span></label>
                        <br>
                        <input id="foodItemURL" name="foodItemURL" value="${foodItem.imageURL}" type="url" required/>
                    </p>
                    
                   <p>
                        <label for="foodItemPrice">Food Item Price<span>(Required)</span></label>
                        <br>
                        <input id="foodItemPrice" name="foodItemPrice" value="${foodItem.productPrice}" number="true" required/>
                    </p>
          
                    <br>
                    <input class="btn btn-primary" type="submit" name="submit" value="Update"  style="margin: 10px 0px 10px 0px;"/>
             </fieldset>
            <br>
        </form>
        <form id="form2" name="form1" Method="POST" action="FoodItemController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel" style="margin: 10px 0px 10px 0px;"/>
        </form> 
        </div>            
                    
        <script>
         $('#form1').validate();  
        </script>
    </body>
</html>
