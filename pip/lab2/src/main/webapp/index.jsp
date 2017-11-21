<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="resources/css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="resources/js/main.js"></script>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <title>TITLE</title>
</head>

<body onload="update(<%= config.getServletContext().getAttribute("condition")%>,
    '<%= config.getServletContext().getAttribute("points")%>', <%= config.getServletContext().getAttribute("r")%>)">

<table cellpadding="0" cellspacing="0" border="1">
    <tr>
        <th colspan="4">
            Performed by Orlova Kristina Aleksandrovna<br/>
            Group P3202, Variant 550
        </th>
    </tr>

    <tr>
        <td>
            <table class="result" cellspacing="0" cellpadding="0" border="1">
                <tr id="title">
                    <td>
                        X
                    </td>
                    <td>
                        Y
                    </td>
                    <td>
                        R
                    </td>
                    <td>
                        Result
                    </td>
                </tr>
            </table>
            <div id="table-scroll">
                <table class="result" cellspacing="0" cellpadding="0" border="1" id="result_table"></table>
            </div>
        </td>

        <td>
            <div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
            <input type="submit" value="Create graph" id="button1"
                   onclick="resizeGraph(<%= config.getServletContext().getAttribute("condition")%>,
                           '<%= config.getServletContext().getAttribute("points")%>'); checkParams('x')" disabled/><br/>

            <form method="GET" action="main">
                <label for="r" class="label">Input R (1; 4)</label><br/>
                <input type="text" id="r" name="r" class="field" oninput="checkParams('r')"/>
                <span id="r_span" class="span"></span> <span id="r_tick" class="tick"></span><br/>

                <label for="x" class="label">Input X (-5; 5)</label><br/>
                <input type="text" id="x" class="field" name="x" oninput="checkParams('x')"/>
                <span id="x_span" class="span"></span> <span id="x_tick" class="tick"></span> <br/>

                <label for="y" class="label">Choose Y</label><br/>
                <select class="field" size="4" id="y" name="y">
                    <option disabled>Choose Y</option>
                    <option value="-4" selected> -4</option>
                    <option value="-3"> -3</option>
                    <option value="-2"> -2</option>
                    <option value="-1"> -1</option>
                    <option value="0"> 0</option>
                    <option value="1"> 1</option>
                    <option value="2"> 2</option>
                    <option value="3"> 3</option>
                    <option value="4"> 4</option>
                </select>
                <input type="submit" value="Check!" id="button2" disabled/>
            </form>
        </td>
    </tr>

    <tr id="clear"></tr>
</table>
</body>
</html>
