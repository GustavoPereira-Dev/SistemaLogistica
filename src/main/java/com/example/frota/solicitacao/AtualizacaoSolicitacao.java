package com.example.frota.solicitacao;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoSolicitacao(
		
		Long id,
	    
		@NotNull(message = "Código do Produto é obrigatório")
		Long produtoId,
		@NotNull(message = "Código da Caixa é obrigatória")
		Long caixaId,
		@NotNull(message = "Código do Caminhão é obrigatório")
		Long caminhaoId,
		@NotNull(message = "Endereço de origem é obrigatório")
		String cepOrigem,
		@NotNull(message = "Endereço de destino é obrigatório")
		String cepDestino,
		LocalDateTime dataSolicitacao,
		
		// @Positive(message = "Distancia em km deve ser positiva")
		Double distanciaKm,
		//@Positive(message = "Custo de pedagios deve ser positivo")
		Double custoPedagios,
		// @Positive(message = "Peso deve ser positivo")
		Double pesoConsideradoKg,
		// @Positive(message = "Custo do Frete deve ser positivo")
		Double custoFreteCalculado	
		
	) {

}
