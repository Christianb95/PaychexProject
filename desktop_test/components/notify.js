import React, { useState } from "react";
import { View, Text } from 'react-native';
import {Toast} from "toastify-react-native"

const notify = (message, type)=>{
  toast(message, {position: toast.POSITION.TOP_CENTER, type: type});
}
