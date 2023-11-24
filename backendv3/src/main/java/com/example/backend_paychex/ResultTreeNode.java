package com.example.backend_paychex;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ResultTreeNode {
    /*Node for tree along with hashmap for children nodes*/
    private String name;
    private Map<String, ResultTreeNode> children;
    private List<String> path;
    @Setter
    private Object value;

    public ResultTreeNode(String name){
        this.name = name;
        this.children = new HashMap<>();
        this.path = new ArrayList<>();
    }

    public void addChild(ResultTreeNode child){
        children.put(child.getName(), child);
    }
    public ResultTreeNode getChild(String childName) {
        return children.get(childName);
    }

    public void setPath(List<String> path){
        this.path = path;
    }

    public String getPath(){
        return String.join(".", path);
    }

}
