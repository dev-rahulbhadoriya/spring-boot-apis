package com.facetcloud.apis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.repository.ConnectionGroupRepository;

@Service
public class ConnectionGroupService {
    @Autowired
    private ConnectionGroupRepository connectionGroupRepository;

    public ConnectionGroup saveConnectionGroup(ConnectionGroup connectionGroup) {
        return connectionGroupRepository.save(connectionGroup);
    }

    public ConnectionGroup findConnectionGroupByNodeName(String nodeName) {
        return connectionGroupRepository.findByNodes_NodeName(nodeName);
    }

    public ConnectionGroup findConnectionGroupByName(String groupName) {
        return connectionGroupRepository.findByGroupName(groupName);
    }
}

