package com.facetcloud.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facetcloud.apis.model.ConnectionGroup;

public interface ConnectionGroupRepository extends JpaRepository<ConnectionGroup, Long> {
    Optional<ConnectionGroup> findByVirtualNodes_Name(String virtualNodeName);
}
