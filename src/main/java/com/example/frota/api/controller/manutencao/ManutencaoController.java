package com.example.frota.api.controller.manutencao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.frota.api.annotations.PublicRoute;
import com.example.frota.application.dto.manutencao.AtualizacaoManutencao;
import com.example.frota.domain.manuntencao.mapper.ManuntencaoMapper;
import com.example.frota.domain.manutencao.model.Manutencao;
import com.example.frota.domain.manutencao.service.ManutencaoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manutencao")
public class ManutencaoController {
	
	@Autowired
	private ManutencaoService manutencaoService;
	
	@Autowired
	private ManuntencaoMapper manutencaoMapper;
	
	@PublicRoute
    @GetMapping
    public ResponseEntity<List<AtualizacaoManutencao>> listarTodos() {
        List<Manutencao> manutencoes = manutencaoService.procurarTodos();
        List<AtualizacaoManutencao> dtos = manutencoes.stream()
                .map(manutencaoMapper::toAtualizacaoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
	
	@PublicRoute
    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoManutencao> buscarPorId(@PathVariable Long id) {
        return manutencaoService.procurarPorId(id)
                .map(manutencaoMapper::toAtualizacaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
    @Transactional
    public ResponseEntity<?> criar(
            @RequestBody @Valid AtualizacaoManutencao dto) {

        try {
            Manutencao manutencaoSalvo = manutencaoService.salvarOuAtualizar(dto);
            AtualizacaoManutencao dtoSalvo = manutencaoMapper.toAtualizacaoDto(manutencaoSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Caminhão não encontrado\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoManutencao> atualizar(@RequestBody @Valid AtualizacaoManutencao dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Manutencao manutencaoSalvo = manutencaoService.salvarOuAtualizar(dto);
            AtualizacaoManutencao dtoSalvo = manutencaoMapper.toAtualizacaoDto(manutencaoSalvo);
            return ResponseEntity.ok(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (manutencaoService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            manutencaoService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
