import './App.css';
import Login from "./components/login/Login";
import QueryForm from "./components/queryForm/QueryForm";
import React, {useState} from "react";
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from "react-toastify";

function App() {
    // const [currentForm, setCurrentForm] = useState('login')
  return (
    <div className="App">
        {/*<Login/>*/}
        <QueryForm/>
        <ToastContainer/>
    </div>
  );
}

export default App;
