<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
         <meta charset="UTF-8">
    </head>

    <body>

    <jsp:include page="header.jsp" />


    <h1>Project Ideas</h1>

        <%
            List<ProjectIdea> projects = (List<ProjectIdea>) req.getAttribute("projects");

            for (ProjectIdea project : projects){
        %>
            <div class="card">
                <h5 class="card-header">Project Idea</h5>
                <div class="card-body">
                   <h5 class="card-title"><%project.getName()%</h5>
                   <p class="card-text"><%project%></p>
                   <form action="join.html" method="POST">-->
                     <input type="hidden" name="projectToJoin" value="<%project.getIdea_id()%>" />
                     <input class="btn btn-primary" type="submit" value="Join">
                   </form>
                </div>
            </div>
        <%
            }

        %>

    </body>

</html>