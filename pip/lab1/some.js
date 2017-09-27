function checkField() {
    var x = document.getElementById('x');
    var p = parseFloat(x.value);

    if ((!/^-?\d+\.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
        document.getElementById('x_span').innerHTML = "Invalid format";
        document.getElementById('button').disabled = true;
        document.getElementById('tick').innerHTML = "";
    } else if ((p <= -3) || (p >= 3)) {
        document.getElementById('x_span').innerHTML = "The number is out of (-3;3)";
        document.getElementById('button').disabled = true;
        document.getElementById('tick').innerHTML = "";
    } else {
        document.getElementById('tick').innerHTML = "Correct data";
        document.getElementById('x_span').innerHTML = "";
        document.getElementById('button').disabled = false;
    }
}

function exchangeParameters() {
    var x = document.getElementById('x').value;
    var y = getY();
    var r = getR();
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE ) {
            if (xmlhttp.status === 200) {
                var str = xmlhttp.responseText;
                var arr = str.split(";");
                getRow(arr[3], arr[4], arr[5], arr[1], arr[2], arr[0], arr[6]);

            } else {
                alert(xmlhttp.statusText); // вызвать обработчик ошибки с текстом ответа
            }
        }
    };

    xmlhttp.open("POST", "main.php", true);
    xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlhttp.send("x=" + x + "&y=" + y + "&r=" + r);
}

function getRow(x, y, r, current_time, work_time, result, support) {
    var table = document.getElementById('result_table');
    table.innerHTML += "<tr> <td>"+x+"</td><td>"+y+"</td><td>"+r+"</td><td>"+current_time+"</td><td>"+work_time+"" +
        "</td><td>"+result+"</td><td>"+support+"</td><tr>";
}

function getY() {
    var e = document.getElementById("y");
    return e.options[e.selectedIndex].text;
}

function getR() {
    var R = 0;
    var radio = document.getElementsByName('r');
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked === true) {
            R = radio[i].value;
            break;
        }
    }
    return R;
}

