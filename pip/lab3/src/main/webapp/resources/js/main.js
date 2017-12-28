window.onload = function () {
    var button = $("button[id$='button2']")[0];
    button.disabled = true;
    button.classList.add("ui-state-disabled");

    update(true);

    window.setInterval(
        function () {
            $("input[id$='y']")[0].readOnly = true;
        }, 400);
};

var nonCorrect = false;

function update(onLoad) {
    if (!nonCorrect || onLoad) {
        createGraph();

        if ($('#point_table_data').find("tr")[0].children[0].innerHTML !== "No records found.") {
            var points = getTablePoints();

            if (points[0].r !== parseFloat($("input[id$='r_input']")[0].value)) {
                $("button[id$='button3']")[0].click();
            }

            for (var i = 0; i < points.length; i++) {
                var point = points[i];

                addNewPoint(point.x, point.y, point.result);
            }
        }
    }
}

function finish() {
    var points = getTablePoints();

    alert(points[0].r);

    for (var i = 0; i < points.length; i++) {
        var point = points[i];

        addNewPoint(point.x, point.y, point.result);
    }
}

function checkParams() {
    var x = $("input[id$='x']")[0];
    var p = parseFloat(x.value);

    if ((!/^-?\d+\.?\d*$/.test(x.value.trim())) || (x.value.trim() === "")) {
        x.style.border = "1.1px solid #c70417";
        nonCorrect = true;
    } else if ((p <= -3) || (p >= 5)) {
        x.style.border = "1.1px solid #c70417";
        nonCorrect = true;
    } else {
        x.style.border = "1.1px solid #EFEFEF";
        nonCorrect = false;
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

function createGraph() {
    var r = parseFloat($("input[id$='r_input']")[0].value);
    var button = $("button[id$='button2']")[0];
    button.classList.remove("ui-state-disabled");
    button.disabled = false;

    var chart = {
        events: {
            'click': function (e) {
                var x = e.xAxis[0].value,
                    y = e.yAxis[0].value;

                //series = this.series[3];
                //series.addPoint([x, y]);

                $("input[id$='x']")[0].value = x.toFixed(3);
                checkParams();
                $("input[id$='y']")[0].readOnly = false;
                $("input[id$='y']")[0].value = y.toFixed(3);
                $("button[id$='button2']")[0].click();
                $("input[id$='y']")[0].readOnly = true;
            }
        }
    };

    var xAxis = {
        maxZoom: 7,
        gridLineWidth: 1
    };

    var yAxis = {
        maxZoom: 7
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
            data: [[0, r / 2], [r, r / 2], [r, 0]],
            enableMouseTracking: false
        }, {
            marker: {
                enabled: false
            },
            color: '#4d1f80',
            type: 'area',
            data: [[-r / 2, 0], [0, -r / 2]],
            enableMouseTracking: false
        }, {
            marker: {
                enabled: false
            },
            color: '#4d1f80',
            type: 'areaspline',
            data: [[-r, 0], [-r * Math.sqrt(3 / 4), r / 2], [-r / 2, r * Math.sqrt(3 / 4)], [0, r]],
            enableMouseTracking: false
        }, {
            marker: {
                symbol: 'diamond'
            },

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

function getTablePoints() {
    var rows = $('#point_table_data').find("tr");
    var points = [];


    for (var i = 0; i < rows.length; i++) {
        points[i] = {
            x: parseFloat(rows[i].children[0].innerHTML),
            y: parseFloat(rows[i].children[1].innerHTML),
            r: parseFloat(rows[i].children[2].innerHTML),
            result: rows[i].children[3].innerHTML
        }
    }

    return points;
}
