package com.facetcloud.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facetcloud.apis.model.ConnectionGroup;

public interface ConnectionGroupRepository extends JpaRepository<ConnectionGroup, Long> {

    ConnectionGroup findByNodes_NodeName(String nodeName);
}