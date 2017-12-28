<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>EJB 3.1</title>
</head>
<body>
<h3>Enterprise JavaBeans 3</h3>
<p>${answer}</p>
<form action="sayHello" method="post">
    Введите имя : <input type="text" name="name" value=""/>
    <input type="submit" value="OK"/>
</form>
</body>
</html>