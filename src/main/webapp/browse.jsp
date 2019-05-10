<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <h1>Project Ideas</h1>

        <%
            List<ProjectIdea> projects = (List<ProjectIdea>) req.getAttribute("projects");

            for (ProjectIdea project : projects){
                out.println("<p>"+project+"</p>");
            }

        %>

</html>