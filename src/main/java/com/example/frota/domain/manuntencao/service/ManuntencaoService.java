package com.example.frota.domain.manuntencao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.application.dto.manuntencao.AtualizacaoManuntencao;
import com.example.frota.domain.manuntencao.mapper.ManuntencaoMapper;
import com.example.frota.domain.manuntencao.model.Manuntencao;
import com.example.frota.domain.manuntencao.repository.ManuntencaoRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.caminhao.service.CaminhaoService;

@Service
public class ManuntencaoService {
	@Autowired
	private ManuntencaoRepository manuntencaoRepository;
	
	@Autowired
	private CaminhaoService caminhaoService;
	
	@Autowired
	private ManuntencaoMapper manuntencaoMapper;
	
	public Manuntencao salvarOuAtualizar(AtualizacaoManuntencao dto) {
        // Valida se a marca existe
        Caminhao caminhao = caminhaoService.buscar(dto.caminhaoId())
            .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.caminhaoId()));
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Manuntencao existente = manuntencaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.id()));
            manuntencaoMapper.updateEntityFromDto(dto, existente);
            existente.setCaminhao(caminhao);
            return manuntencaoRepository.save(existente);
        } else {
            // criando Novo caminhão
            Manuntencao novoManuntencao = manuntencaoMapper.toEntityFromAtualizacao(dto);
            novoManuntencao.setCaminhao(caminhao);
            
            return manuntencaoRepository.save(novoManuntencao);
        }
    }
	
	public List<Manuntencao> procurarTodos(){
		return manuntencaoRepository.findAll(Sort.by("data").ascending());
	}
	public void apagarPorId (Long id) {
		manuntencaoRepository.deleteById(id);
	}
	
	public Optional<Manuntencao> procurarPorId(Long id) {
	    return manuntencaoRepository.findById(id);
	}
	
}