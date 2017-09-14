function check_fields(x) {
    var p = parseFloat(x.value);

    if ((/[a-z]+/.test(x.value)) || (x.value === "")) {
        var spanEl = document.getElementById('x_span');
        spanEl.innerHTML = "Неверный формат числа";

    } else if ((p <= -3) || (p >= 3)) {
        var spanEl = document.getElementById('x_span');
        spanEl.innerHTML = "Число выходит за ОДБ";
    } else {
        console.log(p);
    }
}

function returnField() {
    var spanEl = document.getElementById('x_span');
    spanEl.htmlText("");
}


