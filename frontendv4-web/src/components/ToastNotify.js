import {toast} from "react-toastify";


let notify;
export default notify = (message, type)=>{
    toast(message, {position: toast.POSITION.TOP_CENTER, type: type});
}
