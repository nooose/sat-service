import React, {useEffect} from "react";
import Home from "./components/Home";
import {BrowserRouter, Route, Router, Routes} from "react-router-dom";
import Login from "./components/Login";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>} />
                <Route path="/login-success" element={<Login/>} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
