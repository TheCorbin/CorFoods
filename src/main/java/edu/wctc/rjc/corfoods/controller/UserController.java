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
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author ryancorbin
 */

public class UserController extends HttpServlet {
    
//    @Inject
//    private FoodItemFacade foodsService;


    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/jsp-u/listUser.jsp";
    private static final String UPDATE_PAGE = "/jsp-u/updateUser.jsp";
    private static final String INSERT_PAGE = "/jsp-u/insertUser.jsp";
    private static final String ABOUT_PAGE = "about.jsp";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String INSERT_ACTION = "insert";
    private static final String ACTION_PARAM = "action";
    private static final String USER_PARAM = "userName";
    private static final String UPDATE_FINAL_ACTION = "updateFinal";
    private static final String INSERT_FINAL_ACTION = "insertFinal";
    private static final String UPDATE_INVENTORY_ACTION = "updateInventory"; 
    private static final String CANCEL_ACTION = "cancel";
    
    private static final String ID = "Id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENABLED = "enabled";
    private static final String LASTUPDATE = "lastUpdate";
    private static final String VERSION = "version";
    
    
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
        String action = request.getParameter(ACTION_PARAM);
        String user = request.getParameter(USER_PARAM);
        String destination = LIST_PAGE;
        
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(ctx);
        UserService userService = (UserService) wctx.getBean("userService");
        
        
        ctx.setAttribute("sessions", sessionCounter.getActiveSessions());
        
        
        if (user != null){ 
            session.setAttribute("user", user);
        }
        
        User tempUser = null;
        
        try {
            
            
            switch (action){
                case LIST_ACTION:
                    this.refreshList(request, userService);
                    destination = LIST_PAGE;
                    break;
                case DELETE_ACTION:
                    String Id = request.getParameter(ID);
                    tempUser = userService.findById(Id);
                    userService.remove(tempUser);
                    //Reload page without deleted author
                    this.refreshList(request, userService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    break;
                case UPDATE_ACTION:
                    String userNameU = request.getParameter(USERNAME);
                    User userAlt = userService.findById(userNameU);
                    request.setAttribute("user", userAlt);
                    destination = UPDATE_PAGE; 
                    break;
                case INSERT_ACTION:
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = new java.util.Date();
                    request.setAttribute("date", dateFormat.format(date));
                    destination = INSERT_PAGE;
                    break;
                case INSERT_FINAL_ACTION:
                    String userNameI = request.getParameter(USERNAME);
                    String passwordI = request.getParameter(PASSWORD);
                    Boolean enabledI = Boolean.parseBoolean(request.getParameter(ENABLED));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                    Date lastUpdateI = formatter.parse(LASTUPDATE);
                    Integer versionI = Integer.parseInt(request.getParameter(VERSION));
                    tempUser = new User();
                    tempUser.setUsername(userNameI);
                    tempUser.setPassword(passwordI);
                    tempUser.setEnabled(enabledI);
                    tempUser.setLastUpdate(lastUpdateI);
                    tempUser.setVersion(versionI);
                    userService.edit(tempUser);
                    this.refreshList(request, userService);
                    destination = LIST_PAGE; 
                    break;
                case UPDATE_FINAL_ACTION:
                    String UserNameU = request.getParameter(USERNAME);
                    String PasswordU = request.getParameter(PASSWORD);
                    Boolean EnabledU = Boolean.parseBoolean(request.getParameter(LASTUPDATE));
                    Integer VersionU = Integer.parseInt(request.getParameter(VERSION));
                    tempUser = new User();
                    tempUser.setUsername(UserNameU);
                    tempUser.setPassword(PasswordU);
                    tempUser.setEnabled(EnabledU);
                    tempUser.setVersion(VersionU);
                    userService.edit(tempUser);
                    this.refreshList(request, userService);
                    destination = LIST_PAGE; 
                    break;
                case CANCEL_ACTION:
                    this.refreshList(request, userService);
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
    
    private void refreshList(HttpServletRequest request, UserService userService) throws Exception {
        List<User> users = userService.findAll();
        request.setAttribute("users", users);
    }
    
    public static String sha512(String pwd, String salt) {

            ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
            pe.setIterations(1024);
            String hash = pe.encodePassword(pwd, salt);

            return hash;
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
