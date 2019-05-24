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
        if (request.getAttribute("joinCheck") != null){
            int check = (Integer)request.getAttribute("joinCheck");
            if (check == 0){  %>
               <div class="alert alert-danger alert-dismissible fade show" role="alert">
                   <strong>Join unsuccessful!</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                     </button>
               </div>
                    <%}
            else if ( check == 1){  %>
                 <div class="alert alert-success alert-dismissible fade show" role="alert">
                      <strong>Join successful!</strong>
                      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                         <span aria-hidden="true">&times;</span>
                      </button>
                  </div>
            <%}
            else if ( check == 2){  %>
                  <div class="alert alert-danger alert-dismissible fade show" role="alert">
                       <strong>Join unsuccessful! You are already participating in the project! </strong>
                       <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                       </button>
                  </div>
            <%}
            else if ( check == 3){  %>
                  <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Join unsuccessful! This project is not accepting any more participants! </strong>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                           <span aria-hidden="true">&times;</span>
                        </button>
                  </div>
            <%}

        }
    %>


    <h1>Project Ideas</h1>

        <%
            List<ProjectIdea> projects = (List<ProjectIdea>) request.getAttribute("projects");

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

        %>

    </body>

</html>