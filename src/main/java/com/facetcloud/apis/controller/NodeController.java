package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.service.ConnectionGroupService;
import com.facetcloud.apis.service.NodeService;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @Autowired
    private ConnectionGroupService connectionGroupService;  

    @PostMapping("/createNode")
    public ResponseEntity<String> createNode(
            @RequestParam("nodeName") String nodeName,
            @RequestParam("connectionGroupName") String connectionGroupName) {

        nodeService.saveNode(nodeName, connectionGroupName);
        return ResponseEntity.ok("Node created successfully");
    }

    @PostMapping("/connectNodes")
    public ResponseEntity<String> connectNodes(
            @RequestParam("parentNodeName") String parentNodeName,
            @RequestParam("childNodeName") String childNodeName,
            @RequestParam("connectionGroupName") String connectionGroupName) {
        nodeService.connectNodes(parentNodeName, childNodeName, connectionGroupName);
        return ResponseEntity.ok("Nodes connected successfully");
    }

    @GetMapping("/findConnectionGroup/{nodeName}")
    public ResponseEntity<ConnectionGroup> findConnectionGroupByNodeName(@PathVariable String nodeName) {
        ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByNodeName(nodeName);
        return ResponseEntity.ok(connectionGroup);
    }
}
