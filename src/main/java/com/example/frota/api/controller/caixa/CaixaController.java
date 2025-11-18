package com.example.frota.api.controller.caixa;

import com.example.frota.domain.caixa.service.CaixaService;
import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.application.dto.caixa.CadastroCaixa;
import com.example.frota.domain.caixa.model.Caixa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/caixa")
public class CaixaController {
	
	@Autowired
	private CaixaService caixaService;
	
	@GetMapping
	public String carregaPaginaFormulario (Model model){ 
		model.addAttribute("lista", caixaService.procurarTodos());
	    return "caixa/listagem";              
	} 

	@GetMapping ("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Caixa caixa = caixaService.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
			model.addAttribute("caixa", caixa);
		}
		return "caixa/formulario";       
    }
	
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		caixaService.apagarPorId(id);
		return "redirect:caixa";
	}
	
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroCaixa dados) {
		caixaService.save(new Caixa(dados));
		return "redirect:caixa";
	}
	
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoCaixa dados) {
		caixaService.atualizarCaixa(dados);
		return "redirect:caixa";
	}

}
