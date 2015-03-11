<%-- 
    Document   : TableMap
    Created on : Mar 6, 2015, 7:13:32 PM
    Author     : Mikayil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        <!-- Copyright 1998-2015 by Northwoods Software Corporation. -->
        <script src="./resources/go.js"></script>
        <script src="./resources/goSamples.js"></script> <!-- this is only for the GoJS Samples framework -->
       
    </head>
    <body onload="init()" class="home">
         <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean> 
           
        
        <div class="header">
                <h3>Growing Database Schemas</h3>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        
        <div id="sample">
            <div id="myDiagram" style="background-color: white; width: 100%; height: 540px"></div>
            <!--<div id="myDiagram" style="background-color: white; border: solid 1px black; width: 100%; height: 700px"></div>-->
        </div>
        <script id="code">
            function init() {
                if (window.goSamples) goSamples(); // init for these samples -- you don't need to call this
                var $ = go.GraphObject.make; // for conciseness in defining templates
            
            myDiagram =
                $(go.Diagram, "myDiagram", // must name or refer to the DIV HTML element
                {
                    initialContentAlignment: go.Spot.Center,
                    allowDelete: false,
                    allowCopy: false,
                    layout: $(go.ForceDirectedLayout),
                    "undoManager.isEnabled": true
                });
            // define several shared Brushes
            var bluegrad = $(go.Brush, go.Brush.Linear, { 0: "rgb(150, 150, 250)", 0.5: "rgb(86, 86, 186)", 1: "rgb(86, 86, 186)" });
            var greengrad = $(go.Brush, go.Brush.Linear, { 0: "rgb(158, 209, 159)", 1: "rgb(67, 101, 56)" });
            var redgrad = $(go.Brush, go.Brush.Linear, { 0: "rgb(206, 106, 100)", 1: "rgb(180, 56, 50)" });
            var yellowgrad = $(go.Brush, go.Brush.Linear, { 0: "rgb(254, 221, 50)", 1: "rgb(254, 182, 50)" });
            var lightgrad = $(go.Brush, go.Brush.Linear, { 1: "#E6E6FA", 0: "#FFFAF0" });
            // the template for each attribute in a node's array of item data
            var itemTempl =
                $(go.Panel, "Horizontal",
                    $(go.Shape,
                        { desiredSize: new go.Size(10, 10) },
                        new go.Binding("figure", "figure"),
                        new go.Binding("fill", "color")),
                    $(go.TextBlock,
                        { stroke: "#333333",
                          font: "bold 14px sans-serif" },
                        new go.Binding("text", "name"))
                );
            // define the Node template, representing an entity
            myDiagram.nodeTemplate =
                $(go.Node, "Auto", // the whole node panel
                    { selectionAdorned: true,
                      resizable: true,
                      layoutConditions: go.Part.LayoutStandard & ~go.Part.LayoutNodeSized,
                      fromSpot: go.Spot.AllSides,
                      toSpot: go.Spot.AllSides,
                      isShadowed: true,
                      shadowColor: "#C5C1AA" },
                    new go.Binding("location", "location").makeTwoWay(),
                    // define the node's outer shape, which will surround the Table
                    $(go.Shape, "Rectangle",
                        { fill: lightgrad, stroke: "#756875", strokeWidth: 3 }),
                    $(go.Panel, "Table",
                        { margin: 8, stretch: go.GraphObject.Fill },
                        $(go.RowColumnDefinition, { row: 0, sizing: go.RowColumnDefinition.None }),
                        // the table header
                        $(go.TextBlock,
                        {
                            row: 0, alignment: go.Spot.Center,
                            margin: new go.Margin(0, 14, 0, 2), // leave room for Button
                            font: "bold 16px sans-serif"
                        },
                        new go.Binding("text", "key")),
                    // the collapse/expand button
                    $("Button",
                    {
                        row: 0, alignment: go.Spot.TopRight,
                        "ButtonBorder.stroke": null,
                        click: function(e, but) {
                          var list = but.part.findObject("LIST");
                          if (list !== null) {
                            list.diagram.startTransaction("collapse/expand");
                            list.visible = !list.visible;
                            var shape = but.findObject("SHAPE");
                            if (shape !== null) shape.figure = (list.visible ? "TriangleUp" : "TriangleDown");
                            list.diagram.commitTransaction("collapse/expand");
                          }
                        }
                    },
                    $(go.Shape, "TriangleUp",
                    { name: "SHAPE", width: 6, height: 4 })),
                // the list of Panels, each showing an attribute
                $(go.Panel, "Vertical",
                {
                    name: "LIST",
                    row: 1,
                    padding: 3,
                    alignment: go.Spot.TopLeft,
                    defaultAlignment: go.Spot.Left,
                    stretch: go.GraphObject.Horizontal,
                    itemTemplate: itemTempl
                },
                new go.Binding("itemArray", "items"))
                ) // end Table Panel
            ); // end Node
            // define the Link template, representing a relationship
            myDiagram.linkTemplate =
                $(go.Link, // the whole link panel
                {
                    selectionAdorned: true,
                    layerName: "Foreground",
                    reshapable: true,
                    routing: go.Link.AvoidsNodes,
                    corner: 5,
                    curve: go.Link.JumpOver
                },
                $(go.Shape, // the link shape
                  { stroke: "#303B45", strokeWidth: 2.5 }),
                $(go.TextBlock, // the "from" label
                  {
                    textAlign: "center",
                    font: "bold 14px sans-serif",
                    stroke: "#1967B3",
                    segmentIndex: 0,
                    segmentOffset: new go.Point(NaN, NaN),
                    segmentOrientation: go.Link.OrientUpright
                  },
                  new go.Binding("text", "text")),
                $(go.TextBlock, // the "to" label
                {
                    textAlign: "center",
                    font: "bold 14px sans-serif",
                    stroke: "#1967B3",
                    segmentIndex: -1,
                    segmentOffset: new go.Point(NaN, NaN),
                    segmentOrientation: go.Link.OrientUpright
                },
                new go.Binding("text", "toText"))
                );


            // create the model for the E-R diagram
            var nodeDataArray = [
                <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="table" >
                    <c:set var="isFirst" value = "true"/>        
                    { key: "${table}",
                      items: [        
                        <c:forEach items="${query_proc.tableList(table, query_proc.DBname)}" var="column" >
                            <c:choose>
                                <c:when test = "${isFirst eq true}" > <%--to distinguis primary ID--%>
                                    { name: "${column.FIELD}", iskey: true, figure: "Decision", color: yellowgrad },
                                    <c:set var="isFirst" value = "false"/>        
                                </c:when>  
                                <c:otherwise>    
                                    { name: "${column.FIELD}", iskey: false, figure: "Cube1", color: bluegrad },
                                </c:otherwise>
                            </c:choose>        
                        </c:forEach>
                    ] },    
                </c:forEach> 
            ];

            var linkDataArray = [
                <c:forEach items="${query_proc.getRelation(param)}" var="map" >
                    { from: "${map.key}", to: "${map.value}", text: "", toText: "" },
                </c:forEach>
            ]; 
            
            myDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
            }
        </script>
 
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>           

