<%-- 
    Document   : index
    Created on : Feb 8, 2015, 9:47:00 PM
    Author     : Mikayil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        
        <c:if test="${param.submit == 'Undo Last Input'}" >
            ${query_proc.undoLastInput(query_proc.DBname)}       
        </c:if>
        
        
        <div class="header">
            
            <h3>Growing Database Schemas</h3>
            <ul class="urlParent" >
                <li><a href="./home.html">Home</a></li>
                <li><a href="./index.jsp">Start</a></li>
                <li><a href="#"> About</a></li>
            </ul>
            
            <div class="index_phase">
                <img alt="step1-start" title="" src="resources/images/phase-start.png" width="300" height="40">                    
            </div>
            
        </div>

        <script>
            function validateForm() {

                // to check if the db name has been entered
                if (document.getElementById("DBname").value === "" ){
                    alert("PLEASE ENTER A DATABASE NAME \nOR CHOSE FROM THE LIST");
                    return false;
                }
                else if(document.getElementById("DBname").value === "chose database name"){
                    alert("PLEASE ENTER A DATABASE NAME \nOR CHOSE FROM THE LIST");
                    return false;
                }
                // to check if the query has been antered
                if (document.getElementById("inpt").value === "") {
                    alert("PLEASE ENTER A QUERY");
                    return false;
                }                                
            }


            $("document").ready(function(){
                $("#slctRE").change(function(){                    
                    $("#selectR").val($(this).val());
                    if ($(this).val() === "Reset")
                        alert("WARNING!\n\
                        \n\nchosing RESET action \nwill delete your previous schema in selected database \n\
                        \nand generate schemas of only currently added queries ");
                });
                
                $("#slct").change(function(){
                    $("#DBname").val($(this).val());
                });  
                
                $("button[name='actBTN']").click(function(){
                    $("#action").val($(this).attr("id"));
                    $("#textForm").submit();
                });
                
            });
            
            
        </script>

        <div class="index_section">
            
            <form method="POST" action="./upload"  enctype="multipart/form-data" id="formSer" >
                
                <div style="width:100%;">
                    <font size="4">Chose a file:  </font>
                    <img title="You can upload a file containing all of the SQL queries." src="resources/images/info.png">
                </div>
                
                <input type="file" name="file" id="file" /> <br>
                <input type="submit" value="Upload" name="upload" id="upload" />               
            </form>            

            <form action="./Relations.jsp" method="POST" id="textForm" onsubmit="return validateForm();">                
                
                <div style="width:100%;">
                    <br><font size="4">Enter or chose a database name:  </font>
                    <img title="Create a new database by entering its name or choose an existing one from the list." src="resources/images/info.png">                    
                </div>
                
                <input type="text" name="DBname" id="DBname" />
                
                <select id="slct">
                    <c:forEach items="${query_proc.getDBnames()}" var="dbn" >
                        <option value="${dbn}" > ${dbn}</option>
                    </c:forEach>
                </select>

                <div style="width:100%;">
                    <br><font size="4">Enter or edit queries:  </font>
                    <img title="Enter queries that you want to create schema of or edit the ones you uploaded." src="resources/images/info.png">                    
                </div>
                
                <textarea rows="4" cols="52" id="inpt" name="query">${requestScope.test}</textarea>
                <input type="hidden" id="action" name="dbAction" value="" />
                
                <div style="width:100%;">
                    <img title="Creates the schema of the entered query or enlarges the existing schema." src="resources/images/info.png"> 
                    <button name="actBTN" id="enlarge">Create Schema</button>
                    <button name="actBTN" id="reset">Reset Schema</button>
                    <img title="Reset the current schema and create the schema of the currently entered query." src="resources/images/info.png"> 
                </div>
            </form>

        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
