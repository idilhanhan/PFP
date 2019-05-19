<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
             <meta charset="UTF-8">
        </head>

        <body>

        <jsp:include page="header.jsp" />


        <h1>Project Ideas of ${session.getAttribute("currUser").getName}</h1>

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
                       <form action="leave" method="post">
                         <input type="hidden" name="projectToLeave" value="${id}" />
                         <input class="btn btn-danger" type="submit" value="Leave">
                       </form>
                    </div>
                </div>
            <%
                }

            %>

        </body>

</html>