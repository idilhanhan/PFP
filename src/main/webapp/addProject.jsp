<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <meta charset="UTF-8">

         </head>

         <%
             if (request.getAttribute("failure") != null){%>
               <div class="alert alert-danger alert-dismissible fade show" role="alert">
                   <strong>Addition Unsuccessful!</strong> Please make sure the project name is unique and every field is filled
                   <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                   </button>
               </div>
             <%}
         %>

         <body class="text-center">
             <h1>Add Project</h1>
             <form action="addProject">
               <div class="form-group">
                 <label for="name">Project Name</label><input type="text" class="form-control" name="projectName" placeholder="Project Doe">
               </div>
               <div class="form-group">
                 <label for="abstract">Abstract</label><input type="text" class="form-control" name="projectAbstract" placeholder="Sample project">
               </div>
               <div class="form-group">
                  <label for="limit">Member Limit</label><input type="text" class="form-control" name="memberLimit" placeholder="3">
               </div>
               <div class="form-group">
                 <label for="keywords">Another label</label><input type="text" class="form-control" id="keywords" placeholder="Keywords separated with space">
               </div>
             </form>
         </body>
</html>