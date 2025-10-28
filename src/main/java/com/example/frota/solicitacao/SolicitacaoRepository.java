package com.example.frota.solicitacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.marca.Marca;

@Repository
@Transactional
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>{
	
}
