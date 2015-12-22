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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author ryancorbin
 */

public class OrderController extends HttpServlet {


    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/jsp-o/listOrders.jsp";
    private static final String UPDATE_PAGE = "/jsp-o/updateOrder.jsp";
    private static final String INSERT_PAGE = "/jsp-o/insertOrder.jsp";
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
    private static final String CANCEL_ACTION = "cancel";
    
    private static final String ID = "Id";
    private static final String ORDERID = "orderId";
    private static final String ORDERITEM = "orderItem";
    private static final String CUSTOMEREMAIL = "customerEmail";
    private static final String ORDERITEMS= "orderItems";
    private static final String ORDERTOTAL = "orderTotal";
    
    
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
        OrderService orderService = (OrderService) wctx.getBean("orderService");
        FoodItemService foodService = (FoodItemService) wctx.getBean("foodItemService");
        OrderItemService orderItemService = (OrderItemService) wctx.getBean("orderItemService");
        
        ctx.setAttribute("sessions", sessionCounter.getActiveSessions());
        
        Order order = null;
        FoodItem tempFood = null;
        
        try {
            
            
            switch (action){
                case LIST_ACTION:
                    this.refreshList(request, orderService);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:
                    String orderID = request.getParameter(ID);
                    order = orderService.findByIdAndFetchOrderItemsEagerly(orderID);
                    orderService.remove(order);
                    //Reload page without deleted author
                    this.refreshList(request, orderService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    break;
                case UPDATE_ACTION:
                    String orderItemIDU = request.getParameter(ID);
                    order = orderService.findByIdAndFetchOrderItemsEagerly(orderItemIDU);
                    request.setAttribute("order", order);
                    destination = UPDATE_PAGE; 
                    break;
                case INSERT_ACTION:
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = new java.util.Date();
                    request.setAttribute("date", dateFormat.format(date));
                    destination = INSERT_PAGE;
                    break;
                case INSERT_FINAL_ACTION:
                    String customerEmail = request.getParameter(CUSTOMEREMAIL);
                    Float total = Float.parseFloat(request.getParameter(ORDERTOTAL));
                    order = new Order();
                    order.setCustomerEmail(customerEmail);
                    
                    List<FoodItem> foodList = foodService.findAll();
                    Set<OrderItem> result = new HashSet();
                    for(FoodItem food : foodList){
                        Integer orderAmount = Integer.parseInt(request.getParameter(ORDERITEM + food.getFoodId()));
                        
                        if (orderAmount > 0){
                           OrderItem temp = new OrderItem();
                           temp.setOrderId(order);
                           temp.setFoodName(food.getFoodName());
                           temp.setFoodItemAmount(orderAmount);
                           result.add(temp);
                           
                           //Adjusting the Inventory based on the orderedAmount.
                           //Currently no way to adjust the inventory based on if the stock falls
                           //below zero.  Simply defaults to zero.
                           Integer inventory = food.getFoodInventory();
                           Integer newInventory = inventory - orderAmount;
                           
                           if (newInventory > 0){
                               food.setFoodInventory(newInventory);
                               foodService.edit(food);
                           } else {
                               food.setFoodInventory(0);
                               foodService.edit(food);
                           }
                        }
                    }
                    
                    order.setOrderItems(result);
                    order.setOrderTotal(total);
                    orderService.edit(order);
                    this.refreshList(request, orderService);
                    destination = LIST_PAGE; 
                    break;
                case UPDATE_FINAL_ACTION:
                    Integer orderIDU = Integer.parseInt(request.getParameter(ORDERID));
                    String customerEmailU = request.getParameter(CUSTOMEREMAIL);
                    Float totalU = Float.parseFloat(request.getParameter(ORDERTOTAL));
                    order = new Order();
                    
                    //Remove All Current Order Items
                    List<OrderItem> currentOrderItems = orderItemService.findAll();
                    for (OrderItem orderItem : currentOrderItems){
                        Order orderItemOrder = orderItem.getOrderId();
                        if (orderItemOrder.getOrderId().equals(orderIDU)){
                            orderItemService.remove(orderItem);
                        }
                    }
                    
                    List<FoodItem> foodListU = foodService.findAll();
                    Set<OrderItem> resultU = new HashSet();
                    for(FoodItem food : foodListU){
                        Integer orderAmount = Integer.parseInt(request.getParameter(ORDERITEM + food.getFoodId()));
                        
                        if (orderAmount > 0){
                           OrderItem temp = new OrderItem();
                           temp.setOrderId(order);
                           temp.setFoodName(food.getFoodName());
                           temp.setFoodItemAmount(orderAmount);
                           resultU.add(temp);
                        }
                    }
                    
                    order.setOrderId(orderIDU);
                    order.setCustomerEmail(customerEmailU);
                    order.setOrderItems(resultU);
                    order.setOrderTotal(totalU);
                    orderService.edit(order);
                    this.refreshList(request, orderService);
                    destination = LIST_PAGE; 
                    break;
                    
                case CANCEL_ACTION:
                    this.refreshList(request, orderService);
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
    
    private void refreshList(HttpServletRequest request, OrderService orderService) throws Exception {
        List<Order> orders = orderService.findAllAndFetchOrderItemsEagerly();
        request.setAttribute("orders", orders);
    }
    
//    @Override
//    public void init() throws ServletException {
//        // Get init params from web.xml
//        ServletContext ctx = getServletContext();
//        
//        driverClass = ctx.getInitParameter("driverClass");
//        url = ctx.getInitParameter("url");
//        userName = ctx.getInitParameter("userName");
//        password = ctx.getInitParameter("password");
//        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
//        daoClassName = this.getServletContext().getInitParameter("foodItemDAO");
//        dataSource = ctx.getInitParameter("dataSource");
//    }

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
