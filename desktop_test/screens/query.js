import React, { useState } from "react";
import { View, Text } from 'react-native';
import { Button, TextInput } from "react-native-windows";
import api from "../api/axiosConfig"
import { useNavigation } from '@react-navigation/native';
// import DocumentPicker from 'react-native-document-picker';
// import * as RNFS from "react-native-fs";

const Query = (props) => {
  const [sqlQuery, setQuery] = useState("");
  const [responseInfo, setResponseInfo] = useState();
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const navigation = useNavigation();

  const validate = ()=>{
    let result = true;
    console.log(sqlQuery);
    if(sqlQuery===""||sqlQuery===null){
      result = false;
    }
    return result
  }

  const querySubmit = async (e) =>{
    if(validate()){
      try{
        const response = await api.post("/api/v3/query", {query:sqlQuery});
        const get_response = await api.get("/api/v3/display");
        if(get_response!==null){
          const queryInfo = get_response.data;
          console.log(queryInfo);
          setResponseInfo(queryInfo);
          setIsButtonDisabled(false);
        }
      }catch (error){
        console.log(error);
      }
    }
  }

  const handleEnableButton = () =>{
    this.setState({isButtonDisabled: false})
  }

  const exportJSON = async () =>{
    console.log("test if not disabled")
    try{
      // const file = {type: DocumentPicker.types.allFiles, name:"myData.json",
      //   fileCopyURI:'file://' + RNFS.DocumentDirectoryPath + '/myFile.json',
      // }
      // await RNFS.writeFile(file.fileCopyURI, content, 'utf8');
    }catch(error){
      console.log(error)
    }
  }

  const exitToLogin = ()=>{
    console.log("Exit To Login pushed")
    try {
      navigation.navigate("Login"); //passes in login, or ternary statement remains false
    }catch (error){
      console.log(error)
    }
  }

  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Text>Enter Query</Text>
      <TextInput value={sqlQuery} onChangeText={(text)=>setQuery(text)} placeholder='Enter Query' id="sqlQuery"/>
      <Button onPress={querySubmit} title="Submit Query"/>
      <Button onPress={exportJSON} title="Export to JSON" disabled={isButtonDisabled}/>
      <Button onPress={exitToLogin} title="Exit To Login"/>
    </View>
  );
}
export default Query;
