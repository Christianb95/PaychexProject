import './App.css';
import Login from "./components/login";
import React, {useState} from "react";
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from "react-toastify";

function App() {
    const [currentForm, setCurrentForm] = useState('login')
  return (
    <div className="App">
      <Login/>
        <ToastContainer/>
    </div>
  );
}

export default App;
