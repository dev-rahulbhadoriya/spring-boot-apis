package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.service.ConnectionGroupService;
import com.facetcloud.apis.service.NodeService;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @Autowired
    private ConnectionGroupService connectionGroupService;  

    @PostMapping
    public VirtualNode createNode(@RequestBody VirtualNode node) {
        return nodeService.saveNode(node);
    }

    @PostMapping("/connect")
    public void connectNodes(
            @RequestParam("parentNodeName") String parentNodeName,
            @RequestParam("childNodeName") String childNodeName,
            @RequestParam("connectionGroupName") String connectionGroupName) {
        nodeService.connectNodes(parentNodeName, childNodeName, connectionGroupName);
    }

    @GetMapping("/find-connection-group/{nodeName}")
    public ConnectionGroup findConnectionGroupByNodeName(@PathVariable String nodeName) {
        return connectionGroupService.findConnectionGroupByNodeName(nodeName);
    }

}
