import React from "react";
import ReactDOM from "react-dom";

import Store from "../Components/Redux/Store.jsx";
import StartPage from "../Pages/StartPage.jsx";

document.onreadystatechange = () => {
    ReactDOM.render (
        <StartPage store={Store} />,
        document.getElementsByClassName("react_root")[0]
    );
};