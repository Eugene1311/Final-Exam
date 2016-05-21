<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <base href="/">
  <meta charset="utf-8">
  <title>Projects Manager</title>
  <link rel="stylesheet" href="css/reset.css">
  <link rel="stylesheet" href="css/font-awesome.min.css">
  <link rel="stylesheet" href="css/style.css">
  <script src="js/script.js"></script>
</head>
<body>
  <%--<div id="app" class="app"></div>--%>
  <header class="header">
    <div class="container">
      <div class="logo">
        <a href="/" class="logo_link">Projects Manager</a>
      </div>
      <div class="languages">
        <a href="#" id="ruButton" class="header_button">Рус</a>
        <a href="#" id="engButton" class="header_button languages-selected">Eng</a>
      </div>
      <div class="sign_buttons">
        <a href="#" class="header_button sign_button sign_button-sighin">Sign In</a>
        <a href="#" class="header_button sign_button sign_button-sighup">Sign Up</a>
      </div>
    </div>
  </header>
  <main class="main"></main>
  <footer class="footer"></footer>
  <div class="overlay">
    <form class="sign_form">
      <label>Enter name:
        <input type="text" class="sign_form_input">
      </label>
      <label>Enter password:
        <input type="password" class="sign_form_input">
      </label>
      <input type="submit" value="Sign in" class="sign_form_input sign_form_input-submit">
    </form>
  </div>
</body>
</html>