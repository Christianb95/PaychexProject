package com.example.backend_paychex;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

class TreeNodeSerializer implements JsonSerializer<TreeNode> {
    @Override
    public JsonElement serialize(TreeNode treeNode, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        //checks node for Object value, and serializes if exists
        if (treeNode.getValue() != null) {
            return jsonSerializationContext.serialize(treeNode.getValue());
        }

        //iterates over
        for (Map.Entry<String, TreeNode> entry : treeNode.getChildren().entrySet()) {
            jsonObject.add(entry.getKey(), serialize(entry.getValue(), type, jsonSerializationContext));
        }

        return jsonObject;
    }
}
