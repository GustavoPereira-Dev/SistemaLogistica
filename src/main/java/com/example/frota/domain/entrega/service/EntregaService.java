package com.example.frota.domain.entrega.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.application.dto.entrega.AtualizacaoEntrega;
import com.example.frota.application.dto.entrega.CadastroEntrega;
import com.example.frota.domain.entrega.mapper.EntregaMapper;
import com.example.frota.domain.entrega.model.Entrega;
import com.example.frota.domain.entrega.repository.EntregaRepository;
import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.produto.service.ProdutoService;
import com.example.frota.domain.viagem.model.Viagem;
import com.example.frota.domain.viagem.service.ViagemService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private EntregaMapper entregaMapper;

    public Entrega criar(CadastroEntrega dto) {
        Produto produto = produtoService.procurarPorId(dto.produtoId())
               .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));
        
        Viagem viagem = viagemService.procurarPorId(dto.viagemId())
               .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada com ID: " + dto.viagemId()));

        Entrega novaEntrega = entregaMapper.toEntity(dto);
        novaEntrega.setProduto(produto);
        novaEntrega.setViagem(viagem);
        
        return entregaRepository.save(novaEntrega);
    }

    public Entrega atualizar(AtualizacaoEntrega dto) {
        Entrega existente = entregaRepository.findById(dto.id())
               .orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada com ID: " + dto.id()));

        entregaMapper.updateEntityFromDto(dto, existente);

        if (dto.produtoId()!= null) {
            Produto p = produtoService.procurarPorId(dto.produtoId())
               .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
            existente.setProduto(p);
        }
        if (dto.viagemId()!= null) {
            Viagem v = viagemService.procurarPorId(dto.viagemId())
               .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada"));
            existente.setViagem(v);
        }

        return entregaRepository.save(existente);
    }

    public List<Entrega> procurarTodos() {
        return entregaRepository.findAll(Sort.by("dataPrevisao").ascending());
    }

    public Optional<Entrega> procurarPorId(Long id) {
        return entregaRepository.findById(id);
    }

    public void apagarPorId(Long id) {
        entregaRepository.deleteById(id);
    }
}