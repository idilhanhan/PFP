<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta charset="UTF-8">

     </head>
     <body class="text-center">

        <%
            if (request.getAttribute("signUp") != null){%>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Signed up!</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            <%}
        %>

         <h1>Login</h1>
         <form class="form-signin" action="login", method="post">
           <div class="form-group">
              <label>Username</label><input type="text" class="form-control" name="username" placeholder="joedoe" required>
           </div>
           <div class="form-group">
              <label>Password</label><input type="password" class="form-control" name="password" placeholder="password" required>
           </div>
           <button type="submit" class="btn btn-lg btn-primary btn-block">Login</button>
         </form>

     </body>

</html>