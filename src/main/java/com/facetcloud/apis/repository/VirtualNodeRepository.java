package com.facetcloud.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facetcloud.apis.model.VirtualNode;

public interface VirtualNodeRepository extends JpaRepository<VirtualNode, Long> {
    Optional<VirtualNode> findByName(String name);
}

