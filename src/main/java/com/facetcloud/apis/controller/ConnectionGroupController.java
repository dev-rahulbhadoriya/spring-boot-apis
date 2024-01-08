package com.facetcloud.apis.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facetcloud.apis.exception.CustomException;
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
    public ResponseEntity<?> createConnectionGroup(@RequestBody ConnectionGroup connectionGroup) {
        try {
            ConnectionGroup existingGroup = connectionGroupService.findConnectionGroupByName(connectionGroup.getGroupName());

            if (existingGroup != null) {
                return ResponseEntity.badRequest().body("Connection group with the same name already exists");
            }

            ConnectionGroup savedGroup = connectionGroupService.saveConnectionGroup(connectionGroup);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedGroup);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @GetMapping("/findConnectionGroup/{nodeName}")
    public ResponseEntity<?> findConnectionGroupByNodeName(@PathVariable String nodeName) {
        try {
            ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByNodeName(nodeName);

            if (connectionGroup != null) {
                return ResponseEntity.ok(connectionGroup);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Connection group not found for node name: " + nodeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
