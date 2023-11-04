import React, { useState } from "react";
import { View, Text } from 'react-native';
import { Button, TextInput } from "react-native-windows";
import api from "../api/axiosConfig"
import { useNavigation } from '@react-navigation/native';

const Login = (props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [dbURL, setDBURL] = useState("");
  const navigation = useNavigation();

  const validate = ()=>{
    let result = true;
    console.log("Validate! Woohoo!");
    if (username==='' || username===null){
      result = false;
      console.log("username false");
    }
    if (password==="" || password===null) {
      result = false;
    }
    if (dbURL==="" || dbURL===null) {
      result = false;
    }
    return result;
  }

  const handleSubmit = async (e)=>{
    console.log("In handleSubmit");
    e.preventDefault();
    if (validate()){
      try{
        const response = await api.post("/api/v3/login", {username:username, password:password, databaseURL:dbURL})
        if (response.data) {
          console.log("API! API!")
          console.log(response.data)
          navigation.navigate("Query")
        }
      } catch(error){
        console.error("Error: ", error)
      }
    }
  }


  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Text>Username: </Text>
      <TextInput value={username} onChangeText={(text) =>setUsername(text)} placeholder='username' id='username'/>
      <Text>Password: </Text>
      <TextInput value={password} onChangeText={(text) =>setPassword(text)} placeholder='**********' secureTextEntry={true} id='password'/>
      <Text>Database URL</Text>
      <TextInput value={dbURL} onChangeText={(text) =>setDBURL(text)} placeholder='jdbc:oracle:thin:@localhost:1521:xe' id='password'/>
      <Button onPress={handleSubmit} title="test"/>
    </View>
  );
}
export default Login;
