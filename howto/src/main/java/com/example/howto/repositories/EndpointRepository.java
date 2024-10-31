package com.example.howto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.howto.model.Endpoint;

public interface EndpointRepository extends JpaRepository<Endpoint, Long> {

	List<Endpoint> findByApiId(Long api_id);
}