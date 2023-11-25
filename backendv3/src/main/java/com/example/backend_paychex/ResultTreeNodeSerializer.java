package com.example.backend_paychex;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

class ResultTreeNodeSerializer implements JsonSerializer<ResultTreeNode> {
    @Override
    public JsonElement serialize(ResultTreeNode resultTreeNode, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        /*
        Input: root TreeNode to be serialized, type of object, and serialization context
        Output: JsonObject
        Iterates over node and recursively calls serialize function to get serialized form of each child-node,
        until last child is reached. Base case is triggered as only last child node has non-null value in it.
        Adds serialized objects to jsonObject and returns
         */
        if (resultTreeNode.getNodeVal() != null) {
            return jsonSerializationContext.serialize(resultTreeNode.getNodeVal());
        }

        //iterates over hashmap of children for current node, and calls serialize for each child node
        for (Map.Entry<String, ResultTreeNode> entry : resultTreeNode.getChildren().entrySet()) {
            jsonObject.add(entry.getKey(), serialize(entry.getValue(), type, jsonSerializationContext));
        }

        return jsonObject;
    }
}
