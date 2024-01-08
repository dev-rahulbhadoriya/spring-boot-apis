package com.facetcloud.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facetcloud.apis.model.VirtualNode;

public interface VirtualNodeRepository extends JpaRepository<VirtualNode, Long> {
    @Query("SELECT vn FROM VirtualNode vn " +
    "JOIN FETCH vn.connectionGroup cg " +
    "WHERE vn.nodeName = :nodeName AND cg.groupName = :connectionGroupName")
    Optional<VirtualNode>findByNodeNameAndConnectionGroup_GroupName(@Param("nodeName") String nodeName,@Param("connectionGroupName") String connectionGroupName);

    VirtualNode findByNodeName(String nodeName);
    boolean existsByNodeNameAndConnectionGroup_GroupName(String nodeName, String connectionGroupName);

}
    

