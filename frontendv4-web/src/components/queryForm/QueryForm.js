import React, {useState} from "react";
import {toast} from "react-toastify";
import api from "../../api/axiosConfig";
import logoImage from "../../Assets/Paychex_logo.svg.png";

const QueryForm = (props)=>{
    const [sqlQuery, setQuery] = useState("");
    const [responseInfo, setResponseInfo] = useState();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [showResponse, setShowResponse] = useState(true);

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
                const response = await api.post("/api/v3/query", {query:sqlQuery});
                notify("Query submitted successfully", "success");
                const get_response = await api.get("/api/v3/display");
                if(get_response!==null){
                    const queryInfo = get_response.data;
                    setResponseInfo(queryInfo);
                    setShowResponse(true);
                    setIsButtonDisabled(false);
                }
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
        api.get("/api/v3/exportJSON", {responseType: 'blob'})
            .then((response) => {
            const blob = new Blob([response.data], {type: 'application/json'});
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'output.json';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            }).catch((error) =>{
                notify(error.message);
        });
    }

    const exitToLogin = async (e)=>{
        e.preventDefault();
        notify("Exited application", "success");
        props.onPageSwitch("login"); //passes in login, or ternary statement remains false
    }

    return(
        <div style={{ display: "flex" }}>
            <div className="query-form-container" style={{ flex: 1 }}>
                <img
                    src={logoImage}
                    alt="Logo"
                    style={{
                        display: "inline-block",
                        width: "200px",
                        height: "auto",
                        position: "absolute",
                        top: "0",
                        right: "0",
                    }}
                />
                <h2> Enter SQL Query</h2>
                <form className="query-form">
                    <textarea value={sqlQuery} onChange={e=>setQuery(e.target.value)}
                           type="sqlQuery" placeholder='enter query' id="sqlQuery" name="sqlQuery"/>
                    <button onClick={querySubmit} type="submit">Submit Query</button>
                    <button disabled={isButtonDisabled} onClick={exportJSON} type="submit">Export JSON</button>
                    <button onClick={exitToLogin} type="submit">Exit To Login</button>
                </form>
            </div>
            {showResponse && (
                <div className="response-form" style={{ flex: 1 }}>
                    <h2>Response</h2>
                    <div className="response-container" style={{ flex: 1 }}>
                        <text><center><i>First 5 Results</i></center></text>
                        <pre>
                        {JSON.stringify(responseInfo, null, 2)}
                        </pre>
                    </div>
                </div>
            )}
        </div>
    )
}

export default QueryForm
