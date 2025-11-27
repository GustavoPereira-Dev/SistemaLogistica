package com.example.frota.application.dto.entrega;

import java.time.LocalDateTime;

import com.example.frota.domain.entrega.model.Entrega.StatusEntrega;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoEntrega(
    
    @NotNull
    Long id,
    
    Long produtoId,
    Long viagemId,
    
    String enderecoDestino,
    StatusEntrega status,
    
    LocalDateTime dataConclusao,
    String feedbackCliente,
    Integer pontuacaoEntrega
) {}