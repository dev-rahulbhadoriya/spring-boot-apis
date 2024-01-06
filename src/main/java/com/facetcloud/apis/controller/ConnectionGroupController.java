package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.service.ConnectionGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/connection-groups")
public class ConnectionGroupController {
    @Autowired
    private ConnectionGroupService connectionGroupService;

    @GetMapping("/by-virtual-node")
    public ResponseEntity<ConnectionGroup> findConnectionGroupByVirtualNodeName(
            @RequestParam String virtualNodeName) {
        ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByVirtualNodeName(virtualNodeName);
        return ResponseEntity.ok(connectionGroup);
    }
}
