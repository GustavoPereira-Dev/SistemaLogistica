package com.example.frota.produto;

import jakarta.validation.constraints.NotBlank;

public record CadastroProduto(
		@NotBlank
		String nome,
		Double peso,
		Double preco
		) {}
