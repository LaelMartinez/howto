package com.example.howto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.howto.model.Api;

public interface ApiRepository extends JpaRepository<Api, Long> {
}
