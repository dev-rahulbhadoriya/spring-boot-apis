package com.facetcloud.apis.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facetcloud.apis.model.ConnectionGroup;

public interface ConnectionGroupRepository extends JpaRepository<ConnectionGroup, Long> {

    @Query("SELECT DISTINCT cg FROM ConnectionGroup cg JOIN cg.nodes n WHERE n.nodeName = :nodeName")
    List<ConnectionGroup> findByNodes_NodeName(@Param("nodeName") String nodeName);

    ConnectionGroup findByGroupName(String groupName);

}