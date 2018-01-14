import React from "react";
import axios from "axios";

import Header from "../Components/Header.jsx"

export default class StartPage extends React.Component {
    constructor(props) {
        super(props);
    }

    onLoginSubmit() {
        let login = document.getElementsByClassName("_login")[0].value;

        axios.post(`/popitka2-1.0-SNAPSHOT/users?userName=${login}`)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                <Header />
                <div className="login_form">
                    <div>
                        <div>Login:</div>
                        <input className="_login" type="text" />
                    </div>

                    <button onClick={this.onLoginSubmit} >Sumbit</button>
                </div>
            </div>
        );
    }
}
