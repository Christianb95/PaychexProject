import './App.css';
import Login from "./components/login/Login";
import QueryForm from "./components/queryForm/QueryForm";
import React, {useState} from "react";
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from "react-toastify";

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const togglePage = (pageName) =>{
    setCurrentPage(pageName);
  }

  return (
      <div className="App">{
        currentPage === "login" ? <Login onPageSwitch = {togglePage}/> : <QueryForm onPageSwitch = {togglePage}/>
      }
        <ToastContainer/>
      </div>
  );
}

export default App;
