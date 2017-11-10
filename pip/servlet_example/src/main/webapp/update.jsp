<%--
  Created by IntelliJ IDEA.
  User: promoscow
  Date: 01.08.17
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<section>
    <jsp:useBean id="bot" scope="request" type="web.Bot"/>
    <form method="post" action="bot?action=submit">
        <dl>
            <dt>ID: </dt>
            <dd><input type="number" name="id" value="${bot.id}" placeholder="${bot.id}" /></dd>
        </dl>
        <dl>
            <dt>Name: </dt>
            <dd><input type="text" name="name" value="${bot.name}" placeholder="${bot.name}" /></dd>
        </dl>
        <dl>
            <dt>Serial number: </dt>
            <dd><input type="number" name="serial" value="${bot.serial}" placeholder="${bot.serial}" /></dd>
        </dl>
        <button type="submit">Save</button>
    </form>
</section>
</body>
</html>