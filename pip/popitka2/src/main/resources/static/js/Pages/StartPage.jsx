import React from "react";
import axios from "axios";

import {setUser} from "../Components/Actions.jsx";
import {addPoint} from "../Components/Actions.jsx";

export default class StartPage extends React.Component {
    constructor(props) {
        super(props);
    }

    getLoginContent() {
        let button = document.getElementsByClassName("w3-button")[0];

        if (button.innerHTML !== "Login") {
            button.innerHTML = "Login";

            document.getElementsByClassName("_login")[0].classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            document.getElementsByClassName("_password")[0].classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            document.getElementsByClassName("_login")[0].title = "";

            document.getElementsByTagName("h5")[0].classList.add("w3-text-pink");
            document.getElementsByTagName("h5")[1].classList.remove("w3-text-pink");
        }
    }

    getRegContent() {
        let button = document.getElementsByClassName("w3-button")[0];

        if (button.innerHTML !== "Sign in") {
            button.innerHTML = "Sign in";

            document.getElementsByClassName("_login")[0].classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
            document.getElementsByClassName("_password")[0].classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

            document.getElementsByTagName("h5")[1].classList.add("w3-text-pink");
            document.getElementsByTagName("h5")[0].classList.remove("w3-text-pink");
        }
    }

    getHashValue(value) {
        let hash = 0;

        if (value.length === 0) return hash;
        for (let i = 0; i < value.length; i++) {
            let char = value.charCodeAt(i);

            hash = ((hash << 5) - hash) + char;
            hash = hash & hash;
        }

        return hash;
    }

    sendPostCall() {
        let login = document.getElementsByClassName("_login")[0];
        let password = document.getElementsByClassName("_password")[0];
        let id;
        let self = this;

        login.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        password.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        login.title = "";

        if (/\s+/.test(document.getElementsByClassName("_password")[0].value) || password.value === "") {
            password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        } else {
            axios.post('/api/users', {
                login: login.value,
                password: self.getHashValue(password.value),
            })
                .then(function (response) {
                    id = response.data._links.user.href.substr(response.data._links.user.href.lastIndexOf("/") + 1);

                    self.setUser(id, login.value);

                    self.goNext(id);
                })
                .catch(function (error) {
                    let message = error.toString();

                    if (message.substr(message.lastIndexOf("code") + 5) === "409") {
                        login.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                        login.title = "This login is already in use"
                    } else {
                        console.log(error);
                    }
                });
        }
    }

    sendGetCall() {
        let login = document.getElementsByClassName("_login")[0];
        let password = document.getElementsByClassName("_password")[0];
        let id;
        let self = this;

        login.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
        password.classList.remove("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");

        axios.get('/api/users/search/findByLogin?login=' + login.value)
            .then(function (response) {
                if (self.getHashValue(password.value).toString() !== response.data.password) {
                    login.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                    password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                } else {
                    id = response.data._links.user.href.substr(response.data._links.user.href.lastIndexOf("/") + 1);

                    self.setUser(id, login.value);

                    self.goNext(id);
                }
            })
            .catch(function (error) {
                let message = error.toString();

                if (message.substr(message.lastIndexOf("code") + 5) === "404") {
                    login.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                    password.classList.add("w3-border-0", "w3-pale-red", "w3-leftbar", "w3-border-red");
                } else {
                    console.log(error);
                }
            });
    }

    //need to delete
    sendPutCall() {
        let login = document.getElementsByClassName("_login")[0];
        let password = document.getElementsByClassName("_password")[0];
        let id = 9; //response.data._links.href.substr(response.data._links.href.lastIndexOf("/") + 1)

        axios.put('/api/users/' + id,
            {
                login: login.value,
                password: password.value
            }
        )
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    checkCall() {
        let button = document.getElementsByClassName("w3-button")[0];

        this.sendPostCall = this.sendPostCall.bind(this);
        this.sendGetCall = this.sendGetCall.bind(this);

        if (button.innerHTML === "Sign in") {
            this.sendPostCall();
        } else {
            this.sendGetCall();
        }
    }

    setUser(id, login) {
        console.log("Redux store");
        console.log(this.props.store.getState());

        this.props.store.dispatch(setUser(id, login));
        this.props.store.dispatch(addPoint(1, 2));
        console.log(this.props.store.getState());
    }

    goNext(id) {
        window.location.href = "/main.html#" + id;
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
                                    onClick={this.getLoginContent}>Login</h5>
                            </div>
                            <div className="w3-half w3-border-bottom w3-border-left w3-border-pink w3-hover-opacity">
                                <h5 style={{cursor: 'pointer'}} onClick={this.getRegContent}>Sign in</h5>
                            </div>
                        </div>

                        <div className="w3-section w3-padding"/>

                        <div className="w3-section">
                            <i className={"fa fa-user w3-text-white"} style={{marginRight: '6px'}}/>
                            <input className="_login" type="text" placeholder="Username" style={{width: '60%'}}/>
                        </div>

                        <div className="w3-section">
                            <i className={"fa fa-key w3-text-white"} style={{marginRight: '2px'}}/>
                            <input className="_password" type="password" placeholder="Password" style={{width: '60%'}}/>
                        </div>

                        <button className="w3-button w3-pink w3-center" onClick={this.checkCall.bind(this)}
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
                    <button onClick={this.setUser} >TEST REDUX BABY</button>
                </div>
            </div>*/