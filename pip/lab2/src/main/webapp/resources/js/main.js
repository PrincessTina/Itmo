var isGraphCreated = false;

function update(condition, str, r) {
    if (condition) {
        // graph's part
        document.getElementById('r').value = r;
        createGraph(r);

        var table = document.getElementById('result_table');
        var array = str.split(";");

        for (var i = 0; i < array.length; i++) {
            var point = array[i].split(",");

            addNewPoint(point[0], point[1], point[3]);
            table.innerHTML += "<tr><td>" + point[0] + "</td><td>" + point[1] + "</td><td>" + point[2] + "</td><td>" + point[3];
        }
    }
}

function resizeGraph(condition, str) {
    var r = parseFloat(document.getElementById('r').value);

    if ((/^-?\d+\.?\d*$/.test(r)) && (r > 1) && (r < 4)) {
        createGraph(r);

        if (condition) {
            var array = str.split(";");

            for (var i = 0; i < array.length; i++) {
                var point = array[i].split(",");

                addNewPoint(point[0], point[1], point[3]);
            }
        }
    } else {
        alert("Don't worry. Try again");
    }
}

function addNewPoint(x, y, condition) {
    var chart = $('#container').highcharts();
    x = parseFloat(x);
    y = parseFloat(y);

    if (condition === "Point is in the scope") {
        chart.series[3].color = 'pink';
    } else {
        chart.series[3].color = 'black';
    }
    chart.series[3].addPoint([x, y]);
}

function checkParams(a) {
    var x = document.getElementById(a);
    var p = parseFloat(x.value);

    switch (a) {
        case 'x':
            if ((!/^-?\d+\.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
                document.getElementById('x_span').innerHTML = "Invalid format";
                document.getElementById('button2').disabled = true;
                document.getElementById('x_tick').innerHTML = "";
            } else if ((p <= -5) || (p >= 5)) {
                document.getElementById('x_span').innerHTML = "The number is out of ODZ";
                document.getElementById('button2').disabled = true;
                document.getElementById('x_tick').innerHTML = "";
            } else {
                document.getElementById('x_tick').innerHTML = "Correct data";
                document.getElementById('x_span').innerHTML = "";

                if (isGraphCreated) {
                    document.getElementById('button2').disabled = false;
                }
            }
            break;
        case 'r':
            if ((!/^-?\d+\.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
                document.getElementById('r_span').innerHTML = "Invalid format";
                document.getElementById('button1').disabled = true;
                document.getElementById('r_tick').innerHTML = "";
            } else if ((p <= 1) || (p >= 4)) {
                document.getElementById('r_span').innerHTML = "The number is out of ODZ";
                document.getElementById('button1').disabled = true;
                document.getElementById('r_tick').innerHTML = "";
            } else {
                document.getElementById('r_tick').innerHTML = "Correct data";
                document.getElementById('r_span').innerHTML = "";
                document.getElementById('button1').disabled = false;
            }
            break;
    }
}

function createGraph(R) {
    var r;
    isGraphCreated = true;

    if (R === null) {
        r = parseFloat(document.getElementById('r').value);
    } else {
        r = parseFloat(R);
    }

    var chart = {
        events: {
            'click': function (e) {
                var x = e.xAxis[0].value,
                    y = e.yAxis[0].value;

                document.location.href = 'main?r=' + r + '&x=' + x + '&y=' + y;
            }
        }
    };

    var xAxis = {
        maxZoom: 10,
        gridLineWidth: 1
    };

    var yAxis = {
        maxZoom: 10
    };

    var legend = {
        enabled: false
    };

    var title = {
        text: ""
    };

    var series = [
        {
            marker: {
                enabled: false
            },
            color: '#4d1f80',
            type: 'area',
            data: [[0, -r], [r, -r], [r, 0]],
            enableMouseTracking: false
        }, {
            marker: {
                enabled: false
            },
            color: '#4d1f80',
            type: 'area',
            data: [[0, r], [r, 0]],
            enableMouseTracking: false
        }, {
            marker: {
                enabled: false
            },
            color: '#4d1f80',
            type: 'areaspline',
            data: [[-r, 0], [-r * Math.sqrt(3 / 4), -r / 2], [-r / 2, -r * Math.sqrt(3 / 4)], [0, -r]],
            enableMouseTracking: false
        }, {
            marker: {
                symbol: 'diamond'
            },
            //colors: ['red', 'green'],
            type: 'scatter'
        }
    ];

    var json = {};
    json.chart = chart;
    json.title = title;
    json.legend = legend;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.series = series;

    $('#container').highcharts(json);
}