/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.SQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mikayil
 */
@WebServlet(name = "Controll", urlPatterns = {"/Controll"})
public class Controll extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            String action = request.getParameter("action");
            String redirectPage = "page_404.jsp";
            
            switch(action){
                case "tabledisplay":
                    
                    String dbName = request.getParameter("DBname");
                    String dbAction = request.getParameter("dbAction");
                    String query  =request.getParameter("query");
                    String tbName = request.getParameter("tbl_nm");
                    
                    QueryProc qp = new QueryProc();
//                    qp.resetDB(dbName, dbAction);
                    qp.setInputVal(query);
                    qp.setDBname(dbName);
                    qp.setTable_name_process(tbName);
                    qp.Input();
                    
                    request.setAttribute("tableNamePrc", tbName);
                    request.setAttribute("tableList", qp.TBList(tbName, dbName) );
                    request.setAttribute("columnNames", qp.getColumnName());
                    
                    redirectPage = "TableDisplay.jsp";
                    break;
            }
            
            
            request.getRequestDispatcher(redirectPage).forward(request, response);
            
        }
    }
    
    
    public String getDBByCode(int DBCode){
        
        return new QueryProc().getDBnames().get(DBCode);
    }
    
    public List getTableColumns(String DBName, String TableName){
        
        return new SQL().getTableProperty(TableName, DBName);
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

    

}
