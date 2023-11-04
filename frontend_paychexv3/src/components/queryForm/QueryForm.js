import React, {useState} from "react";
import {toast} from "react-toastify";
import api from "../../api/axiosConfig";

const QueryForm = (props)=>{
    const [sqlQuery, setQuery] = useState("");
    const [responseInfo, setResponseInfo] = useState();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    const notify = (message, type)=>{
        toast(message, {position: toast.POSITION.TOP_CENTER, type: type});
    }
    const validate = ()=>{
        let result = true;
        console.log(sqlQuery);
        if(sqlQuery===""||sqlQuery===null){
            notify("Query can not be empty", "warning");
            result = false;
        }
        return result
    }
    const querySubmit = async (e) =>{
        e.preventDefault();
        if(validate()){
            try{
                //const response = await api.post("/api/v3/query", {query:sqlQuery});
                notify("Query submitted successfully", "success");
                //const get_response = await api.get("/api/v3/display");
                /*if(get_response!==null){
                    const queryInfo = get_response.data;
                    console.log(queryInfo);
                    setResponseInfo(queryInfo);
                    setIsButtonDisabled(false);
                }*/
                const queryInfo = '{"LOCATION_ID":"1000000","LOCATION_NAME":"California","LOCATION_SHORT_NAME":"CA"}';
                setResponseInfo(queryInfo);
                setIsButtonDisabled(false);
            }catch (error){
                notify(error.response.data, "warning");
            }
        }
    }

    const handleEnableButton = () =>{
        this.setState({isButtonDisabled: false})
    }
    const exportJSON = async (e) =>{
        e.preventDefault();
        console.log("test if not disabled")
        //const response = await api.post("/api/v3/exportJSON", {query:sqlQuery});
        /*if (response!==null){
            console.log(response.data)
            notify("Output file successfully", "success");
        }*/
    }

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
                <pre>{JSON.stringify(responseInfo)}</pre>
            </div>
        </div>
    )
}

export default QueryForm