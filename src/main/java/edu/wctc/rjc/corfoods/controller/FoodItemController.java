/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.controller;

import edu.wctc.rjc.corfoods.listener.MySessionCounter;
import edu.wctc.rjc.corfoods.entity.*;
import edu.wctc.rjc.corfoods.service.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author ryancorbin
 */

public class FoodItemController extends HttpServlet {
    
//    @Inject
//    private FoodItemFacade foodsService;


    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/jsp-fi/listFoodItems.jsp";
    private static final String UPDATE_PAGE = "/jsp-fi/updateFoodItem.jsp";
    private static final String INSERT_PAGE = "/jsp-fi/insertFoodItem.jsp";
    private static final String ABOUT_PAGE = "about.jsp";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String INSERT_ACTION = "insert";
    private static final String ACTION_PARAM = "action";
    private static final String USER_PARAM = "userName";
    private static final String CURRENCY_PARAM = "currency";
    private static final String UPDATE_FINAL_ACTION = "updateFinal";
    private static final String INSERT_FINAL_ACTION = "insertFinal";
    private static final String UPDATE_INVENTORY_ACTION = "updateInventory"; 
    private static final String CANCEL_ACTION = "cancel";
    private static final String AJAX_LIST_ACTION = "listAjax";
    private static final String POUND_CURRENCY = "pound";
    private static final String ID_PARAM = "foodId";
    
    private static final String ID = "Id";
    private static final String foodId = "foodItemID";
    private static final String foodName = "foodItemName";
    private static final String foodDescription = "foodItemDescription";
    private static final String foodURL = "foodItemURL";
    private static final String foodPrice = "foodItemPrice";
    
    private MySessionCounter sessionCounter = new MySessionCounter();
//    private DBStrategy db;
//    private AuthorDaoStrategy authorDao;
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String currency = request.getParameter(CURRENCY_PARAM);
        String action = request.getParameter(ACTION_PARAM);
        String user = request.getParameter(USER_PARAM);
        String destination = LIST_PAGE;
        
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(ctx);
        FoodItemService foodService = (FoodItemService) wctx.getBean("foodItemService");
        PrintWriter out = response.getWriter();
        
        ctx.setAttribute("sessions", sessionCounter.getActiveSessions());
        String foodId = request.getParameter(ID_PARAM);
        
//        if (user != null){ 
//            session.setAttribute("user", user);
//        }
        
        String foodItemId = request.getParameter(ID_PARAM);
        FoodItem food = null;
        
        try {
            
            
            switch (action){
                case AJAX_LIST_ACTION:
                    List<FoodItem> foodItems = foodService.findAll();
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                    foodItems.forEach((foodItemObj) -> {
                        jsonArrayBuilder.add(
                                Json.createObjectBuilder()
                                .add("foodId", foodItemObj.getFoodId())
                                .add("foodName", foodItemObj.getFoodName())
                        );
                    });

                    JsonArray foodItemsJson = jsonArrayBuilder.build();
                    response.setContentType("application/json");
                    out.write(foodItemsJson.toString());
                    out.flush();
                    return; // must not continue at bottom!
                case LIST_ACTION:
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:
//                    PrintWriter outd = response.getWriter();
//                    foodService.deleteById(Integer.valueOf(foodItemId));
//                    response.setContentType("application/json; charset=UTF-8");
//                    response.setStatus(200);
//                    outd.write("{\"success\":\"true\"}");
//                    outd.flush();
//                    
                    String foodItemID = request.getParameter(ID);
                    FoodItem tempFoodItem = foodService.findById(foodItemID);
                    foodService.remove(tempFoodItem);
                    //Reload page without deleted author
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    break;
                case UPDATE_ACTION:
                    String foodItemIDU = request.getParameter(ID);
                    FoodItem foodItemAlt = foodService.findById(foodItemIDU);
                    request.setAttribute("foodItem", foodItemAlt);
                    destination = UPDATE_PAGE; 
                    break;
                case INSERT_ACTION:
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = new java.util.Date();
                    request.setAttribute("date", dateFormat.format(date));
                    destination = INSERT_PAGE;
                    break;
                case INSERT_FINAL_ACTION:
                    String foodNameTemp = request.getParameter(foodName);
                    String foodDescriptionTemp = request.getParameter(foodDescription);
                    String imgURL = request.getParameter(foodURL);
                    Float price = Float.parseFloat(request.getParameter(foodPrice));
                    food = new FoodItem();
                    food.setFoodName(foodNameTemp);
                    food.setFoodDescription(foodDescriptionTemp);
                    food.setFoodimageURL(imgURL);
                    food.setFoodPrice(price);
                    foodService.edit(food);
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE; 
                    break;
                case UPDATE_FINAL_ACTION:
                    List colValuesU = new ArrayList();
                    Integer foodIDU = Integer.parseInt(request.getParameter(foodId));
                    String foodNameU = request.getParameter(foodName);
                    String foodDescriptionU = request.getParameter(foodDescription);
                    String imgURLU = request.getParameter(foodURL);
                    Float priceU = Float.parseFloat(request.getParameter(foodPrice));
                    food = new FoodItem();
                    food.setFoodId(foodIDU);
                    food.setFoodName(foodNameU);
                    food.setFoodDescription(foodDescriptionU);
                    food.setFoodimageURL(imgURLU);
                    food.setFoodPrice(priceU);
                    foodService.edit(food);
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE; 
                    break;
                case UPDATE_INVENTORY_ACTION:
                    String inventoryid = request.getParameter(ID);
                    Integer inventory = Integer.parseInt(request.getParameter(inventoryid + "inventory"));
                    food = foodService.findById(inventoryid);
                    food.setFoodInventory(inventory);
                    foodService.edit(food);
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE; 
                    break;
                    
                case CANCEL_ACTION:
                    this.refreshList(request, foodService);
                    destination = LIST_PAGE; 
                    break;
                default:
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
            }
        } catch (Exception e){
                System.out.println(e.getMessage());
                request.setAttribute("errMsg", e.getCause().getMessage());
        }
        
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
           
    }
    
    private void refreshList(HttpServletRequest request, FoodItemService foodService) throws Exception {
        List<FoodItem> foods = foodService.findAll();
        request.setAttribute("foods", foods);
    }
    
    @Override
    public void init() throws ServletException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
