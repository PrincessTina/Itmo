import React from "react";
import axios from "axios";

//import Header from "../Components/Header.jsx"
import {addPoint} from "../Components/Redux/Actions.jsx";

export default class StartPage extends React.Component {
    constructor(props) {
        super(props);

        this.testRedux = this.testRedux.bind(this);
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

    testRedux() {
        console.log("Redux store");
        console.log(this.props.store.getState());

        this.props.store.dispatch(addPoint(1, 2));
        console.log(this.props.store.getState());

        this.props.store.dispatch(addPoint(3, 4));
        console.log(this.props.store.getState());
    }

    static getLoginContent() {
        let button = document.getElementsByClassName("w3-button")[0];

        if (button.innerHTML !== "Login") {
            button.innerHTML = "Login";

            document.getElementsByTagName("h5")[0].classList.add("w3-text-pink");
            document.getElementsByTagName("h5")[1].classList.remove("w3-text-pink");
        }
    }

    static getRegContent() {
        let button = document.getElementsByClassName("w3-button")[0];

        if (button.innerHTML !== "Sign in") {
            button.innerHTML = "Sign in";

            document.getElementsByTagName("h5")[1].classList.add("w3-text-pink");
            document.getElementsByTagName("h5")[0].classList.remove("w3-text-pink");
        }
    }

    sendPostCall() {
        let button = document.getElementsByClassName("w3-button")[0];

        if (button.innerHTML === "Sign in") {
            let login = document.getElementsByClassName("_login")[0].value;
            let password = document.getElementsByClassName("_password")[0].value;

            axios.post('/api/users', {
                login: login,
                password: password
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }

    render() {
        return (
            <div>
                <header className="w3-container w3-center w3-padding"
                        style={{borderBottom: 'dashed 3px #e91e63', background: '#1b1520fa'}}>
                    <h2>
                        <p>Powered by Orlova Kristina Aleksandrovna</p>
                        <p>Group P3202, Variant 827</p>
                    </h2>
                </header>

                <div className="w3-card-4 w3-round-large w3-padding"
                     style={{
                         width: '60%',
                         border: '1px dashed #e91e63',
                         marginTop: '9%',
                         marginLeft: '20%',
                         background: '#1b1520fa'
                     }}>
                    <div className="w3-container w3-center">

                        <div className="w3-section w3-padding">
                            <div className="w3-half w3-border-bottom w3-border-pink w3-hover-opacity">
                                <h5 className={"w3-text-pink"} style={{cursor: 'pointer'}}
                                    onClick={StartPage.getLoginContent}>Login</h5>
                            </div>
                            <div className="w3-half w3-border-bottom w3-border-left w3-border-pink w3-hover-opacity">
                                <h5 style={{cursor: 'pointer'}} onClick={StartPage.getRegContent}>Sign in</h5>
                            </div>
                        </div>

                        <div className="w3-section w3-padding"/>

                        <div className="w3-section">
                            <img className="icon" src={"../static/resources/images/icon1.png"}
                                 style={{width: '17px', height: '17px', marginRight: '5px'}}/>
                            <input className="_login" type="text" placeholder="Username" style={{width: '60%'}}/>
                        </div>

                        <div className="w3-section">
                            <img className="icon" src={"../static/resources/images/icon2.png"}
                                 style={{width: '17px', height: '17px', marginRight: '5px'}}/>
                            <input className="_password" type="password" placeholder="Password" style={{width: '60%'}}/>
                        </div>

                        <button className="w3-button w3-pink w3-center" onClick={this.sendPostCall}
                                style={{width: '59%', marginLeft: '2%'}}>Login
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

/*<div>
                <Header />
                <div classNameName="login_form">
                    <div>
                        <div>Login:</div>
                        <input className="_login" type="text" />
                    </div>

                    <button onClick={this.onLoginSubmit} >Sumbit</button>
                    <button onClick={this.testRedux} >TEST REDUX BABY</button>
                </div>
            </div>*/