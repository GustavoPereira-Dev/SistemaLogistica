package com.example.frota.domain.entrega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.domain.entrega.model.Entrega;

@Repository
@Transactional
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}