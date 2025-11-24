package com.example.frota.domain.manuntencao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.domain.manuntencao.model.Manuntencao;

@Repository
@Transactional
public interface ManuntencaoRepository extends JpaRepository<Manuntencao, Long>{
	
}
