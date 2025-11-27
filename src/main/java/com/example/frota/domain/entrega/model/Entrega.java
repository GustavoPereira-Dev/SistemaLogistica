package com.example.frota.domain.entrega.model;

import java.time.LocalDateTime;

import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.viagem.model.Viagem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "entrega")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrega_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viagem_id", referencedColumnName = "id")
    private Viagem viagem;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    private String enderecoDestino;
    private LocalDateTime dataPrevisao;
    private LocalDateTime dataConclusao;
    
    private String feedbackCliente;
    private Integer pontuacaoEntrega; // 1 a 5 estrelas

    // Enum interno ou externo
    public enum StatusEntrega {
        COLETA,
        EM_PROCESSAMENTO,
        A_CAMINHO,
        ENTREGUE,
        CANCELADA
    }
}

