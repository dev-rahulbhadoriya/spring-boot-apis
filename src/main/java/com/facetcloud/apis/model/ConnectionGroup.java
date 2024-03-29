package com.facetcloud.apis.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "connection_group")
public class ConnectionGroup {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @OneToMany(mappedBy = "connectionGroup", cascade = CascadeType.ALL)
    @JsonManagedReference 
    private List<VirtualNode> nodes = new ArrayList<>();

    public void addNode(VirtualNode node) {
        nodes.add(node);
        node.setConnectionGroup(this);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<VirtualNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<VirtualNode> nodes) {
        this.nodes = nodes;
    }
}
