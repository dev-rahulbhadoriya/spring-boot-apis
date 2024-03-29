package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.exception.CustomException;
import com.facetcloud.apis.service.NodeService;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @PostMapping("/createNode")
   public ResponseEntity<String> createNode(
            @RequestParam("nodeName") String nodeName,
            @RequestParam("connectionGroupName") String connectionGroupName) {

        try {
            if (nodeService.isNodeExistsInGroup(nodeName, connectionGroupName)) {
                return ResponseEntity.badRequest().body("Node with the same name already exists in the group");
            }
            nodeService.saveNode(nodeName, connectionGroupName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Node created successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/connectNodes")
    public ResponseEntity<String> connectNodes(
        @RequestParam("parentNodeName") String parentNodeName,
        @RequestParam("childNodeName") String childNodeName,
        @RequestParam("connectionGroupName") String connectionGroupName) {

        try {
            if (!nodeService.isNodeExistsInGroup(parentNodeName, connectionGroupName) ||
                !nodeService.isNodeExistsInGroup(childNodeName, connectionGroupName)) {
                return ResponseEntity.badRequest().body("Parent or child node not found in the specified connection group");
            }
        
            nodeService.connectNodes(parentNodeName, childNodeName, connectionGroupName);
            return ResponseEntity.ok("Nodes connected successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


}
