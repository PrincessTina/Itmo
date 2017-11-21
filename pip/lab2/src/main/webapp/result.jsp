<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="resources/css/result.css" rel="stylesheet" type="text/css"/>
    <title>TITLE</title>
</head>

<body>
<table>
    <tr>
        <td></td>
        <td>
            <div class="circle-slide">X<hr><%= config.getServletContext().getAttribute("x")%></div>
        </td>
        <td></td>
        <td>
            <div class="circle-slide">Y<hr><%= config.getServletContext().getAttribute("y")%></div>
        </td>
    </tr>
    <tr>
        <td>
            <div class="circle-slide">R<hr><%= config.getServletContext().getAttribute("r")%></div>
        </td>
        <td></td>
        <td>
            <div class="circle-slide">Result<hr><%= config.getServletContext().getAttribute("result")%></div>
        </td>
        <td></td>
    </tr>
    <tr>
        <td colspan="4">
            <a href="main?action=return">Back to main page!</a>
        </td>
    </tr>
</table>
</body>
</html>
