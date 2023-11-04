import axios from "axios"

//api URL
export default axios.create({
        baseURL:"http://localhost:8080"
        //Can also try using ngrok
    }
)
