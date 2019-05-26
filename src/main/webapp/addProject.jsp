<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta charset="UTF-8">

    </head>

    <body class="text-center">

         <jsp:include page="header.jsp" />

         <%
             if (request.getAttribute("failure") != null){%>
               <div class="alert alert-danger" role="alert">
                   <strong>Addition Unsuccessful!</strong> Please make sure the project name is unique and every field is filled
               </div>
             <%}
         %>

             <h1>Add Project</h1>
             <form action="addProject" method="post">
               <div class="form-group">
                 <label>Project Name</label><input type="text" class="form-control" name="projectName" placeholder="Project Doe" required>
               </div>
               <div class="form-group">
                 <label>Abstract</label><input type="text" class="form-control" name="projectAbstract" placeholder="Sample project" required>
               </div>
               <div class="form-group">
                  <label>Member Limit</label><input type="text" class="form-control" name="memberLimit" placeholder="3" required>
               </div>
               <div class="form-group">
                 <label>Keywords</label><input type="text" class="form-control" name="keywords" placeholder="Keywords separated with space" required>
               </div>
               <button type="submit" class="btn btn-lg btn-primary">Add</button>
             </form>
    </body>
</html>