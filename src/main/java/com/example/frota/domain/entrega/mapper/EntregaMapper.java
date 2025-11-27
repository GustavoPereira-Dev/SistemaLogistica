package com.example.frota.domain.entrega.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.application.dto.entrega.AtualizacaoEntrega;
import com.example.frota.application.dto.entrega.CadastroEntrega;
import com.example.frota.domain.entrega.model.Entrega;
import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.viagem.model.Viagem;

@Mapper(componentModel = "spring")
public interface EntregaMapper {

    // CREATE: De DTO Cadastro para Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "COLETA") // Status inicial padrão
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "viagem", source = "viagemId", qualifiedByName = "idToViagem")
    Entrega toEntity(CadastroEntrega dto);

    // READ/UPDATE: De Entity para DTO Atualizacao (para retornar na API ou popular form)
    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "viagemId", source = "viagem.id")
    AtualizacaoEntrega toAtualizacaoDto(Entrega entrega);

    // UPDATE: Atualiza a Entity com dados do DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "viagem", source = "viagemId", qualifiedByName = "idToViagem")
    void updateEntityFromDto(AtualizacaoEntrega dto, @MappingTarget Entrega entrega);

    @Named("idToProduto")
    default Produto idToProduto(Long id) {
        if (id == null) return null;
        Produto p = new Produto();
        p.setId(id);
        return p;
    }

    @Named("idToViagem")
    default Viagem idToViagem(Long id) {
        if (id == null) return null;
        Viagem v = new Viagem();
        v.setId(id);
        return v;
    }
}