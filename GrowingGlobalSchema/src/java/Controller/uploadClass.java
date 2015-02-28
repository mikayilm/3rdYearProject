/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Mikayil
 */
@MultipartConfig
public class uploadClass extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
//        System.out.println("test: " + request.getParameterMap().size() );
        final Part filePart = request.getPart("file");
        InputStream filecontent = null;
        StringBuilder sb = new StringBuilder();

        try {
            filecontent = filePart.getInputStream();
            int read = 0;
            while ((read = filecontent.read()) != -1) {
//                System.out.println("from while: " + (char)read);
                sb.append((char)read);
            }

        } catch (Exception fne) {            
            System.out.println("exception: " + fne.getMessage());
        } finally {
            if (filecontent != null) {
                filecontent.close();
            }
        }
        
//        System.out.println("result: " + sb.toString());        
        request.setAttribute("test", sb.toString());        
        request.getRequestDispatcher("./index.jsp").forward(request, response);

    }
}
