import React, {useState} from "react";
import {toast} from "react-toastify";
import api from "../../api/axiosConfig";

const QueryForm = ()=>{
    const [sqlQuery, setQuery] = useState("")
    const notify = (message, type)=>{
        toast(message, {position: toast.POSITION.TOP_CENTER, autoClose: false, type: type});
    }
    const validate = ()=>{
        let result = true
        console.log(sqlQuery)
        if(sqlQuery===""||sqlQuery===null){
            notify("Query can not be empty", "warning")
            result = false;
        }
        return result
    }
    const handleSubmit = async (e) =>{
        console.log("Here")
        e.preventDefault();
        if(validate()){
            try{
                const response = await api.post("/api/v3/query", {query:sqlQuery})
                notify("Query submitted successfully", "success")
            }catch (error){
                notify(error.response.data, "warning")
            }
        }
    }
    return(
        <div className="query-form-container">
            <h2> Enter Query</h2>
            <form className="query-form" onSubmit={handleSubmit}>
                <label htmlFor="sqlQuery">SQL Query</label>
                <input value={sqlQuery} onChange={e=>setQuery(e.target.value)}
                       type="sqlQuery" placeholder='enter query' id="sqlQuery" name="sqlQuery"/>
                <button type="submit">Submit Query</button>
            </form>
        </div>
    )
}

export default QueryForm