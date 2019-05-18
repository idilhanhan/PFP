<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta charset="UTF-8">

     </head>
     <body class="text-center">
         <h1>Login</h1>
         <form class="form-signin" action="login", method="post">
           <div class="form-group">
              <label>Username</label><input type="text" class="form-control" id="username" placeholder="joedoe">
           </div>
           <div class="form-group">
              <label>Password</label><input type="password" class="form-control" id="password" placeholder="password">
           </div>
           <button type="submit" class="btn btn-lg btn-primary btn-block">Submit</button>
         </form>

     </body>

</html>