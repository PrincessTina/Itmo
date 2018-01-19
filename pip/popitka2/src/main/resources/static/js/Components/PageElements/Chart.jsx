import React from "react";

export default class Chart extends React.Component {
    constructor(props) {
        super(props);
    }

    createGraph() {
        let r = this.props.r;

        let chart = {
            events: {
                'click': function (e) {
                    document.getElementsByClassName("_x")[0].value = e.xAxis[0].value.toFixed(3);
                    document.getElementsByClassName("_y")[0].value = e.yAxis[0].value.toFixed(3);
                    document.getElementsByClassName("checkButton")[0].click()
                }
            }
        };

        let xAxis = {
            maxZoom: 7,
            gridLineWidth: 1
        };

        let yAxis = {
            maxZoom: 7
        };

        let legend = {
            enabled: false
        };

        let title = {
            text: ""
        };

        let series = [
            {
                marker: {
                    enabled: false
                },
                color: 'pink',
                type: 'area',
                data: [[-r, 0], [-r, -r], [0, -r]],
                enableMouseTracking: false
            }, {
                marker: {
                    enabled: false
                },
                color: 'pink',
                type: 'area',
                data: [[0, r/2], [r, 0]],
                enableMouseTracking: false
            }, {
                marker: {
                    enabled: false
                },
                color: 'pink',
                type: 'areaspline',
                data: [[-r, 0], [-r * Math.sqrt(3 / 4), r / 2], [-r / 2, r * Math.sqrt(3 / 4)], [0, r]],
                enableMouseTracking: false
            }, {
                color: '#1b1520fa',
                marker: {
                    symbol: 'diamond'
                },
                type: 'scatter'
            }
        ];

        let json = {};
        json.chart = chart;
        json.title = title;
        json.legend = legend;
        json.xAxis = xAxis;
        json.yAxis = yAxis;
        json.series = series;

        $('.chart').highcharts(json);
    }

    render() {
        this.createGraph();

        return null;
    }
}