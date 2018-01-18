import React from "react";
import axios from "axios";
import ReactDOM from "react-dom";

import Store from "../Components/Reducer.jsx";
import StartPage from "../Pages/StartPage.jsx";
import MainPage from "../Pages/MainPage.jsx";

import {setUser} from "../Components/Actions.jsx";

document.onreadystatechange = () => {
    let index = window.location.href.lastIndexOf("#") + 1;
    let store = Store;

    if (index !== 0) {
        let id = window.location.href.substr(index);

        axios.get('/api/users/' + id)
            .then(function (response) {
                store.dispatch(setUser(id, response.data.login));

                checkPage(store);
            })
            .catch(function (error) {
                console.log(error);
            });
    } else {
        checkPage(store);
    }
};

function checkPage(store) {
    if (window.location.href.lastIndexOf("/index.html") !== - 1) {
        ReactDOM.render (
            <StartPage store={store}/>,
            document.getElementsByClassName("react_root")[0]
        );
    } else {
        ReactDOM.render (
            <MainPage store={store}/>,
            document.getElementsByClassName("react_root")[0]
        );
    }
}