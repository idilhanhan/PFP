<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="rawdata.pfp.model.ProjectIdea"%>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>
<!DOCTYPE html>
<html>

    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
         <meta charset="UTF-8">
    </head>

    <body>

    <jsp:include page="header.jsp" />

    <%
        String info = (String)request.getAttribute("nameAbstract");
        pageContext.setAttribute("info", info);

        String creator = (String)request.getAttribute("creator");
        pageContext.setAttribute("creator", creator);

        int limit = (Integer)request.getAttribute("limit");
        pageContext.setAttribute("limit", limit);

        int id = (Integer)request.getAttribute("projectID");
        pageContext.setAttribute("id", id);
    %>

    <div class="card">
        <h5 class="card-header">Project Idea</h5>
           <div class="card-body">
             <h5 class="card-title">${info}</h5>
             <p class="card-text">Creator: ${creator}</p>
             <p class="card-text">Participants:
             <%
                       List<String> names = (List<String>) request.getAttribute("participantNames");
                       for (String name : names){
                            out.print(name + " ");
                       }
             %>
             </p>
             <p class="card-text">MemberLimit: ${limit}</p>
             <div class="btn-group">
                  <form action="join" method="post">
                     <input type="hidden" name="projectToJoin" value="${id}" /><%-- this is for the join page --%>
                     <input class="btn btn-primary" type="submit" value="Join">
                  </form>
                  <a class="btn btn-primary", href="browse", role="button">Go Back</a>
             </div>
           </div>
    </div>


    </body>

</html>