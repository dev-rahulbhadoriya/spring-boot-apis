package com.facetcloud.apis.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.repository.VirtualNodeRepository;
import com.facetcloud.apis.service.ConnectionGroupService;
import com.facetcloud.apis.service.NodeService;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NodeServiceTest {
    
    
    @Mock
    private VirtualNodeRepository nodeRepository;

    @Mock
    private ConnectionGroupService connectionGroupService;

    @InjectMocks
    private NodeService nodeService;


    @Test
    public void testSaveNode() {
        VirtualNode node = new VirtualNode();
        node.setNodeName("TestNode");

        ConnectionGroup  connectionGroup = new ConnectionGroup();
        connectionGroup.setGroupName("testGroup");

        when(nodeRepository.save(any(VirtualNode.class))).thenReturn(node);
        when(connectionGroupService.findConnectionGroupByName(connectionGroup.getGroupName())).thenReturn(connectionGroup);


        VirtualNode savedNode = nodeService.saveNode(node.getNodeName(),connectionGroup.getGroupName());
        assertEquals("TestNode", savedNode.getNodeName());

        verify(nodeRepository).save(any(VirtualNode.class));
    }

    @Test
    public void testConnectNodes() {
        ConnectionGroup connectionGroup = new ConnectionGroup();
        connectionGroup.setGroupName("GroupA");
        connectionGroupService.saveConnectionGroup(connectionGroup);

        VirtualNode parentNode = new VirtualNode();
        parentNode.setNodeName("NodeParent");
        VirtualNode savedParentNode = nodeService.saveNode(parentNode.getNodeName(),connectionGroup.getGroupName());
       
        VirtualNode childNode = new VirtualNode();
        childNode.setNodeName("NodeChild");

        // Connect nodes using the service method
        nodeService.connectNodes("NodeParent", "NodeChild", "GroupA");

        // Retrieve child node after connection
        VirtualNode connectedChildNode = nodeService.getNodeByName(childNode.getNodeName(), connectionGroup.getGroupName());

        assertThat(connectedChildNode).isNotNull();
        assertThat(connectedChildNode.getParentNode()).isEqualTo(savedParentNode);
        assertThat(connectedChildNode.getConnectionGroup().getGroupName()).isEqualTo("GroupA");
    }


}
