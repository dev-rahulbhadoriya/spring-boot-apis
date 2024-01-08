package com.facetcloud.apis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.facetcloud.apis.exception.CustomException;
import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.repository.VirtualNodeRepository;
@Service
public class NodeService {
    @Autowired
    private VirtualNodeRepository nodeRepository;

    @Autowired
    private ConnectionGroupService connectionGroupService;

    // node save in group
    public VirtualNode saveNode(String nodeName, String connectionGroupName) {
        ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByName(connectionGroupName);

        if (connectionGroup != null) {
            Optional<VirtualNode> existingNode = nodeRepository.findByNodeNameAndConnectionGroup_GroupName(nodeName, connectionGroupName);
        if (existingNode.isPresent()) {
            throw new CustomException("Node with the specified name already exists in the connection group", HttpStatus.BAD_REQUEST);
        } else {
            VirtualNode node = new VirtualNode();
            node.setNodeName(nodeName);
            node.setConnectionGroup(connectionGroup);
            return nodeRepository.save(node);
        }
        } else {
            throw new CustomException("Invalid input or connection group not found", HttpStatus.BAD_REQUEST);
        }
    }

    // child node connected to parent node
    public void connectNodes(String parentNodeName, String childNodeName, String connectionGroupName) {

        Optional<VirtualNode> parentNodeOptional = nodeRepository.findByNodeNameAndConnectionGroup_GroupName(parentNodeName, connectionGroupName);
        Optional<VirtualNode> childNodeOptional = nodeRepository.findByNodeNameAndConnectionGroup_GroupName(childNodeName, connectionGroupName);

        if (parentNodeOptional.isPresent() && childNodeOptional.isPresent()) {
            VirtualNode parentNode = parentNodeOptional.get();
            VirtualNode childNode = childNodeOptional.get();   
            if (parentNode.equals(childNode)) {
                throw new CustomException("Invalid input: parentNode and childNode are the same", HttpStatus.BAD_REQUEST);
            } 

            childNode.setParentNode(parentNode);
            nodeRepository.save(childNode);
        } else {
            throw new CustomException("Invalid input or nodes not found", HttpStatus.BAD_REQUEST);
        }
    }

}
