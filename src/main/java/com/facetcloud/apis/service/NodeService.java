package com.facetcloud.apis.service;

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

    public VirtualNode saveNode(String nodeName, String connectionGroupName) {
        ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByName(connectionGroupName);

        if (connectionGroup != null) {
            VirtualNode node = new VirtualNode();
            node.setNodeName(nodeName);
            node.setConnectionGroup(connectionGroup);
            return nodeRepository.save(node);
        } else {
            throw new CustomException("Invalid input or connection group not found",HttpStatus.NOT_FOUND );
        }
    }

    public void connectNodes(String parentNodeName, String childNodeName, String connectionGroupName) {
        VirtualNode parentNode = nodeRepository.findByNodeNameAndConnectionGroup_GroupName(parentNodeName, connectionGroupName);
        VirtualNode childNode = nodeRepository.findByNodeNameAndConnectionGroup_GroupName(childNodeName, connectionGroupName);
        if (parentNode != null && childNode != null) {
            childNode.setParentNode(parentNode);
            nodeRepository.save(childNode);
        } else {
            throw new CustomException("Invalid input or nodes not found", HttpStatus.NOT_FOUND);
        }
    }

}
