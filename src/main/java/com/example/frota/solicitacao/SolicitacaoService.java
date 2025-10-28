package com.example.frota.solicitacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;
import com.example.frota.caminhao.CaminhaoService;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.produto.Produto;
import com.example.frota.produto.ProdutoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitacaoService {
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CaminhaoService caminhaoService;
	
	@Autowired
	private SolicitacaoMapper solicitacaoMapper;
	
	public Solicitacao salvarOuAtualizar(AtualizacaoSolicitacao dto) {
        Caixa caixa = caixaService.procurarPorId(dto.caixaId())
            .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.caixaId()));
        
        Produto produto = produtoService.procurarPorId(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));
        
        Caminhao caminhao = caminhaoService.procurarPorId(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.produtoId()));
        
        if (dto.id() != null) {
            Solicitacao existente = solicitacaoRepository.findById(dto.id())
            		.orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada com ID: " + dto.id()));
            solicitacaoMapper.updateEntityFromDto(dto, existente);
            existente.setCaixa(caixa);
            existente.setProduto(produto);
            existente.setCaminhao(caminhao);
            return solicitacaoRepository.save(existente);
        } else {
            Solicitacao novaSolicitacao = solicitacaoMapper.toEntityFromAtualizacao(dto);
            novaSolicitacao.setCaixa(caixa);
            novaSolicitacao.setProduto(produto);
            novaSolicitacao.setCaminhao(caminhao);
            
            return solicitacaoRepository.save(novaSolicitacao);
        }
    }
	
	public List<Solicitacao> procurarTodos(){
		return solicitacaoRepository.findAll(Sort.by("dataSolicitacao").ascending());
	}
	public void apagarPorId (Long id) {
		solicitacaoRepository.deleteById(id);
	}
	
	public Optional<Solicitacao> procurarPorId(Long id) {
	    return solicitacaoRepository.findById(id);
	}
}
