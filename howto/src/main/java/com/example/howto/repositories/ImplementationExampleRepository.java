package com.example.howto.repositories;


import com.example.howto.model.ImplementationExample;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImplementationExampleRepository extends JpaRepository<ImplementationExample, Long> {

	List<ImplementationExample> findByApiId(Long api);
}
