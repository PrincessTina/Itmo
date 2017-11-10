<%--
  Created by IntelliJ IDEA.
  User: promoscow
  Date: 26.07.17
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bot</title>
</head>
<body>
<section>
    <h3>Bot info</h3>
    <jsp:useBean id="bot" scope="request" type="web.Bot"/>
    <tr>
        <td>ID: ${bot.id} | Name: ${bot.name} | Serial number: ${bot.serial}</td>
        <td><a href="bot?action=update">Update</a></td>
    </tr>
</section>
</body>
</html>