<!--    //        var nodeDataArray = [
    //        { key: "Products",
    //        items: [ { name: "ProductID", iskey: true, figure: "Decision", color: yellowgrad },
    //        { name: "ProductName", iskey: false, figure: "Cube1", color: bluegrad },
    //        { name: "SupplierID", iskey: false, figure: "Decision", color: "purple" },
    //        { name: "CategoryID", iskey: false, figure: "Decision", color: "purple" } ] },
    //        { key: "Suppliers",
    //        items: [ { name: "SupplierID", iskey: true, figure: "Decision", color: yellowgrad },
    //        { name: "CompanyName", iskey: false, figure: "Cube1", color: bluegrad },
    //        { name: "ContactName", iskey: false, figure: "Cube1", color: bluegrad },
    //        { name: "Address", iskey: false, figure: "Cube1", color: bluegrad } ] },
    //        { key: "Categories",
    //        items: [ { name: "CategoryID", iskey: true, figure: "Decision", color: yellowgrad },
    //        { name: "CategoryName", iskey: false, figure: "Cube1", color: bluegrad },
    //        { name: "Description", iskey: false, figure: "Cube1", color: bluegrad },
    //        { name: "Picture", iskey: false, figure: "TriangleUp", color: redgrad } ] },
    //        { key: "Order Details",
    //        items: [ { name: "OrderID", iskey: true, figure: "Decision", color: yellowgrad },
    //        { name: "ProductID", iskey: true, figure: "Decision", color: yellowgrad },
    //        { name: "UnitPrice", iskey: false, figure: "MagneticData", color: greengrad },
    //        { name: "Quantity", iskey: false, figure: "MagneticData", color: greengrad },
    //        { name: "Discount", iskey: false, figure: "MagneticData", color: greengrad } ] }
//            ];
//            var linkDataArray = [
//            { from: "Products", to: "Suppliers", text: "0..N", toText: "1" },
//            { from: "Products", to: "Categories", text: "0..N", toText: "1" },
//            { from: "Order Details", to: "Products", text: "0..N", toText: "1" }
//            ];-->
            

