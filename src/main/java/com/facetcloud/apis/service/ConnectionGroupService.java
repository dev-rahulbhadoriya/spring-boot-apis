package com.facetcloud.apis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.repository.ConnectionGroupRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConnectionGroupService {
    @Autowired
    private ConnectionGroupRepository connectionGroupRepository;

    public ConnectionGroup findConnectionGroupByVirtualNodeName(String virtualNodeName) {
        return connectionGroupRepository.findByVirtualNodes_Name(virtualNodeName)
                .orElseThrow(() -> new EntityNotFoundException("Connection group not found for virtual node: " + virtualNodeName));
    }
}

