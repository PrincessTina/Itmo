import React from "react";
import axios from "axios";

import ReactDOM from "react-dom";
import Chart from "../Components/PageElements/Chart.jsx";
import {setNullPoints} from "../Components/Redux/Actions.jsx";
import {addPoint} from "../Components/Redux/Actions.jsx";

export default class MainPage extends React.Component {
    constructor(props) {
        super(props);

        this.initStore = this.initStore.bind(this);
        this.redraw = this.redraw.bind(this);
    }

    componentDidUpdate() {
        setTimeout(this.initStore(), 2 * 1000);
    }

    changeRadius() {
        let r = document.getElementsByClassName("_r")[0];
        let value = parseFloat(r.value);
        let self = this;

        r.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

        if (this.validate(r.value) && value > 0 && value <= 3) {
            ReactDOM.render(
                <Chart r={r.value}/>,
                document.getElementsByClassName("chart")[0]
            );

            this.props.store.getState().pointsReducer.points.forEach((point) => {
                let id = point.id;
                let result = self.getResult(point.x, point.y, r.value);

                axios.put('/api/points/' + id,
                    {
                        x: point.x,
                        y: point.y,
                        r: r.value,
                        result: result,
                        userid: self.props.store.getState().userReducer.user.id,
                    }
                )
                    .then(function (response) {
                        console.log(response);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            });

            self.initStore();
        } else {
            r.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        }
    }

    sendPoint() {
        let x = document.getElementsByClassName("_x")[0];
        let y = document.getElementsByClassName("_y")[0];
        let r = document.getElementsByClassName("_r")[0];
        let result;
        let self = this;
        let x_value = parseFloat(x.value);
        let y_value = parseFloat(y.value);
        let condition = true;
        let r_value;

        x.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        y.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        r.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

        if (this.props.store.getState().pointsReducer.points[0] !== undefined) {
            r_value = this.props.store.getState().pointsReducer.points[0].r;
        } else {
            r_value = parseFloat(document.getElementsByClassName("_r")[0].value);

            if (this.validate(r.value) && r_value > 0 && r_value <= 3) {

            } else {
                condition = false;
                r.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            }
        }


        if (this.validate(x.value) && x_value >= -5 && x_value <= 3) {

        } else {
            condition = false;
            x.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        }

        if (this.validate(y.value) && y_value >= -3 && y_value <= 5) {

        } else {
            condition = false;
            y.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        }

        if (condition) {
            result = this.getResult(x_value, y_value, r_value);

            axios.post('/api/points', {
                x: x_value,
                y: y_value,
                r: r_value,
                result: result,
                userid: self.props.store.getState().userReducer.user.id,
            })
                .then(function (response) {
                    self.initStore();
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }

    initStore() {
        let self = this;
        let userid = this.props.store.getState().userReducer.user.id;

        this.props.store.dispatch(setNullPoints());

        axios.get('/api/points/search/findByUserid?userid=' + userid)
            .then(function (response) {
                response.data._embedded.points.forEach((point) => {
                    let id = point._links.point.href.substr(point._links.point.href.lastIndexOf("/") + 1);

                    self.props.store.dispatch(addPoint(id, point.x, point.y, point.r, point.result));
                });

                console.log(self.props.store.getState());

                self.redraw();
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    redraw() {
        let chart = $('.chart').highcharts();

        //redraw chart
        chart.series[3].data = null;

        //redraw table body
        document.getElementsByTagName("tbody")[0].innerHTML = ``;

        //insert table and chart
        this.props.store.getState().pointsReducer.points.forEach((point) => {
            document.getElementsByTagName("tbody")[0].innerHTML += `
            <tr class="w3-hover-pink">
                   <td>${point.x}</td>
                   <td>${point.y}</td>
                   <td>${point.r}</td>
                   <td>${point.result}</td>
               </tr>
            `;

            if (point.result === "-") {
                chart.series[3].color = '#e91e63';
            } else {
                chart.series[3].color = 'black';
            }
            chart.series[3].addPoint([point.x, point.y]);
        });
    }

    getResult(x, y, r) {
        if ((x >= -r && x <= 0 && y >= -r && y <= 0) || (x ^ 2 + y ^ 2 <= r ^ 2 && x >= -r && x <= 0 && y >= 0 && y <= r) ||
            (y <= (r - x) / 2 && y >= 0 && y <= r / 2 && x >= 0 && x <= r)) {
            return "+";
        } else {
            return "-";
        }
    }

    validate(value) {
        return /^-?\d+\.?\d*$/.test(value);
    }

    createGraph(r) {
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
                data: [[0, r / 2], [r, 0]],
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
        return (
            <div className="w3-container" style={{padding: '0'}}>
                <div className="w3-container w3-half table"
                     style={{
                         padding: '0',
                         height: '100%',
                         borderRight: '6px solid rgba(27, 21, 32, 0.98)',
                         overflow: 'auto'
                     }}>
                    <table className="w3-table-all w3-hoverable" style={{textAlignLast: 'center', border: '0'}}>
                        <thead>
                        <tr style={{background: '#1b1520fa', color: 'white'}}>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Result</th>
                        </tr>
                        </thead>
                        <tbody/>
                    </table>
                </div>
                <div className="w3-container w3-rest" style={{verticalAlign: 'top', padding: '0'}}>
                    <div className={"chart"}/>

                    <div className="w3-container w3-padding w3-margin">
                        <input type="text"
                               className="_r w3-padding-small w3-margin w3-animate-input w3-border w3-border-black"
                               placeholder="Input r (0; 3]" style={{width: '46%'}}/>
                        <button className="w3-button w3-pink w3-center" style={{width: '40%', marginLeft: '33%'}}
                                onClick={this.changeRadius.bind(this)}>Change radius
                        </button>
                        <div className="w3-black w3-margin-top" style={{width: '100%', height: '2px'}}/>
                        <input type="text"
                               className="_x w3-margin w3-border w3-border-black w3-animate-input w3-padding-small"
                               placeholder="Input x [-5; 3]" style={{width: '46%'}}/>
                        <input type="text"
                               className="_y w3-margin w3-border w3-border-black w3-animate-input w3-padding-small"
                               placeholder="Input y [-3; 5]" style={{width: '46%'}}/>
                        <button className="w3-button w3-pink w3-center checkButton"
                                style={{width: '40%', marginLeft: '33%'}}
                                onClick={this.sendPoint.bind(this)}>Check point
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}