import React from "react";
import axios from "axios";

export default class MainPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="w3-cell-row w3-margin-top">
                <div className="w3-container w3-black w3-cell" style={{width: '45%'}}>
                    <table className="w3-table-all w3-hoverable">
                        <thead>
                        <tr style={{background: '#1b1520fa', color: 'white'}}>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Result</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>Jill</td>
                            <td>Smith</td>
                            <td>50</td>
                        </tr>
                    </table>
                </div>
                <div className="w3-container w3-dark-grey w3-cell w3-cell-middle"
                     style={{width: '10%', backgroundImage: 'url(./images/wallpaper.jpg)'}}/>
                <div className="w3-container w3-blue-grey w3-cell w3-cell-bottom" style={{width: '45%'}}>
                    <p>Hello W3.CSS Layout.</p>
                </div>
            </div>
        );
    }
}