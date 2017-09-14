function check_field() {
    var x = document.getElementById('x');
    var p = parseFloat(x.value);

    if ((!/^-?\d+.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
        document.getElementById('x_span').innerHTML = "Неверный формат числа";
    } else if ((p <= -3) || (p >= 3)) {
        document.getElementById('x_span').innerHTML = "Число выходит за ОДБ";
    } else {
        console.log(p);
        document.getElementById('tick').innerHTML = "*";
    }
}

function returnField() {
    document.getElementById('x_span').innerHTML = "";
    document.getElementById('tick').innerHTML = "";
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

function getY() {
    var e = document.getElementById("y");
    return e.options[e.selectedIndex].text;
}

function exchangeParameters() {
    var x = document.getElementById('x');
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE ) {
            if (xmlhttp.status === 200) {
                document.getElementById("x_table").innerHTML = xmlhttp.responseText;
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
    xmlhttp.send("x=" + x.value + "&y=" + getY() + "&r=" + getR());
}



