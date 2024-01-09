package com.facetcloud.apis.backend.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.facetcloud.apis.exception.CustomException;
import com.facetcloud.apis.model.ConnectionGroup;
import com.facetcloud.apis.model.VirtualNode;
import com.facetcloud.apis.repository.VirtualNodeRepository;
import com.facetcloud.apis.service.ConnectionGroupService;
import com.facetcloud.apis.service.NodeService;


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

    assertThrows(CustomException.class, () -> {
        VirtualNode parentNode = new VirtualNode();
        parentNode.setNodeName("NodeParent");
        VirtualNode savedParentNode = nodeService.saveNode(parentNode.getNodeName(), connectionGroup.getGroupName());

        VirtualNode childNode = new VirtualNode();
        childNode.setNodeName("NodeChild");

        nodeService.connectNodes("NodeParent", "NodeChild", "GroupA");

        VirtualNode connectedChildNode = nodeService.getNodeByName(childNode.getNodeName(), connectionGroup.getGroupName());

    });

    }

    @Test
    public void testGetConnectionGroupByNodeName(){
        ConnectionGroup connectionGroup = new ConnectionGroup();
        connectionGroup.setGroupName("TestGroup");
        connectionGroupService.saveConnectionGroup(connectionGroup);
    
        VirtualNode testNode = new VirtualNode();
        testNode.setNodeName("TestNode");
        testNode.setConnectionGroup(connectionGroup);
        connectionGroup.getNodes().add(testNode);
        connectionGroupService.saveConnectionGroup(connectionGroup);
    
        List<ConnectionGroup> foundGroups = connectionGroupService.findConnectionGroupByNodeName("TestNode");
    
        assertTrue(foundGroups.isEmpty(), "No connection group should be found for the specified node name");

    }


}
