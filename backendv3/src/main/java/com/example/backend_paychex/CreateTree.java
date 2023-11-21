package com.example.backend_paychex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class CreateTree {
    private TreeNode root;

    public CreateTree() {
        /*Adds root node when create tree is called*/
        this.root = new TreeNode("root");
    }
    public void addPath(String colName, Object value) {
        /*
        Input: String path of Result set column name, Object value for the value of the cell in the result set
        Output: None
        Adds column name as tree node. If column name contains '.', breaks into multiple names, and each name is child-node
        of the previous name. Ex: name1.name3 will be name1 and child-node name3. Last child-node is assigned the value.
         */
        String[] names = colName.split("\\.");

        TreeNode currentNode = root;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            //checks to see if current node is in tree and adds child if not
            if (!currentNode.getChildren().containsKey(name)) {
                TreeNode newNode = new TreeNode(name);
                currentNode.addChild(newNode);

                if (i == names.length-1){
                    newNode.setValue(value);
                }
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

      to
    [
      {
        "LOCATION_ID": 1000000,
        "location": {
          "name": "CA"
        },
         */
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(TreeNode.class, new TreeNodeSerializer())
                .setPrettyPrinting()
                .create();
        return gson.toJson(root);
    }
}
