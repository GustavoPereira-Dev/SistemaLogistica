package com.example.frota.api.controller.entrega;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.frota.application.dto.entrega.AtualizacaoEntrega;
import com.example.frota.application.dto.entrega.CadastroEntrega;
import com.example.frota.domain.entrega.mapper.EntregaMapper;
import com.example.frota.domain.entrega.model.Entrega;
import com.example.frota.domain.entrega.service.EntregaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entrega")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private EntregaMapper entregaMapper;

    @GetMapping
    public ResponseEntity<List<AtualizacaoEntrega>> listarTodos() {
        List<Entrega> entregas = entregaService.procurarTodos();
        List<AtualizacaoEntrega> dtos = entregas.stream()
               .map(entregaMapper::toAtualizacaoDto)
               .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoEntrega> buscarPorId(@PathVariable Long id) {
        return entregaService.procurarPorId(id)
               .map(entregaMapper::toAtualizacaoDto)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid CadastroEntrega dto) {
        try {
            Entrega entregaSalva = entregaService.criar(dto);
            AtualizacaoEntrega dtoSalvo = entregaMapper.toAtualizacaoDto(entregaSalva);
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody @Valid AtualizacaoEntrega dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().body("{\"erro\":\"ID da entrega é obrigatório\"}");
        }
        try {
            Entrega entregaAtualizada = entregaService.atualizar(dto);
            return ResponseEntity.ok(entregaMapper.toAtualizacaoDto(entregaAtualizada));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (entregaService.procurarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entregaService.apagarPorId(id);
        return ResponseEntity.noContent().build();
    }
}