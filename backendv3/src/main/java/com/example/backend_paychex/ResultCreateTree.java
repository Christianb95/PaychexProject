package com.example.backend_paychex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;

class ResultCreateTree {
    private ResultTreeNode root;

    public ResultCreateTree() {
        /*Adds root node when create tree is called*/
        this.root = new ResultTreeNode("root");
    }

    public void addPath(String colName, Object value) throws Exception {
        /*
        Input: String path of Result set column name, Object value for the value of the cell in the result set
        Output: None
        Adds column name as tree node. If column name contains '.', breaks into multiple names, and each name is child-node
        of the previous name. Ex: name1.name3 will be name1 and child-node name3. Last child-node is assigned the value.
         */
        List<String> names = List.of(colName.split("\\."));

        ResultTreeNode currentNode = root;
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            //checks to see if current node is in tree and adds child if not
            if (!currentNode.getChildren().containsKey(name)) {
                ResultTreeNode newNode = new ResultTreeNode(name);
                newNode.setPath(names.subList(0, i+1));
                currentNode.addChild(newNode);
                if (i == names.size()-1){
                    newNode.setValue(value);
                }
            } else if(currentNode.getChild(name) != null && names.size() == 1) { //check if this node is in the tree,
                                                                                // and the len of this list is 1
                String message = String.format("Can't have %s and %s as field names in the same query", colName, currentNode.getChild(name).getName());
                throw new Exception(message);

            } else if (Objects.equals(currentNode.getChild(name).getPath(), colName)) {//check if path and column
                                                                                        // name is duplicate
                String message = String.format("Can't have %s and %s as field names in the same query", colName, currentNode.getChild(name).getPath());
                throw new Exception(message);

            }else if(currentNode.getChild(name).getValue() != null){ //check if this node is in the tree,
                                                                    // and already has a value associated with it
                String message = String.format("Can't have %s and %s as field names in the same query",currentNode.getChild(name).getName(), colName);
                throw new Exception(message);

            }
            //Gets child-node from map, and sets current node as retrieved child-node
            currentNode = currentNode.getChildren().get(name);

        }
    }

    public String toJson(){
        /*
        Input: None
        Output: jsonObject
        Tree to Json Object using type adapter to customize serialization. Changes example output from:
         {
    "name": "root",
    "children": {
      "LOCATION_ID": {
        "name": "LOCATION_ID",
        "children": {},
        "object": 1000000
      },

      to desired output:
    [
      {
        "LOCATION_ID": 1000000,
        "location": {
          "name": "CA"
        },
         */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ResultTreeNode.class, new ResultTreeNodeSerializer())
                .setPrettyPrinting()
                .create();
        return gson.toJson(root);
    }
}
