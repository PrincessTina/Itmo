import React from "react";
import ReactDOM from "react-dom";

import StartPage from "./Pages/StartPage.jsx";

document.onreadystatechange = () => {
    ReactDOM.render(
        <StartPage />,
        document.getElementsByClassName("react_root")[0]
    );
};