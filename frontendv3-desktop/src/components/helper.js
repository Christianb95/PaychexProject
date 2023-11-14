import {toast} from "react-toastify";

export const notify = (message, type)=>{
    toast(message, {position: toast.POSITION.TOP_CENTER, type: type});
}
