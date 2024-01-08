package com.facetcloud.apis.service;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<ConnectionGroup> findConnectionGroupByNodeName(String nodeName) {
        List<ConnectionGroup> allGroups = connectionGroupRepository.findByNodes_NodeName(nodeName);
        
        return allGroups.stream()
            .filter(group -> groupContainsNode(group, nodeName))
            .collect(Collectors.toList());
        }

        private boolean groupContainsNode(ConnectionGroup group, String nodeName) {
                return group.getNodes().stream()
                .anyMatch(node -> nodeName.equals(node.getNodeName()));
        }

    public ConnectionGroup findConnectionGroupByName(String groupName) {
        return connectionGroupRepository.findByGroupName(groupName);
    }
}

