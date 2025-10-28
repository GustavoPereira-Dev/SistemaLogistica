package com.example.frota.marca;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MarcaService{
	@Autowired
	private MarcaRepository marcaRepository;

	public Marca save(Marca marca){
		return marcaRepository.save(marca);
	}
	
	public List<Marca> procurarTodos(){
		return marcaRepository.findAll(Sort.by("nome").ascending());
	}
	
	public Optional<Marca> buscar(long id) {
		return marcaRepository.findById(id);
	}
	
	public void apagar(long id) {
		marcaRepository.deleteById(id);
	}
	
	public void atualizarMarca(DadosAtualizacaoMarca dados) {
	    Marca marca = marcaRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
	    marca.atualizarInformacoes(dados);
	}
	
}
