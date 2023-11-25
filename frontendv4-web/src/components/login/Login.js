import React, {useState, useEffect} from "react";
import api from "../../api/axiosConfig"
import logoImage from "../../Assets/Paychex_logo.svg.png"; // Import your image file
import notify from "../../components/ToastNotify"

const Login = (props) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [dbURL, setDBURL] = useState("");

    const validate=()=>{
        //checks if username, password, and dbURL have been entered. Returns pop-up notification if empty or null
        let result = true;
        if (username==="" || username===null){
            result=false;
            notify("Please enter a username", "warning");
        }
        if (password==="" || password===null) {
            result = false;
            notify("This is a React Toastify Notification!", "warning");
        }
        if (dbURL==="" || dbURL===null) {
            result = false;
            notify("Please enter the Database URL", "warning");
        }
        return result
    }

    const encrypt = (data) => {
        const shift = 3;
        let encryptedData = '';

        for (let i = 0; i < data.length; i++) {
            let c = data.charAt(i);

            if (c.match(/[a-z]/i)) {
                const isUpperCase = c === c.toUpperCase();
                const offset = isUpperCase ? 'A'.charCodeAt(0) : 'a'.charCodeAt(0);
                console.log((c.charCodeAt(0) - offset + shift) % 26 + offset);
                encryptedData += String.fromCharCode(offset + (c.charCodeAt(0) + shift - offset) % 26);
            } else {
                encryptedData += c;
            }
        }
        return encryptedData;
    }

    const handleSubmit = async (e) =>{
        e.preventDefault();
        if(validate()){
            try{
                console.log(password);
                const response = await api.post("/api/v3/login", {username:username, password:encrypt(password), databaseURL:dbURL});
                notify("Log in to database successful!", "success");
                props.onPageSwitch(); //does not need argument passed in due to ternary statement, see App.js
            }catch (error){
                notify(error.response.data, "warning");
            }
        }
    }


    return (
        <div className="login-form-container">
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
            <h2> Login </h2>
            <form className="login-form" onSubmit={handleSubmit}>
                <label htmlFor="username">username</label>
                <input value={username} onChange={e=>setUsername(e.target.value)} type="username" placeholder='username' id="username" name="username"/>
                <label htmlFor="password">password</label>
                <input value={password} onChange={e=>setPassword(e.target.value)} type="password" placeholder='**********' id="password" name="password"/>
                <label htmlFor="dbURL">Database URL</label>
                <input value={dbURL} type="dbURL" onChange={e=>setDBURL(e.target.value)} placeholder='Ex: jdbc:oracle:thin:@<dbhost>:<dbport>:<sid>' id="dbURL" name="dbURL"/>
                <button type="submit">Login</button>
            </form>
        </div>
    )
}

export default Login
