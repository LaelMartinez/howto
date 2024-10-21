package com.example.howto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.howto.model.Howto;

public interface HowtoRepository extends JpaRepository<Howto, Long> {
}
