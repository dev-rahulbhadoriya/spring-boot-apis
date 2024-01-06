package com.facetcloud.apis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.repository.ConnectionGroupRepository;
import com.facetcloud.apis.repository.VirtualNodeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VirtualNodeService {

    @Autowired
    private VirtualNodeRepository virtualNodeRepository;

    @Autowired
    private ConnectionGroupRepository connectionGroupRepository;

    public VirtualNode createVirtualNode(VirtualNode virtualNode) {

        if (virtualNodeRepository.findByName(virtualNode.getId()).isPresent()) {
            throw new IllegalStateException("Virtual node with name " + virtualNode.getName() + " already exists.");
        }

        if (virtualNode.getConnectionGroup() != null) {
            Long connectionGroupId = virtualNode.getConnectionGroup().getId();
            ConnectionGroup connectionGroup = connectionGroupRepository.findById(connectionGroupId)
                    .orElseThrow(() -> new EntityNotFoundException("Connection group not found with id: " + connectionGroupId));
            virtualNode.setConnectionGroup(connectionGroup);
        }

        return virtualNodeRepository.save(virtualNode);
    }

    public VirtualNode getVirtualNodeById(Long id) {
        return virtualNodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Virtual node not found with id: " + id));
    }

    public VirtualNode updateVirtualNode(Long id, VirtualNode updatedVirtualNode) {

        VirtualNode existingNode = getVirtualNodeById(id);

        String updatedName = updatedVirtualNode.getName();
        if (!existingNode.getName().equals(updatedName) && virtualNodeRepository.findByName(updatedName).isPresent()) {
            throw new IllegalStateException("Virtual node with name " + updatedName + " already exists.");
        }

        existingNode.setName(updatedName);

        ConnectionGroup updatedConnectionGroup = updatedVirtualNode.getConnectionGroup();
        if (updatedConnectionGroup != null) {
            Long connectionGroupId = updatedConnectionGroup.getId();
            ConnectionGroup connectionGroup = connectionGroupRepository.findById(connectionGroupId)
                    .orElseThrow(() -> new EntityNotFoundException("Connection group not found with id: " + connectionGroupId));
            existingNode.setConnectionGroup(connectionGroup);
        } else {
            existingNode.setConnectionGroup(null); 
        }

        return virtualNodeRepository.save(existingNode);
    }

    public void deleteVirtualNode(Long id) {
        VirtualNode virtualNode = getVirtualNodeById(id);

        virtualNodeRepository.delete(virtualNode);
    }

    public List<VirtualNode> getAllVirtualNodes() {
        return virtualNodeRepository.findAll();
    }
}