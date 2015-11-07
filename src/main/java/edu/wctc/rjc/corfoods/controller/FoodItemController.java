/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.controller;

import edu.wctc.rjc.corfoods.listener.MySessionCounter;
import edu.wctc.rjc.corfoods.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author ryancorbin
 */

public class FoodItemController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listFoodItems.jsp";
    private static final String UPDATE_PAGE = "/updateFoodItem.jsp";
    private static final String INSERT_PAGE = "/insertFoodItem.jsp";
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
    private static final String POUND_CURRENCY = "pound";
    
    private static final String ID = "Id";
    private static final String foodId = "foodItemID";
    private static final String foodName = "foodItemName";
    private static final String foodDescription = "foodItemDescription";
    private static final String foodURL = "foodItemURL";
    private static final String foodPrice = "foodItemPrice";
    
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private String dataSource;
    
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
        
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        ctx.setAttribute("sessions", sessionCounter.getActiveSessions());
        String currency = request.getParameter(CURRENCY_PARAM);
        String action = request.getParameter(ACTION_PARAM);
        String user = request.getParameter(USER_PARAM);
        String destination = LIST_PAGE;
        
        if (user != null){ 
            session.setAttribute("user", user);
        }
        
        try {
            FoodItemService foodsService = injectDependenciesAndGetAuthorService();
            
            switch (action){
                case LIST_ACTION:
                    this.refreshList(request, foodsService);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:
                    String foodItemID = request.getParameter(ID);
                    foodsService.deleteFoodItemById(foodItemID);
                    //Reload page without deleted author
                    this.refreshList(request, foodsService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    break;
                case UPDATE_ACTION:
                    String foodItemIDU = request.getParameter(ID);
                    FoodItem foodItemAlt = foodsService.getFoodItemById(foodItemIDU);
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
                    List colValues = new ArrayList();
                    String foodNameTemp = request.getParameter(foodName);
                    String foodDescriptionTemp = request.getParameter(foodDescription);
                    String imgURL = request.getParameter(foodURL);
                    String foodPriceTemp = request.getParameter(foodPrice);
                    colValues.add(foodNameTemp);
                    colValues.add(foodDescriptionTemp);
                    colValues.add(imgURL);
                    colValues.add(foodPriceTemp);
                    
                    foodsService.insertFoodItem(colValues);
//                authService.insertAuthor("author", descriptions, colValues);
                    this.refreshList(request, foodsService);
                    destination = LIST_PAGE; 
                    break;
                  
                case UPDATE_FINAL_ACTION:
                    List colValuesU = new ArrayList();
                    String foodIDU = request.getParameter(foodId);
                    String foodNameU = request.getParameter(foodName);
                    String foodDescriptionU = request.getParameter(foodDescription);
                    String imgURLU = request.getParameter(foodURL);
                    String foodPriceU = request.getParameter(foodPrice);
                    colValuesU.add(foodIDU);
                    colValuesU.add(foodNameU);
                    colValuesU.add(foodDescriptionU);
                    colValuesU.add(imgURLU);
                    colValuesU.add(foodPriceU);
                    foodsService.updateFoodItem(colValuesU);
                    this.refreshList(request, foodsService);
                    destination = LIST_PAGE; 
                    break;
                case UPDATE_INVENTORY_ACTION:
                    String inventoryid = request.getParameter(ID);
                    String inventory = request.getParameter(inventoryid + "inventory");
                    foodsService.updateInventory(inventoryid, inventory);
                    this.refreshList(request, foodsService);
                    destination = LIST_PAGE; 
                    break;
                    
                case CANCEL_ACTION:
                    this.refreshList(request, foodsService);
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
        List<FoodItem> foods = foodService.getAllFoodItems();
        request.setAttribute("foods", foods);
    }
    
    
    private FoodItemService injectDependenciesAndGetAuthorService() throws Exception {
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DBStrategy based on the class name retrieved
        // from web.xml
        Class dbClass = Class.forName(dbStrategyClassName);
        // Note that DBStrategy classes have no constructor params
        DbStrategy db = (DbStrategy) dbClass.newInstance();

            // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        
        
        FoodItemDAOStrategy foodItemDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        try {
            constructor = daoClass.getConstructor(new Class[]{
                DbStrategy.class, String.class, String.class, String.class, String.class
            });
        } catch(NoSuchMethodException nsme) {
            // do nothing, constructor will be null and code below handles
        }

            // This will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
        if (constructor != null) {
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password
            };
            foodItemDao = (FoodItemDAOStrategy) constructor
                    .newInstance(constructorArgs);

        } else {
            /*
             Here's what the connection pool version looks like. First
             we lookup the JNDI name of the Glassfish connection pool
             and then we use Java Refletion to create the needed
             objects based on the servlet init params
             */
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dataSource);
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbStrategy.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            foodItemDao = (FoodItemDAOStrategy) constructor
                    .newInstance(constructorArgs);
        }
        
        return new FoodItemService(foodItemDao);
    }
    
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml
        ServletContext ctx = getServletContext();
        
        driverClass = ctx.getInitParameter("driverClass");
        url = ctx.getInitParameter("url");
        userName = ctx.getInitParameter("userName");
        password = ctx.getInitParameter("password");
        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletContext().getInitParameter("foodItemDAO");
        dataSource = ctx.getInitParameter("dataSource");
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
