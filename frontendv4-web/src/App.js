import './App.css';
import Login from "./components/login/Login";
import QueryForm from "./components/queryForm/QueryForm";
import React, {useState} from "react";
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from "react-toastify";
import GithubForm from "./components/GithubOverlay/GithubForm";

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [gitOpen, setGitOpen] = useState(false)
  const togglePage = (pageName) =>{
    setCurrentPage(pageName);
  }
  const openModal = (e)=> {
      if (e)
        e.preventDefault();
      setGitOpen(true)
  }

  const closeModal = () =>{
      setGitOpen(false)
  }

  return (
      <div className="App">{
        currentPage === "login" ? <Login onPageSwitch = {togglePage}/> : <QueryForm onPageSwitch = {togglePage}
                                                                                    openGitModal = {openModal}
                                                                                    closeGitModal = {closeModal}/>
      }
          <GithubForm isOpen={gitOpen} onClose={()=>setGitOpen(false)}>
          </GithubForm>
        <ToastContainer/>
      </div>
  );
}

export default App;
