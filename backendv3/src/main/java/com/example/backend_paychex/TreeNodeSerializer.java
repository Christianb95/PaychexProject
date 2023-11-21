package com.example.backend_paychex;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

class TreeNodeSerializer implements JsonSerializer<TreeNode> {
    @Override
    public JsonElement serialize(TreeNode treeNode, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        /*
        Input: root TreeNode to be serialized, type of object, and serialization context
        Output: JsonObject
        Iterates over node and recursively calls serialize function to get serialized form of each child-node,
        until last child is reached. Base case is triggered as only last child node has non-null value in it.
        Adds serialized objects to jsonObject and returns
         */
        if (treeNode.getValue() != null) {
            return jsonSerializationContext.serialize(treeNode.getValue());
        }

        //iterates over node and serializes child-node
        for (Map.Entry<String, TreeNode> entry : treeNode.getChildren().entrySet()) {
            jsonObject.add(entry.getKey(), serialize(entry.getValue(), type, jsonSerializationContext));
        }

        return jsonObject;
    }
}
