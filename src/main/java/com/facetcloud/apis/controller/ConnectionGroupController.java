package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.service.ConnectionGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/connection-groups")
public class ConnectionGroupController {
    @Autowired
    private ConnectionGroupService connectionGroupService;

    @PostMapping
    public ConnectionGroup createConnectionGroup(@RequestBody ConnectionGroup connectionGroup) {
        return connectionGroupService.saveConnectionGroup(connectionGroup);
    }

    @GetMapping("/findbynode/{nodeName}")
    public ConnectionGroup findConnectionGroupByNodeName(@PathVariable String nodeName) {
        return connectionGroupService.findConnectionGroupByNodeName(nodeName);
    }
}
