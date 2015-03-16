<%-- 
    Document   : DatabaseDisplay
    Created on : Feb 28, 2015, 4:26:20 PM
    Author     : Mikayil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        
        <title>Table</title>
    </head>
    <body class="home">    
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean>     
                
        <!-- If navigation is not coming from Relations class then it comes from TableDisplay class
        and when it comes from TableDisplay class the types of columns needs to be updated      -->
        <c:if test="${param.submit != 'Edit Columns in Tables'}" >
            ${query_proc.alterDDL(query_proc.DBname, query_proc.tableName.get(0), param )}       
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
        <div class="database_section">
            <form action ="./Relations.jsp" method="post" >
                
                <div style="width:100%;">
                    <img title="Select the table to edit column of it." src="resources/images/info.png">                    
                    <font size="4">Database name:  ${query_proc.DBname}  </font><br>
                </div>
                    
                    <table class="table table-striped" style="width:50%" >
                            <tr>
                                <th>Table Names</th>
                            </tr>
                          <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="table" >
                            <tr>
                                <td><a href="./TableDisplay.jsp?DBname=${query_proc.DBname}&tbl_nm=${table}">${table}</a></td>
                            </tr>
                          </c:forEach>
                    </table>                
                
                <div style="width:100%;">
                    <img title="To end editing and to go back to setting relations." src="resources/images/info.png">                    
                    <button>Save and Continue</button><br>
                </div>
                
                <br><br>
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>