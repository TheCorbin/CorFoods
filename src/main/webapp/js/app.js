/* 
 * 
 * Author: Ryan Corbin
 * Version: 1.00
 */

// Auto-start anonymous functions with JQuery
// Note that all variables and functions are private, except where noted
(function ($, window, document) {
    $(function () {
        // ==================================================================
        // Private properties
        // ==================================================================

        // normal properties
        var curDate = new Date();
        var foodItemsBaseUrl = "FoodItemController";

        // properties that cache JQuery selectors
        var $document = $(document);
        var $body = $('body');
        var $foodItemTableBody = $('#foodItemTblBody');
//        var $btnDelete = $('#btnDelete');

        // ==================================================================
        // Private event handlers and functions
        // ==================================================================

        /*
         * This is a JQuery-specific event that only fires after all HTML 
         * is loaded, except images, and the DOM is ready. You must be careful 
         * to only act on DOM elements from JavaScript AFTER they have been 
         * loaded.
         * 
         * Gets a collection of author objects as a JSON array from the server.
         */
        $document.ready(function () {
            console.log("document ready event fired!");

            // Make sure we only do this on pages with an foodItem list
            if ($body.attr('class') === 'foodItemsList') {
                $.ajax({
                    type: 'GET',
                    url: foodItemsBaseUrl + "?action=listAjax",
                    success: function (foodItems) {
                        displayFoodItems(foodItems);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get foodItems for this user due to: " + errorThrown.toString());
                    }
                });
            }
        });
        
//        $btnDelete.click(function () {
//            deleteHotel();
//            return false;
//        });
                /*
         * Loops over the authors collection returned by the server and 
         * extracts individual author objects and their properties, then 
         * builds table rows and columns using this data. Each row is 
         * dynamically appended to the table body DOM element.
         */
        function displayFoodItems(foodItems) {
            $.each(foodItems, function (index, foodItem) {
                var row = '<tr class="foodItemListRow">' +
                        '<td>' + foodItem.foodId + '</td>' +
                        '<td>' + foodItem.foodName + '</td>' +
                        '<td>' +  '<input id="orderAmount' + foodItem.foodId + '" name="orderItem' + foodItem.foodId + '" type="text" value = "0" required/>' + '</td>' +
                        '</tr>';
                
                $foodItemTableBody.append(row);
            });
        }
        
//        function findAll() {
//            $.get(baseUrl + "?action=list").then(function (hotels) {
//                renderList(hotels);
//            }, handleError);
//        }
        
        
       
        
        
//        var deleteHotel = function () {
//            console.log('deleteFoodItem');
//            $.ajax({
//                type: 'POST',
//                url: foodItemsBaseUrl+ "?action=delete&foodId=" + $hotelId.val()
//            })
//            .done(function () {
//                findAll();
//                clearForm();
//                $btnDelete.hide();
//                alert("Hotel deleted successfully");
//            })
//            .fail(function ( jqXHR, textStatus, errorThrown ) {
//                alert("Hotel could not be deleted due to: " + errorThrown);
//            });
//        }

    });
}(window.jQuery, window, document));

