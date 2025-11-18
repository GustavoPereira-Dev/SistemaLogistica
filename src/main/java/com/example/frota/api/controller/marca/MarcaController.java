package com.example.frota.api.controller.marca;

import com.example.frota.application.dto.marca.DadosAtualizacaoMarca;
import com.example.frota.application.dto.marca.DadosCadastroMarca;
import com.example.frota.domain.marca.model.Marca;
import com.example.frota.domain.marca.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
 
@Controller
@RequestMapping("/marca")
public class MarcaController {
 
	@Autowired
	private MarcaService marcaService;
 
	@GetMapping              
	public String carregaPaginaListagem(Model model){
		model.addAttribute("lista",marcaService.procurarTodos() );
		return "marca/listagem";              
	}
	
	@GetMapping ("/formulario")             
	public String mostraFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Marca marca = marcaService.buscar(id)
					.orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));;
			model.addAttribute("marca", marca);
		}
		return "marca/formulario";     
	}
	
	@GetMapping ("/formulario/{id}")  
	public String carregaPaginaFormulario(@PathVariable("id") Long id, Model model) {
		if(id != null) {
			Marca marca = marcaService.buscar(id)
					.orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));;
			model.addAttribute("marca", marca);
		}
		return "marca/formulario";     
	}
	
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		marcaService.apagar(id);
		return "redirect:marca";
	}
	
	// Método para gravar/atualizar o formulário
	@PostMapping
	@Transactional
	public String cadastrar (@Valid DadosCadastroMarca dados) {
		marcaService.save(new Marca(dados));
		return "redirect:marca";
	}
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoMarca dados) {
		marcaService.atualizarMarca(dados);
		return "redirect:marca";
	}
}