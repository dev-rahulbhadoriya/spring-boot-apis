package com.facetcloud.apis.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.facetcloud.apis.model.VirtualNode;

public interface VirtualNodeRepository extends JpaRepository<VirtualNode, Long> {
    VirtualNode findByNodeName(String nodeName);
}
    

