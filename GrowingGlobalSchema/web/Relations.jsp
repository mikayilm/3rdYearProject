<%-- 
    Document   : Relations
    Created on : Mar 9, 2015, 7:57:34 PM
    Author     : Mikayil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>GDS</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>        
        <script src="./resources/jquery.js" ></script> 
    </head>
    <body class="home">
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean>  

        <!--To prevent functions from running when navigation does not come from Index class-->
        <c:if test="${param.query ne null}" >
            ${query_proc.resetDB(param.DBname, param.dbAction)}        
            ${query_proc.setInputVal(param.query)}
            ${query_proc.setDBname(param.DBname)}

            ${query_proc.Input()}            
        </c:if>
        
        <div class="header">
            
            <h3>Growing Database Schemas</h3>
            <ul class="urlParent" >
                <li><a href="./home.html">Home</a></li>
                <li><a href="./index.jsp">Start</a></li>
                <li><a href="#"> About</a></li>
            </ul>
            
            <div class="index_phase">
                <img alt="step1-start" title="" src="resources/images/phase-edit.png" width="300" height="40">                    
            </div>
            
        </div>

        <div class="table_section">
           <form action="./TableMap.jsp" method="POST">
               
                <div style="width:100%;">
                    <img title="By chosing table name from the lists you will set relations between tables." src="resources/images/info.png">                    
                    <font size="4">Set a relation between tables  </font><br>
                </div>
               
               <table class="table table-striped" style="width:50%" >
                    <tr>
                        <th>FROM</th>
                        <th>TO</th>
                    </tr>
                    <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="table" >
                    <tr>
                        <td>${table}</td>
                        <td>
                            <select name="${table}">
                                <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="tableSelect" >
                                  <c:if test="${table ne tableSelect}">
                                    <option value="${tableSelect}" > ${tableSelect}</option>
                                  </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    </c:forEach>
                </table>    
               
               <div style="width:100%;">                    
                   <img title="Save and continue to see the whole schema" src="resources/images/info.png">
                    <input name ='submit' type='submit' value='Save and Continue' onclick='this.form.action="TableMap.jsp";' />
                    <img title="Edit if you want to change the type of the column in any table of the schema." src="resources/images/info.png">
                    <input name ='submit' type='submit' value='Edit Columns in Tables' onclick='this.form.action="DatabaseDisplay.jsp";' />
                    <img title="to undo last entered input" src="resources/images/info.png">
                    <input name ='submit' type='submit' value='Undo Last Input' onclick='this.form.action="index.jsp";' />
                    <br><br>
                </div>
                
                
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
