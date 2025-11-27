package com.example.frota.application.dto.entrega;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroEntrega(
    
    @NotNull(message = "Produto é obrigatório")
    Long produtoId,

    @NotNull(message = "Viagem é obrigatória")
    Long viagemId,

    @NotBlank(message = "Endereço de destino é obrigatório")
    String enderecoDestino,

    LocalDateTime dataPrevisao
) {}