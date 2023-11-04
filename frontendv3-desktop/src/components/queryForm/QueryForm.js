import React, {useState} from "react";
import {toast} from "react-toastify";
import api from "../../api/axiosConfig";
import {notify} from "../helper"
const { ipcRenderer } = require('electron');

const QueryForm = (props)=>{
    //gets value from set method and applies value to variable
    const [sqlQuery, setQuery] = useState("");
    const [displayInfo, setDisplayInfo] = useState("");
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [fileInfo, setFileInfo] = useState("")

    //checks that the query is not empty when the submit button is pressed
    const validate = ()=>{
        let result = true;
        console.log(sqlQuery);
        if(sqlQuery===""||sqlQuery===null){
            notify("Query can not be empty", "warning");
            result = false;
        }
        return result
    }
    //submits the query by sending it to the query api, and then gets the json string from the display api.
    // Assigns the JSON string, and enables the Export JSON button
    const querySubmit = async (e) =>{
        e.preventDefault();
        if(validate()){
            try{
                const response = await api.post("/api/v3/query", {query:sqlQuery});
                notify("Query submitted successfully", "success");
                const get_response = await api.get("/api/v3/display");
                if(get_response!==null){
                    const queryInfo = get_response.data;
                    setFileInfo(JSON.stringify(queryInfo));
                    setDisplayInfo(JSON.stringify(queryInfo.slice(0, 3)));
                    setIsButtonDisabled(false);
                }
            }catch (error){
                notify(error.response.data, "warning");
            }
        }
    }

    //changes the state of Export JSON button from true to false. Allows button to be used
    const handleEnableButton = () =>{
        this.setState({isButtonDisabled: false})
    }

    //invokes the save dialog box to save the exported file
    const exportJSON = async (e) =>{
        e.preventDefault();
        // let content = "Testing"
        await ipcRenderer.invoke("showDialog", fileInfo);
        notify("File Saved")
    }

    //Changes page state back to Login page
    const exitToLogin = async (e)=>{
        e.preventDefault();
        notify("Exited application", "success");
        props.onPageSwitch("login"); //passes in login, or ternary statement remains false
    }

    return(
        <div>
            <div className="query-form-container">
                <h2> Enter Query</h2>
                <form className="query-form">
                    <label htmlFor="sqlQuery">SQL Query</label>
                    <input value={sqlQuery} onChange={e=>setQuery(e.target.value)}
                           type="sqlQuery" placeholder='enter query' id="sqlQuery" name="sqlQuery"/>
                    <button onClick={querySubmit} type="submit">Submit Query</button>
                    <button disabled={isButtonDisabled} onClick={exportJSON} type="submit">Export JSON</button>
                    <button onClick={exitToLogin} type="submit">Exit To Login</button>
                </form>
            </div>
            <div className="response-container">
                <h2>Response</h2>
                <pre>{displayInfo}</pre>
            </div>
        </div>
    )
}

export default QueryForm
