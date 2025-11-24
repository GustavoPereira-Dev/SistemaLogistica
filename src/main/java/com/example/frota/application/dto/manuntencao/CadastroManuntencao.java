package com.example.frota.application.dto.manuntencao;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CadastroManuntencao(
		@NotBlank
		Long id,
		Long caminhaoId,
		LocalDate data,
		Double kmNoMomento,
		Double custo,
		String descricao
		
) {}