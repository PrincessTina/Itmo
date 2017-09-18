

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
        document.getElementById('tick').innerHTML = "*";
        document.getElementById('x_span').innerHTML = "";
        document.getElementById('button').disabled = false;
    }
}

function nextField(id) {
    var tab;

    if (id === 1) {
        tab = "tab2";
    } else {
        unBlock('button');
        tab = "tab3";
    }
    document.getElementById(tab).checked = true;
}

function exchangeParameters() {
    var x = document.getElementById('x').value;
    var y = getY();
    var r = getR();
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE ) {
            if (xmlhttp.status === 200) {
                var arr = JSON.parse(xmlhttp.responseText);

                getRow(arr.x, arr.y, arr.r, arr.current_time, arr.work_time, arr.result);
                unBlock('result_table');
                document.getElementById("tab4").checked = true;
            }
            else if (xmlhttp.status === 400) {
                alert('There is an error 400');
            }
            else {
                alert('Unknown error');
            }
        }
    };

    xmlhttp.open("POST", "main.php", true);
    xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlhttp.send("x=" + x + "&y=" + y + "&r=" + r);
}

function getRow(x, y, r, current_time, work_time, result) {
    var table = document.getElementById('result_table');
    table.innerHTML += "<tr> <td>"+x+"</td><td>"+y+"</td><td>"+r+"</td><td>"+current_time+"</td><td>"+work_time+"" +
        "</td><td>"+result+"</td><tr>";
}

function block(id) {
    document.getElementById(id).hidden = true;
}

function unBlock(id) {
    if (id === "button") {
        document.getElementById(id).hidden = false;
        block('result_table');
    } else {
        document.getElementById(id).hidden = false;
        block('button');
    }

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




