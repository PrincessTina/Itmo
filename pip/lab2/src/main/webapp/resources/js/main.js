function hello() {
    var table = document.getElementById('result_table');
    table.innerHTML += "<tr> <td>" + 1 + "</td><td>" + 1 + "</td><td>" + 1 + "</td><td>" + 1 + "</td><td>" + 1 + "" +
        "</td><td>" + 1 + "</td><td>" + 1 + "</td><tr>";
}

var isGraphCreated = false;

function addNewPoint() {
    var x = parseFloat(document.getElementById('x').value);
    var select = document.getElementById('y');
    var y = parseFloat(select.options[select.selectedIndex].value);
    var chart = $('#container').highcharts();

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

function createGraph() {
    isGraphCreated = true;
    var r = parseFloat(document.getElementById('r').value);

    var chart = {
        events: {
            'click': function (e) {
                // find the clicked values and the series
                var x = e.xAxis[0].value,
                    y = e.yAxis[0].value,
                    series = this.series[3];
                // Add it
                series.addPoint([x, y]);
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
            color: 'black',
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