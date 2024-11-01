package com.example.howto.repositories;


import com.example.howto.model.Implementation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImplementationRepository extends JpaRepository<Implementation, Long> {

	List<Implementation> findByApiId(Long api);
}
