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
             String searchKeywords = (String) request.getAttribute("searchKeywords");

             List<ProjectIdea> projects = (List<ProjectIdea>) request.getAttribute("projects");

             if( projects.size() == 0){%>

                <h1>No Search Results for ${searchKeywords}</h1>

             <%}
             else{%>

                <h1>Search Results for ${searchKeywords}</h1>
             <%
                 for (ProjectIdea project : projects){

                    String name = project.getName();
                    pageContext.setAttribute("name", name);
                    String info = project.toString();
                    pageContext.setAttribute("info", info);
                    int id = project.getIdea_id();
                    pageContext.setAttribute("id", id);
              %>



                 <div class="card">
                    <h5 class="card-header">Project Idea</h5>
                        <div class="card-body">
                        <h5 class="card-title">${name}</h5>
                        <p class="card-text">${info}</p>
                         <div class="btn-group">
                            <form action="join" method="post">
                               <input type="hidden" name="projectToJoin" value="${id}" /><%-- this is for the join page --%>
                               <input class="btn btn-primary" type="submit" value="Join">
                            </form>
                            <form action="project" method="post">
                               <input type="hidden" name="projectID" value="${id}" /><%-- this is for the join page --%>
                               <input class="btn btn-primary" type="submit" value="More Detail">
                            </form>
                         </div>
                         </div>
                 </div>
              <%
                  }
             }

          %>

     </body>

</html>