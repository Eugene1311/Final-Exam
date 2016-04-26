<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Projects Manager</title>
  <link rel="stylesheet" href="css/reset.css">
  <link rel="stylesheet" href="css/style.css">
  <script src="js/app.js"></script>
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="locale" var="loc"/>
  <fmt:message bundle="${loc}" key="local.title" var="title"/>
  <fmt:message bundle="${loc}" key="local.signInButton" var="signInButton"/>
  <fmt:message bundle="${loc}" key="local.signUpButton" var="signUpButton"/>
</head>
<body>
  <header class="header">
    <div class="container">
      <div class="logo">
          <a href="/" class="logo_link">${title}</a>
      </div>
      <div class="languages">
          <a href="/LocalizationServlet?locale=ru" class="header_button">Рус</a>
          <a href="/LocalizationServlet?locale=eng" class="header_button languages-selected">Eng</a>
      </div>
      <div class="sign_buttons">
          <a href="#" class="header_button sign_button sign_button-sighin">${signInButton}</a>
          <a href="#" class="header_button sign_button sign_button-sighup">${signUpButton}</a>
      </div>
    </div>
  </header>
  <main class="main">${cookie.locale}</main>
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
                            