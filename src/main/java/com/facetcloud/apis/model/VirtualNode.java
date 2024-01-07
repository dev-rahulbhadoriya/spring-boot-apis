package com.facetcloud.apis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VirtualNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nodeName;

    @ManyToOne
    @JoinColumn(name = "parent_node_id")
    private VirtualNode parentNode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "connection_group_id")
    private ConnectionGroup connectionGroup;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public VirtualNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(VirtualNode parentNode) {
        this.parentNode = parentNode;
    }

    public ConnectionGroup getConnectionGroup() {
        return connectionGroup;
    }

    public void setConnectionGroup(ConnectionGroup connectionGroup) {
        this.connectionGroup = connectionGroup;
    }
}