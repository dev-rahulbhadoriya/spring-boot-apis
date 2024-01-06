package com.facetcloud.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.service.VirtualNodeService;

@RestController
@RequestMapping("/virtual-nodes")
public class VirtualNodeController {

    @Autowired
    private VirtualNodeService virtualNodeService;

    @PostMapping
    public ResponseEntity<VirtualNode> createVirtualNode(@RequestBody VirtualNode virtualNode) {
        VirtualNode createdNode = virtualNodeService.createVirtualNode(virtualNode);
        return new ResponseEntity<>(createdNode, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VirtualNode> getVirtualNodeById(@PathVariable Long id) {
        VirtualNode virtualNode = virtualNodeService.getVirtualNodeById(id);
        return ResponseEntity.ok(virtualNode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VirtualNode> updateVirtualNode(@PathVariable Long id, @RequestBody VirtualNode virtualNode) {
        VirtualNode updatedNode = virtualNodeService.updateVirtualNode(id, virtualNode);
        return ResponseEntity.ok(updatedNode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVirtualNode(@PathVariable Long id) {
        virtualNodeService.deleteVirtualNode(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
