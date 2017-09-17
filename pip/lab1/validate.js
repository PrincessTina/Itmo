

function checkField() {
    var x = document.getElementById('x');
    var p = parseFloat(x.value);

    if ((!/^-?\d+\.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
        document.getElementById('x_span').innerHTML = "Invalid format";
        document.getElementById('button').disabled = true;
        document.getElementById('tick').innerHTML = "";
    } else if ((p <= -3) || (p >= 3)) {
        document.getElementById('x_span').innerHTML = "The number is out of ODB";
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
        unBlock();
        tab = "tab3";
    }
    document.getElementById(tab).checked = true;
}

function exchangeParameters() {
    var x = document.getElementById('x').value;
    var y = getY();
    var r = getR();
    var arr;
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE ) {
            if (xmlhttp.status === 200) {
                arr = JSON.parse(xmlhttp.responseText);

                document.getElementById("x_table").innerHTML = x;
                document.getElementById("y_table").innerHTML = y;
                document.getElementById("r_table").innerHTML = r;
                document.getElementById("result").innerHTML = arr.result;
                document.getElementById("current_time").innerHTML = arr.current_time;
                document.getElementById("work_time").innerHTML = arr.work_time;
                block();
                document.getElementById("tab4").checked = true;
            }
            else if (xmlhttp.status === 400) {
                alert('There was an error 400');
            }
            else {
                alert('something else other than 200 was returned');
            }
        }
    };

    xmlhttp.open("POST", "main.php", true);
    xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlhttp.send("x=" + x + "&y=" + y + "&r=" + r);
}

function block() {
    document.getElementById('button').hidden = true;
}

function unBlock() {
    document.getElementById('button').hidden = false;
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




