package com.facetcloud.apis.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public VirtualNode saveNode(VirtualNode node) {
        return nodeRepository.save(node);
    }

    public void connectNodes(String parentNodeName, String childNodeName, String connectionGroupName) {
        VirtualNode parentNode = nodeRepository.findByNodeName(parentNodeName);
        VirtualNode childNode = nodeRepository.findByNodeName(childNodeName);
        ConnectionGroup connectionGroup = connectionGroupService.findConnectionGroupByName(connectionGroupName);

        if (parentNode != null && childNode != null && connectionGroup != null) {
            childNode.setParentNode(parentNode);
            childNode.setConnectionGroup(connectionGroup);
            nodeRepository.save(childNode);
        } else {
            throw new CustomException("Invalid input or nodes/connection group not found");
        }
    }
}
