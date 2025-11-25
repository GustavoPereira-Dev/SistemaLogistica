package com.example.frota.domain.manuntencao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.application.dto.manuntencao.AtualizacaoManuntencao;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.manuntencao.model.Manuntencao;


@Mapper(componentModel = "spring")
public interface ManuntencaoMapper {
    
    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    AtualizacaoManuntencao toAtualizacaoDto(Manuntencao caminhao);
    
    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    Manuntencao toEntityFromAtualizacao(AtualizacaoManuntencao dto);
    
    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    void updateEntityFromDto(AtualizacaoManuntencao dto, @MappingTarget Manuntencao existente);
    
    // Método para converter marcaId em objeto Marca
    @Named("idToCaminhao")
    default Caminhao idToCaminhao(Long caminhaoId) {
        if (caminhaoId == null) return null;
        Caminhao caminhao = new Caminhao();
        caminhao.setId(caminhaoId);
        return caminhao;
    }
}