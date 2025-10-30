package com.example.frota.produto;

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
@RequestMapping("/produto")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;
	
	
	@GetMapping                 
	public String carregaPaginaFormulario (Model model){ 
		//devolver DTO
		model.addAttribute("lista", produtoService.procurarTodos());
	    return "produto/listagem";              
	} 
	
	@GetMapping ("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Produto produto = produtoService.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
			model.addAttribute("produto", produto);
		}
		return "produto/formulario";       
    }
	
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		produtoService.apagarPorId(id);
		return "redirect:produto";
	}
	
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroProduto dados) {
		produtoService.save(new Produto(dados));
		return "redirect:produto";
	}
	
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoProduto dados) {
		produtoService.atualizarProduto(dados);
		return "redirect:produto";
	}

}
