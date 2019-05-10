<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <meta charset="UTF-8">

        </head>
        <body>
            <h1>Sign Up</h1>

            <form action="signup.html", method="POST">
              <div class="form-group">
                <label>Username</label><input type="text" class="form-control" id="username" placeholder="joedoe">
              </div>
              <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" id="password" placeholder="password">
              </div>
              <button type="submit" class="btn btn-primary">Submit</button>
            </form>

        </body>
</html>