package com.example.backend_paychex;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TreeNode {
    /*Node for tree along with hashmap for children nodes*/
    private String name;
    private Map<String, TreeNode> children;
    @Setter
    private Object value;

    public TreeNode(String name){
        this.name = name;
        this.children = new HashMap<>();
    }

    public void addChild(TreeNode child){
        children.put(child.getName(), child);
    }
    public TreeNode getChild(String childName) {
        return children.get(childName);
    }

}
